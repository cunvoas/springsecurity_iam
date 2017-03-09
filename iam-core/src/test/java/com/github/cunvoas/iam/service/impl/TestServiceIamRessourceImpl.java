package com.github.cunvoas.iam.service.impl;

import java.util.Iterator;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.cunvoas.iam.bo.IamRessource;
import com.github.cunvoas.iam.service.ServiceIamRessource;
import com.github.cunvoas.iam.service.mapper.MapperHelper;

public class TestServiceIamRessourceImpl {
    int genCall=0;
    int nodeCount=0;
    
    private ServiceIamRessource tested;
    
    private IamRessource iamRessource;

    @Before
    public void setUp() throws Exception {
        tested = new ServiceIamRessourceImpl();

        iamRessource = new IamRessource();
        iamRessource.setCode("ROOT");
        
        IamRessource child = new IamRessource();
        child.setCode("NODE_1");
        iamRessource.addEnfant(child);
        
        IamRessource child2 = new IamRessource();
        child2.setCode("NODE_11");
        child.addEnfant(child2);
        child2 = new IamRessource();
        child2.setCode("NODE_12");
        child.addEnfant(child2);
        
        child = new IamRessource();
        child.setCode("NODE_2");
        iamRessource.addEnfant(child);
        
        child2 = new IamRessource();
        child2.setCode("NODE_21");
        child.addEnfant(child2);
        child2 = new IamRessource();
        child2.setCode("NODE_22");
        child.addEnfant(child2);
        
    }

    @Test
    public void testComputeIntervalPerf() {
        long start;
        long stop;
        int timeMS;
        int nChild=0;
        int dDepth=0;

        // depth = 3 <10ms
        genCall=0;
        nodeCount=0;
        nChild=3;
        dDepth=3;
        start = System.nanoTime();
        IamRessource root = new IamRessource();
        root.setCode("ROOT");
        root = generate(root, nChild, dDepth, 0);
        stop = System.nanoTime();
        Assert.assertEquals("nb calls", 40, genCall);
        Assert.assertEquals("nb nodes", 120, nodeCount);
        timeMS= Double.valueOf((stop-start)/1E6).intValue();
        MatcherAssert.assertThat(String.format("duration %sx%s µs", nChild, dDepth),
                timeMS, Matchers.lessThanOrEqualTo(10));
        
        genCall=0;
        nodeCount=0;
        nChild=4;
        dDepth=3;
        start = System.nanoTime();
         root = new IamRessource();
        root.setCode("ROOT");
        root = generate(root, nChild, dDepth, 0);
        stop = System.nanoTime();
        Assert.assertEquals("nb calls", 85, genCall);
        Assert.assertEquals("nb nodes", 340, nodeCount);
        timeMS= Double.valueOf((stop-start)/1E6).intValue();
        MatcherAssert.assertThat(String.format("duration %sx%s µs", nChild, dDepth),
                timeMS, Matchers.lessThanOrEqualTo(10));
        
        // depth = 4 <50ms
        genCall=0;
        nodeCount=0;
        nChild=4;
        dDepth=4;
        start = System.nanoTime();
        root = new IamRessource();
        root.setCode("ROOT");
        root = generate(root, nChild, dDepth, 0);
        stop = System.nanoTime();
        Assert.assertEquals("nb calls", 341, genCall);
        Assert.assertEquals("nb nodes", 1364, nodeCount);
        timeMS= Double.valueOf((stop-start)/1E6).intValue();
        MatcherAssert.assertThat(String.format("duration %sx%s µs", nChild, dDepth),
                timeMS, Matchers.lessThanOrEqualTo(50));
        
        genCall=0;
        nodeCount=0;
        nChild=5;
        dDepth=4;
        start = System.nanoTime();
        root = new IamRessource();
        root.setCode("ROOT");
        root = generate(root, nChild, dDepth, 0);
        stop = System.nanoTime();
        Assert.assertEquals("nb calls", 781, genCall);
        Assert.assertEquals("nb nodes", 3905, nodeCount);
        timeMS= Double.valueOf((stop-start)/1E6).intValue();
        MatcherAssert.assertThat(String.format("duration %sx%s µs", nChild, dDepth),
                timeMS, Matchers.lessThanOrEqualTo(50));

        // depth = 5 <150ms
        genCall=0;
        nodeCount=0;
        nChild=5;
        dDepth=5;
        start = System.nanoTime();
        root = new IamRessource();
        root.setCode("ROOT");
        root = generate(root, nChild, dDepth, 0);
        stop = System.nanoTime();
        Assert.assertEquals("nb calls", 3906, genCall);
        Assert.assertEquals("nb nodes", 19530, nodeCount);
        timeMS= Double.valueOf((stop-start)/1E6).intValue();
        MatcherAssert.assertThat(String.format("duration %sx%s µs", nChild, dDepth),
                timeMS, Matchers.lessThanOrEqualTo(200));

        genCall=0;
        nodeCount=0;
        nChild=6;
        dDepth=5;
        start = System.nanoTime();
        root = new IamRessource();
        root.setCode("ROOT");
        root = generate(root, nChild, dDepth, 0);
        stop = System.nanoTime();
        Assert.assertEquals("nb calls", 9331, genCall);
        Assert.assertEquals("nb nodes", 55986, nodeCount);
        timeMS= Double.valueOf((stop-start)/1E6).intValue();
        MatcherAssert.assertThat(String.format("duration %sx%s µs", nChild, dDepth),
                timeMS, Matchers.lessThanOrEqualTo(300));

        // depth = 6 <1500ms
        genCall=0;
        nodeCount=0;
        nChild=6;
        dDepth=6;
        start = System.nanoTime();
        root = new IamRessource();
        root.setCode("ROOT");
        root = generate(root, nChild, dDepth, 0);
        stop = System.nanoTime();
        Assert.assertEquals("nb calls", 55987, genCall);
        Assert.assertEquals("nb nodes", 335922, nodeCount);
        timeMS= Double.valueOf((stop-start)/1E6).intValue();
        MatcherAssert.assertThat(String.format("duration %sx%s µs", nChild, dDepth),
                timeMS, Matchers.lessThanOrEqualTo(1500));
        
        genCall=0;
        nodeCount=0;
        nChild=7;
        dDepth=6;
        start = System.nanoTime();
        root = new IamRessource();
        root.setCode("ROOT");
        root = generate(root, nChild, dDepth, 0);
        stop = System.nanoTime();
        Assert.assertEquals("nb calls", 137257, genCall);
        Assert.assertEquals("nb nodes", 960799, nodeCount);
        timeMS= Double.valueOf((stop-start)/1E6).intValue();
        MatcherAssert.assertThat(String.format("duration %sx%s µs", nChild, dDepth),
                timeMS, Matchers.lessThanOrEqualTo(7000));
        
    }
    
