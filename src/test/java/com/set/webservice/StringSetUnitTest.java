package com.set.webservice;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import com.set.model.SetHelper;
import com.set.model.SetStatistics;

import junit.framework.AssertionFailedError;

import org.junit.Test;

/**
 * Unit Tests for StringSetOperationService.
 * @author 
 */
public class StringSetUnitTest {
	
	private static final List<String> sampleList = Arrays.asList(new String[]  { "abc", "cdf", "fgh", "hij" });
	private static final Set<String> sampleSet = new LinkedHashSet(sampleList);
	
	private StringSetOpnService createStringSetOpnService() {
        SetHelper.setHelperObj.clear();
        StringSetOpnService result = new StringSetOpnService();
        return result;
    }
    
	/**
     * Test the upload endpoint
     */
    @Test
    public void testUpload() {
        StringSetOpnService res = createStringSetOpnService();
        Map<Integer, Set<String>> list = res.list();
        assertTrue(list.isEmpty());
        int id = res.upload(sampleList);
        list = res.list();
        assertEquals(1, list.size());
        assertEquals((Integer)id, list.keySet().iterator().next());
        assertEquals(sampleList, new ArrayList<>(list.values().iterator().next()));
        int id2 = res.upload(sampleList);
        assertNotEquals(id, id2);
        assertEquals(2, res.list().size());
        assertEquals(sampleSet, res.get(id));
        assertEquals(sampleSet, res.get(id2));
        
        try {
            id = res.upload(Collections.emptyList());
            throw new AssertionFailedError("Uploaded an empty list");
        } catch (BadRequestException ex) {
        }
        try {
            id = res.upload(Collections.singletonList(""));
            throw new AssertionFailedError("Uploading a list containing an empty string");
        } catch (BadRequestException ex) {
        }
        try {
            id = res.upload(Arrays.asList(new String[] {"a", "a"}));
            throw new AssertionFailedError("Duplicate strings contained in the uploaded list");
        } catch (BadRequestException ex) {
        }
    }
    
    /**
     * Test the search endpoint.
     */
    @Test
    public void testSearch() {
    	StringSetOpnService res = createStringSetOpnService();
        int idA = res.upload(Collections.singletonList("a"));
        int idB = res.upload(Collections.singletonList("b"));
        int idC = res.upload(Collections.singletonList("c"));
        int idC2 = res.upload(Collections.singletonList("c"));

        assertEquals(Collections.singletonList(idA), res.search("a"));
        assertEquals(Collections.singletonList(idB), res.search("b"));
        assertEquals(Arrays.asList(new Integer[] {idC, idC2}), res.search("c"));
        assertEquals(Collections.emptyList(), res.search("d"));
    }
    
    /**
     * Test the delete endpoint.
     */
    @Test
    public void testDelete() {
    	StringSetOpnService res = createStringSetOpnService();
        int id = res.upload(sampleList);
        assertEquals(1, res.list().size());
        try {
            res.delete(id+1);
            throw new AssertionFailedError("Deleting non existant string set");
        } catch (NotFoundException ex) {
        }
        assertEquals(sampleSet, res.delete(id));
    }
    
    /**
     * Test the Statistics endpoint.
     */
    @Test
    public void testSetStatistic() {
    	StringSetOpnService res = createStringSetOpnService();
        int id = res.upload(sampleList);
        SetStatistics stats = res.setStatistics(id);
        assertEquals(4, stats.getCount());
        assertEquals(3, stats.getShortestLength());
        assertEquals(3, stats.getLongestLength());
        assertEquals(3D, stats.getAverageLength(), 0D);
        assertEquals(3D, stats.getMedianLength(), 0D);
        id = res.upload(Arrays.asList(new String[] { "a", "aa", "aaa", "aaaa", "aaaaa" }));
        stats = res.setStatistics(id);
        assertEquals(5, stats.getCount());
        assertEquals(1, stats.getShortestLength());
        assertEquals(5, stats.getLongestLength());
        assertEquals(3D, stats.getAverageLength(), 0D);
        assertEquals(3D, stats.getMedianLength(), 0D);
        id = res.upload(Arrays.asList(new String[] { "a", "aa", "aaa", "aaaa" }));
        stats = res.setStatistics(id);
        assertEquals(4, stats.getCount());
        assertEquals(1, stats.getShortestLength());
        assertEquals(4, stats.getLongestLength());
        assertEquals(2.5D, stats.getAverageLength(), 0D);
        assertEquals(2.5D, stats.getMedianLength(), 0D);
    }
    
