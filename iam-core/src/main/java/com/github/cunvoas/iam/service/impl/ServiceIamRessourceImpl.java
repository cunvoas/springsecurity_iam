package com.github.cunvoas.iam.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.cunvoas.iam.bo.IamApplication;
import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.bo.IamRole;
import com.github.cunvoas.iam.bo.IamValue;
import com.github.cunvoas.iam.exception.IamException;
import com.github.cunvoas.iam.exception.IamExceptionContants;
import com.github.cunvoas.iam.persistance.entity.Application;
import com.github.cunvoas.iam.persistance.entity.Ressource;
import com.github.cunvoas.iam.persistance.entity.RessourceValeur;
import com.github.cunvoas.iam.persistance.entity.RessourceValeurPK;
import com.github.cunvoas.iam.persistance.entity.Role;
import com.github.cunvoas.iam.persistance.entity.Valeur;
import com.github.cunvoas.iam.persistance.repositories.ApplicationRepository;
import com.github.cunvoas.iam.persistance.repositories.RessourceRepository;
import com.github.cunvoas.iam.persistance.repositories.RessourceValeurRepository;
import com.github.cunvoas.iam.persistance.repositories.RoleRepository;
import com.github.cunvoas.iam.persistance.repositories.ValeurRepository;
import com.github.cunvoas.iam.service.ServiceIamRessource;
import com.github.cunvoas.iam.service.mapper.MapperHelper;

/**
 * @author cunvoas
 *
 */
@Service("ServiceIamRessource")
public class ServiceIamRessourceImpl implements ServiceIamRessource {
    