    private IamRessource generate(IamRessource ress, int nChild, int dDepth, int curDepth) {
        genCall++;
        for (int c =0; c < nChild;c++) {
            nodeCount++;
            IamRessource enfant = new IamRessource();
            enfant.setCode(String.format("N_%s_%s", curDepth, c));
            ress.addEnfant(enfant);
            
            if (curDepth<dDepth) {
                generate(enfant, nChild, dDepth, curDepth+1);
            }
        }
        return ress;
    }
    
    @Test
    public void testComputeInterval() {
        tested.computeInterval(iamRessource);
        List<IamRessource> testedList = MapperHelper.asList(iamRessource);
        
        String[][] results = {
                {"ROOT", "1", "14"},
                {"NODE_1", "2", "7"},
                {"NODE_11", "3", "4"},
                {"NODE_12", "5", "6"},
                {"NODE_2", "8", "13"},
                {"NODE_21", "9", "10"},
                {"NODE_22", "11", "12"}
        };
        
        Iterator<IamRessource> iter = testedList.iterator();
        for (String[] strings : results) {
            IamRessource res = iter.next();
            
            if (strings[0].equals(res.getCode())) {
                Assert.assertEquals("intervale "+strings[0]+" start", Integer.valueOf(strings[1]), res.getBorneInf());    
                Assert.assertEquals("intervale "+strings[0]+" end",Integer.valueOf(strings[2]), res.getBorneSup());    
            } else {
                Assert.fail(strings[0]);
            }
        }
        
        /*
        ROOT                1    14
            NODE_1            2    7
                NODE_11        3    4
                NODE_12        5    6
            NODE_2            8    13
                NODE_21        9    10
                NODE_22        11    12
        */
        /*
        IamRessource root = iamRessource;
        
        IamRessource node1 = iamRessource.getEnfants().get(0);
        IamRessource node11 = node1.getParent().getEnfants().get(0);
        IamRessource node12 = node1.getParent().getEnfants().get(1);
        
        IamRessource node2 = iamRessource.getEnfants().get(1);
        IamRessource node21 = node2.getParent().getEnfants().get(0);
        IamRessource node22 = node2.getParent().getEnfants().get(1);
        
        
        Assert.assertEquals("intervale ROOT start", Integer.valueOf(1) , root.getBorneInf());
        
        Assert.assertEquals("intervale NODE_1 start", Integer.valueOf(2), node1.getBorneInf());    
        Assert.assertEquals("intervale NODE_11 start", Integer.valueOf(3), node11.getBorneInf());    
        Assert.assertEquals("intervale NODE_11 end", Integer.valueOf(4), node11.getBorneSup());
        Assert.assertEquals("intervale NODE_12 start", Integer.valueOf(5), node12.getBorneInf());    
        Assert.assertEquals("intervale NODE_12 end", Integer.valueOf(6), node12.getBorneSup());
        Assert.assertEquals("intervale NODE_1 end", Integer.valueOf(7), node1.getBorneSup());

        
        Assert.assertEquals("intervale NODE_2 start", Integer.valueOf(8), node2.getBorneInf());    

        Assert.assertEquals("intervale NODE_21 start", Integer.valueOf(9), node21.getBorneInf());    
        Assert.assertEquals("intervale NODE_21 end", Integer.valueOf(10), node21.getBorneSup());
        Assert.assertEquals("intervale NODE_22 start", Integer.valueOf(11), node22.getBorneInf());    
        Assert.assertEquals("intervale NODE_22 end", Integer.valueOf(12), node22.getBorneSup());
        
        Assert.assertEquals("intervale NODE_2 end", Integer.valueOf(13), node2.getBorneSup());
        
        Assert.assertEquals("intervale ROOT end", Integer.valueOf(14), root.getBorneSup());
        */
        
    }

}
