package com.github.cunvoas.iam.security.helper;

import java.util.Comparator;

import com.github.cunvoas.iam.bo.IamRawRessource;

/**
 * @author CUNVOAS
 */
public class IamRawRessourceComparator implements Comparator<IamRawRessource> {

    public static final IamRawRessourceComparator INSTANCE = new IamRawRessourceComparator();

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(IamRawRessource o1, IamRawRessource o2) {
        return o1.getVectorCode().compareTo(o2.getVectorCode());
    }

}