    private  static final int BORNE_START=1;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceIamRessourceImpl.class);

    @Resource
    private ApplicationRepository applicationRepository;

    @Resource
    private RessourceRepository ressourceRepository;
    
    @Resource
    private RoleRepository roleRepository;

    @Resource
    private RessourceValeurRepository ressourceValeurRepository;
    
    @Resource
    private ValeurRepository valeurRepository;

    
    /**
     * @see com.github.cunvoas.iam.service.impl.ServiceIamRessource#saveRoot(com.github.cunvoas.iam.bo.IamRessource, java.lang.String)
     */
    @Override
    public IamRessource saveRoot(IamRessource iamRessource, String applicationCode) {
        IamRessource ret=null;
        
        Application application = applicationRepository.findByCode(applicationCode);
        if (application!=null) {
            // calcule les intervales
            this.computeInterval(iamRessource);
            
            // mise à plat des objet IAM
            List<IamRessource> toSaveIamList = MapperHelper.asList(iamRessource);
            
            // recupération des ressources de l'appli en base
            List<Ressource> existList = ressourceRepository.findApplicationRessource(application.getId());
            
            // tri pour rapprochement rapide des valeurs
            List<Ressource> toDeleteList = new ArrayList<Ressource>(existList);
            Collections.sort(toDeleteList, RessourceComparator.INSTANCE);
            
            // reconstitution des objects métier à partir des DTO Iam
            List<Ressource> toSaveList = new ArrayList<Ressource>(toSaveIamList.size());
            for (IamRessource iamRess : toSaveIamList) {
                
                Ressource key = new Ressource();
                key.setId(iamRess.getId());
                
                Ressource res = null;
                int idx = Collections.binarySearch(toDeleteList, key, RessourceComparator.INSTANCE);
                if (idx>=0) {
                    // element existant
                    Ressource resDb =toDeleteList.get(idx);
                    res = MapperHelper.merge(iamRess, application, resDb);
                    toDeleteList.remove(idx);
                } else {
                    // creation
                    res = MapperHelper.merge(iamRess, application, null);
                }
                                
                toSaveList.add(res);
            }
            
            // sauvegarde
            LOGGER.debug("element to save {}", toSaveList);
            ressourceRepository.save(toSaveList);
            
            if (!toDeleteList.isEmpty()) {
                LOGGER.debug("element to delete {}", toDeleteList);
                for (Ressource ressource : toDeleteList) {
                    ressourceValeurRepository.deleteByResourceId(ressource.getId());
                }
                ressourceRepository.delete(toDeleteList);
            }
            
            // refresh from database
            ret = findResources(iamRessource.getCode());
        }
        return ret;
    }
    
    


    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#computeInterval(com.github.cunvoas.iam.bo.IamRessource)
     */
    @Override
    public final void computeInterval(IamRessource iamRessource){
        // only if root node
        if (iamRessource.getBorneInf()!=null && BORNE_START == iamRessource.getBorneInf()
                || iamRessource.getParent()==null) {
            
            int intervale = BORNE_START;
            iamRessource.setBorneInf(intervale++);
            
            if (iamRessource.avecEnfant()) {
                intervale = computeIntervalR(iamRessource.getEnfants(), intervale);
            }
            iamRessource.setBorneSup(intervale++);
        }
    }
    
    
    /**
     * ReCompute Interval values of children.
     * 
     * @param children
     * @param borneInf
     * @return tmpSup
     */
    private final int computeIntervalR(List<IamRessource> children, int intervale){
        for (IamRessource iamRessource : children) {
            
            iamRessource.setBorneInf(intervale++);
            
            if (iamRessource.avecEnfant()) {
                intervale = computeIntervalR(iamRessource.getEnfants(), intervale) ;
            }
            iamRessource.setBorneSup(intervale++);
        }
        return intervale;
    }
        
    /**
     * Comparaor for fast search.
     * @author cunvoas
     */
    static class RessourceComparator implements Comparator<Ressource> {
        private static final RessourceComparator INSTANCE = new RessourceComparator();
        
        /** 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(Ressource o1, Ressource o2) {
            CompareToBuilder ctb = new CompareToBuilder();
            ctb.append(o1.getId(), o2.getId());
            return ctb.toComparison();
        }
        
    }


    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#read(java.lang.String)
     */
    @Override
    public IamRessource findResources(String applicationCode) {
        IamRessource iamRessource = null;
        IamRessource iamCurrent = null;
        
        Application application = applicationRepository.findByCode(applicationCode);
        if (application!=null) {
            List<Ressource> ress =ressourceRepository.findApplicationRessource(application.getId());
            
            for (Ressource ressource : ress) {
                IamRessource tmpRessource = MapperHelper.map(ressource, null);
                

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("IamRessource in DB {}", tmpRessource);
                }

                // génération des fils pour l'arbre
                if(tmpRessource.getBorneInf() == 1) {
                    iamRessource = tmpRessource;
                } else {
                    if(iamCurrent.getBorneInf() < tmpRessource.getBorneInf()) {
                        if(iamCurrent.getBorneSup() > tmpRessource.getBorneSup())  {
                            iamCurrent.addEnfant(tmpRessource);
                        } else  {
                            for (; tmpRessource.getBorneSup() > iamCurrent.getBorneSup(); iamCurrent = iamCurrent.getParent());
                            iamCurrent.addEnfant(tmpRessource);
                        }
                    }
                }
                
                if (tmpRessource.isNode() && (tmpRessource.getBorneSup() - tmpRessource.getBorneInf() > 1 || tmpRessource.getBorneInf() == 1)) {
                    iamCurrent = tmpRessource;
                } else {
                    iamCurrent = tmpRessource.getParent();
                }
                
            }
        }
        return iamRessource;
    }
    
    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#getRessourceValueTree(com.github.cunvoas.iam.bo.IamApplication, com.github.cunvoas.iam.bo.IamRole)
     */
    public IamRessource getRessourceValueTree(IamApplication application, IamRole role) {
        IamRessource iamRessource = null;
        IamRessource iamCurrent = null;

        List<Object[]> datasList = ressourceRepository
                .findApplicationAllRessourceValeurs(
                        application.getId(), role.getId());

        for (Object[] objects : datasList) {
            Ressource ressource = (Ressource) objects[0];
            RessourceValeur valeur = (RessourceValeur) objects[1];
            IamRessource tmpRessource = MapperHelper.map(ressource, valeur);
            
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("IamRessource in DB {}", tmpRessource);
            }

            // génération des fils pour l'arbre
            if(tmpRessource.getBorneInf() == 1) {
                iamRessource = tmpRessource;
            } else {
                if(iamCurrent.getBorneInf() < tmpRessource.getBorneInf()) {
                    if(iamCurrent.getBorneSup() > tmpRessource.getBorneSup())  {
                        iamCurrent.addEnfant(tmpRessource);
                    } else  {
                        for (; tmpRessource.getBorneSup() > iamCurrent.getBorneSup(); iamCurrent = iamCurrent.getParent());
                        iamCurrent.addEnfant(tmpRessource);
                    }
                }
            }
            
            if (tmpRessource.isNode() && (tmpRessource.getBorneSup() - tmpRessource.getBorneInf() > 1 || tmpRessource.getBorneInf() == 1)) {
                iamCurrent = tmpRessource;
            } else {
                iamCurrent = tmpRessource.getParent();
            }
            
        }

        logTree(iamRessource, 0);
        return iamRessource;
    }
    
    private static void logTree(IamRessource iamRessource, int deep) {
        
        if (LOGGER.isDebugEnabled()) {
            
            String padding="";
            if (deep>0) {
                StringBuilder sBuilder = new StringBuilder();
                for (int i = 0; i < deep; i++) {
                    sBuilder.append("\t");
                }
                
                padding=sBuilder.toString();
            }
            
            LOGGER.debug("IamRessource in TREE{} {}", padding, iamRessource);
            
            if (iamRessource.getEnfants()!=null ) {
                for (IamRessource child : iamRessource.getEnfants()) {
                    logTree(child, deep+1);
                }
            }
        }
        
    }
    
    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#findRessourceLeaf(java.lang.String)
     */
    @Override
    public List<IamRessource> findRessourceLeaf(String applicationCode) {
        List<IamRessource> iamRessources = new ArrayList<IamRessource>();
        
        
        IamRessource tree = findResources(applicationCode);
        if (tree!=null) {
             List<IamRessource> treeList = MapperHelper.asList(tree);
             
             for (IamRessource iamRessource : treeList) {
                 if (!iamRessource.isNode())    {
                        iamRessources.add(iamRessource);
                    }
            }
        }
        
        return iamRessources;
    }



    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#getReferenceResourceValue()
     */
    @Override
    public Map<Integer, String> getReferenceResourceValue() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        List<Valeur> vals = valeurRepository.findAll();
        
        for (Valeur val : vals) {
            map.put(val.getId(), val.getValeur());
        }
        return map;
    }
    
    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#getValues()
     */
    @Override
    public List<IamValue> getValues() {
        List<Valeur> vals = valeurRepository.findAll();
        List<IamValue> iamVals = new ArrayList<IamValue>();
        // add blank
        iamVals.add(new IamValue());
        for (Valeur valeur : vals) {
            iamVals.add(new IamValue(valeur.getId(), valeur.getValeur()));
        }
        return iamVals;
    }


    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#validate(com.github.cunvoas.iam.bo.IamRessource)
     */
    @Override
    public void validate(IamApplication iamApplication, IamRessource iamRessource) throws IamException {
            
        if (iamRessource!=null && iamApplication!=null && iamApplication.getCode()!=null) {
            if (!iamApplication.getCode().equals(iamRessource.getCode())) {
                String[] details = {iamApplication.getCode()};
                throw new IamException(IamExceptionContants.VALID_FIRST_NODE_LIKE_APPLICATION, details) ;
            }
            List<IamRessource> iamRessList = MapperHelper.asList(iamRessource);
            for (IamRessource iamRessource2 : iamRessList) {
                checkUniqueChildCode(iamRessource2.getEnfants());
            }
        } else {
            throw new IamException(IamExceptionContants.VALID_IMPOSSIBLE_TO_CHECK) ;
        }
    }
    
    
    private  static void checkUniqueChildCode(List<IamRessource> listRess) throws IamException {
        if (listRess!=null && !listRess.isEmpty() && listRess.size()!=1) {
            List<String> codesList = new ArrayList<String>();
            for (IamRessource iamRess : listRess) {
                String code = iamRess.getCode();
                
                if (StringUtils.isBlank(code)) {
                    String[] details = {iamRess.getVectorCode()};
                    throw new IamException(IamExceptionContants.VALID_EMPTY_CODE, details) ;
                }
                
                if (codesList.contains(code)) {
                    String[] details = {iamRess.getVectorCode()};
                    throw new IamException(IamExceptionContants.VALID_NON_UNIQUE_CODE, details) ;
                }
                codesList.add(code);
            }
        }
        
    }

    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#rename(com.github.cunvoas.iam.bo.IamRessource)
     */
    @Override
    public IamRessource rename(IamRessource renamed) {
        if (renamed.getId()==null ) {
            throw new IamException(IamExceptionContants.VALID_EMPTY_ID);
        } else if (StringUtils.isBlank(renamed.getCode())) {
            throw new IamException(IamExceptionContants.VALID_EMPTY_CODE);
        }
        
        Ressource resource = ressourceRepository.findOne(renamed.getId());
        resource.setCode(renamed.getCode().toUpperCase().trim());
        ressourceRepository.save(resource);
        
        return renamed;
    }

    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#move(com.github.cunvoas.iam.bo.IamApplication, java.lang.Integer, java.lang.Integer, java.lang.Integer, int)
     */
    @Override
    public IamRessource move(IamApplication application, Integer itemId, Integer oldParentId, Integer newParentId, int newIdx) {
        
        // generate obj from ids
        IamRessource original = new IamRessource();
        original.setId(itemId);
        IamRessource oldParent = new IamRessource();
        oldParent.setId(oldParentId);
        original.setParent(oldParent);
        
        IamRessource newParent = new IamRessource();
        newParent.setId(newParentId);
        
        // search in persistence
        IamRessource tree = this.findResources(application.getCode());
        
        // populate objects
        IamRessource parentOrg = this.findParentInTree(tree, original);
        IamRessource originalRess = this.findResourceInTree(tree, original);
        
        // choose mode
        boolean moveInSaveNode = original.getParent().equals(newParent);
        
        if (moveInSaveNode) {
            // change position in same node
            parentOrg.getEnfants().remove(originalRess);
            parentOrg.getEnfants().add(newIdx, originalRess);
            
        } else {
            // change parent Node
            IamRessource parentNew= findResourceInTree(tree, newParent);
            parentOrg.getEnfants().remove(originalRess);
            parentNew.addEnfant(newIdx, originalRess);
        }

        // save tree structure
        IamRessource savedIamRoot = saveRoot(tree, application.getCode());
        
        // reload persisted
        IamRessource savedIam = this.findResourceInTree(savedIamRoot, original);
        return savedIam;
    }


    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#add(com.github.cunvoas.iam.bo.IamApplication, com.github.cunvoas.iam.bo.IamRessource, int)
     */
    @Override
    public IamRessource add(IamApplication application, IamRessource add, int newIdx) {
        
        if (add==null) {
            throw new IamException("RULE_03_012");
        } else if (add.getParent()==null) {
            throw new IamException("RULE_03_011");
        }
        
        Integer parentId = add.getParent().getId();
        
        IamRessource tree = this.findResources(application.getCode());
        
        IamRessource parentKey = new IamRessource();
        parentKey.setId(parentId);
        
        IamRessource parentOrg = findResourceInTree(tree, parentKey);
        parentOrg.addEnfant(newIdx, add);
        
        IamRessource savedTree = saveRoot(tree, application.getCode());
       
        // search added node ion tree structure.
        IamRessource searched = new IamRessource();
        searched.setId(parentId);
        IamRessource parent = this.findResourceInTree(savedTree, searched);
        IamRessource added = null;
        for (IamRessource child : parent.getEnfants()) {
            if (child.getCode().equals(add.getCode())) {
                added = child;
                break;
            }
        }
        
        return added;
    }
    
    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#remove(com.github.cunvoas.iam.bo.IamApplication, com.github.cunvoas.iam.bo.IamRessource)
     */
    @Override
    public void remove(IamApplication application, IamRessource removed) {
        
        Ressource resource = ressourceRepository.findOne(removed.getId());
        if (resource.getBorneSup()-resource.getBorneInf()>1) {
            throw new IamException("RULE_03_001");
        }
        IamRessource tree = findResources(application.getCode());
        
        IamRessource parent= findParentInTree(tree, removed);
        parent.getEnfants().remove(removed);
        
        saveRoot(tree, application.getCode());
    }
    
    /**
     * Get the parent of searched Resource.
     * @param tree
     * @param searched
     * @return
     */
    protected IamRessource findParentInTree(IamRessource tree, IamRessource searched) {
        IamRessource parent = null;
        if (tree.getParent()==null && tree.equals(searched)) {
            throw new IamException("RULE_03_002");
        }
        
        if(tree.getEnfants()!=null && !tree.getEnfants().isEmpty()) {
            if (tree.getEnfants().contains(searched)) {
                parent = tree;
            } else {
                for (IamRessource child : tree.getEnfants()) {
                    IamRessource tmp = findParentInTree(child, searched);
                    if (tmp!=null) {
                        parent = tmp;
                        break;
                    }
                }
            }
        }
        return parent;
    }

    /**
     * Get the parent of searched Resource.
     * @param tree
     * @param searched
     * @return
     */
    protected IamRessource findResourceInTree(IamRessource tree, IamRessource searched) {
        IamRessource find = null;
        
        if (tree.equals(searched)) {
            find = tree;
        } else {
            if(tree.getEnfants()!=null && !tree.getEnfants().isEmpty()) {
                for (IamRessource child : tree.getEnfants()) {
                    IamRessource tmp = findResourceInTree(child, searched);
                    if (tmp!=null) {
                        find = tmp;
                        break;
                    }
                }
            }
        }
        return find;
    }


    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#findResourcesAndValues(com.github.cunvoas.iam.bo.IamApplication, com.github.cunvoas.iam.bo.IamRole)
     */
    @Override
    public IamRessource findResourcesAndValues(IamApplication application, IamRole role) {
        
        IamRessource root = this.findResources(application.getCode());

        List<Object[]> datasList = ressourceRepository
                .findApplicatioRessourceValeurs(
                        application.getId(), role.getId());
        
        if (datasList!=null && !datasList.isEmpty()) {
            // items from tree as list
            List<IamRessource> listItems = MapperHelper.asList(root);
            
            // binary search preparation
            Collections.sort(listItems, IamRessourceComparator.INSTANCE);
            IamRessource keyResVal = new IamRessource();
            
            // for each datum
            for (Object[] resVal : datasList) {
                Ressource ressource = (Ressource)resVal[0];
                RessourceValeur resval = (RessourceValeur)resVal[1];
                
                keyResVal.setId(ressource.getId());
                
                int index=Collections.binarySearch(listItems, keyResVal, IamRessourceComparator.INSTANCE);
                if (index>=0) {
                    IamRessource ress = listItems.get(index);
                    ress.setValeur(resval.getValue().getId());
                }
            }
        }
        return root;
    }

    static class IamRessourceComparator implements Comparator<IamRessource> {

        private static final IamRessourceComparator INSTANCE = new IamRessourceComparator();
        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(IamRessource o1, IamRessource o2) {
            int ret = 0;
            if (o1!=null && o1.getId()!=null && o2!=null) {
                ret=o1.getId().compareTo(o2.getId());
            }
            return ret;
        }
        
    }


    /**
     * @see com.github.cunvoas.iam.service.ServiceIamRessource#saveResourceValue(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public IamValue saveResourceValue(String applicationCode,Integer resId, Integer roleId, Integer valId) {
        IamValue retIam = null;
        
        Valeur valDbValeur = valeurRepository.findOne(valId);
        if (valDbValeur==null) {
            throw new IamException("RULE_03_007");
        }
        
        Role role = roleRepository.findOne(roleId);
        if (role==null) {
            throw new IamException("RULE_03_008");
        }
        Ressource ressource = ressourceRepository.findOne(resId);
        if (ressource==null) {
            throw new IamException("RULE_03_009");
        }
        Application application = applicationRepository.findByCode(applicationCode);
        if (application!=null 
                && !application.equals(role.getApplication()) && !application.equals(ressource.getApplication())) {
            throw new IamException("RULE_03_010");
        }
        
        RessourceValeurPK pk = new RessourceValeurPK();
        pk.setResId(resId);
        pk.setRolId(roleId);
        RessourceValeur resval = ressourceValeurRepository.findById(pk);
        
        if (resval==null) {
            resval = new RessourceValeur();
            resval.setId(pk);
        }
        
        resval.setValue(valDbValeur);
        ressourceValeurRepository.save(resval);
        retIam = new IamValue(valDbValeur.getId(), valDbValeur.getValeur());
        return retIam;
    }
}