    /**
     * Test the most_common endpoint.
     */
    @Test
    public void testMostCommon() {
    	StringSetOpnService res = createStringSetOpnService();
        int idA = res.upload(Arrays.asList(new String[] { "a", "b", "c", "d", "e" }));
        int idB = res.upload(Arrays.asList(new String[] { "a", "b", "c", "d" }));
        int idC = res.upload(Arrays.asList(new String[] { "a", "b", "c" }));
        int idD = res.upload(Arrays.asList(new String[] { "a", "b" }));
        int idE = res.upload(Arrays.asList(new String[] { "a" }));
        assertEquals(Collections.singletonList("a"), res.mostCommon());
        res.delete(idE);
        assertEquals(Arrays.asList("a", "b"), res.mostCommon());
        res.delete(idD);
        assertEquals(Arrays.asList("a", "b", "c"), res.mostCommon());
    }
    
    /**
     * Test the longest endpoint.
     */
    @Test
    public void testLongest() {
    	StringSetOpnService res = createStringSetOpnService();
        int idA = res.upload(Arrays.asList(new String[] { "a", "aa", "aaa", "aaaa"}));
        int idB = res.upload(Arrays.asList(new String[] { "b", "bb", "bbb", "bbbb"}));
        int idC = res.upload(Arrays.asList(new String[] { "c", "cc", "ccc", "cccc"}));
        int idD = res.upload(Arrays.asList(new String[] { "d", "dd", "ddd" }));
        assertEquals(Arrays.asList(new String[] {"aaaa", "bbbb", "cccc"}), res.longest());
        res.delete(idB);
        assertEquals(Arrays.asList(new String[] {"aaaa", "cccc"}), res.longest());
    }
    
    /**
     * Test the exactly_in endpoint.
     */
    @Test
    public void testExactlyIn() {
    	StringSetOpnService res = createStringSetOpnService();
        int idA = res.upload(Arrays.asList(new String[] { "a", "b", "c", "d", "e" }));
        int idB = res.upload(Arrays.asList(new String[] { "a", "b", "c", "d" }));
        int idC = res.upload(Arrays.asList(new String[] { "a", "b", "c" }));
        int idD = res.upload(Arrays.asList(new String[] { "a", "b" }));
        int idE = res.upload(Arrays.asList(new String[] { "a" }));
        assertEquals(Collections.emptyList(), res.exactlyIn(6));
        assertEquals(Collections.singletonList("a"), res.exactlyIn(5));
        assertEquals(Collections.singletonList("b"), res.exactlyIn(4));
        assertEquals(Collections.singletonList("c"), res.exactlyIn(3));
        assertEquals(Collections.singletonList("d"), res.exactlyIn(2));
        assertEquals(Collections.singletonList("e"), res.exactlyIn(1));
    }
    
    /**
     * Test the create_intersection endpoint.
     */
    @Test
    public void testCreateIntersection() {
    	StringSetOpnService res = createStringSetOpnService();
        int idA = res.upload(sampleList);
        int idB = res.upload(sampleList);
        int idC = res.createIntersection(idA, idB);
        assertEquals(sampleSet, res.get(idC));
        try {
            res.createIntersection(-1, idA);
            throw new AssertionFailedError("invalid string set id");
        } catch (BadRequestException ex) {
        }
        try {
            res.createIntersection(idA, -1);
            throw new AssertionFailedError("invalid string set id");
        } catch (BadRequestException ex) {
        }
        int idD = res.upload(Arrays.asList(new String[] { "e", "f", "g", "h" }));
        try {
            res.createIntersection(idA, idD);
            throw new AssertionFailedError("Error: Empty string set creation");
        } catch (BadRequestException ex) {
        }
        int idE = res.upload(Arrays.asList(new String[] { "h", "g", "f", "e" }));
        int idF = res.createIntersection(idD, idE);
        int idG = res.createIntersection(idE, idD);

        assertEquals(Arrays.asList(new String[] { "e", "f", "g", "h" }), new ArrayList<>(res.get(idF)));
        assertEquals(Arrays.asList(new String[] { "h", "g", "f", "e" }), new ArrayList<>(res.get(idG)));
    }
}
