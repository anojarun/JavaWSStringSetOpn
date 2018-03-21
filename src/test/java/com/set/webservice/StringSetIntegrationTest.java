package com.set.webservice;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.*;
import org.junit.Test;

import com.set.model.SetHelper;
import com.set.model.SetStatistics;

/**
 * Integration tests for StringSetResource
 * @author antony
 */
public class StringSetIntegrationTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(StringSetOpnService.class);
    }

    private static final List<String> sampleList = Arrays.asList(new String[] {"a", "b", "c"});
    private static final List<String> sampleMoreList = Arrays.asList(new String[] {"hij", "klm", "nop", "qrst"});
    private static final List<String> qrstList = Arrays.asList(new String[] {"qrst"});
    private static final List<String> sampleJoinedList = Stream.concat(sampleList.stream(), sampleMoreList.stream())
            .collect(Collectors.toList());
    private static final Set<String> sampleSet = new LinkedHashSet(sampleList);

    @Test
    public void testIntegration() {
    	// Clear full set of strings
        SetHelper.setHelperObj.clear();
        
        // Check the initial contents of the string set
        assertEquals(Collections.emptyMap(), target("set").request().get(Map.class));
        
        // Upload two set of strings
        int id = target("set/upload").queryParam("strings", "a", "b", "c").request().post(Entity.entity(sampleList, MediaType.APPLICATION_JSON), Integer.class);
        int id1 = target("set/upload").queryParam("strings", "hij", "klm", "nop", "qrst").request().post(Entity.entity(sampleMoreList, MediaType.APPLICATION_JSON), Integer.class);
        
        // Check the contents of the string set after uploading       
        assertEquals(sampleList, target(String.format("set/%d", id)).request().get(List.class));
        
        // Search for a particular string in the string set        
        assertEquals(Collections.singletonList((Integer)id), target("set/search").queryParam("searchString", "a").request().post(Entity.entity("a", MediaType.APPLICATION_JSON), List.class));
        
        // Check the statistics related to a particular string set        
        assertEquals(new SetStatistics(sampleSet), target(String.format("set/%d/set_statistic", id)).request().get(SetStatistics.class));
        
        // Find the most common strings in the set of strings        
        assertEquals(sampleJoinedList, target("set/most_common").request().get(List.class));
        
        // Find the longest string among the set of strings
        assertEquals(qrstList, target("set/longest").request().get(List.class));
        
        // Find the string that is present exactly in the given number of sets
        assertEquals(sampleJoinedList, target("set/exactly_in").queryParam("count", 1).request().post(Entity.entity((Integer)1, MediaType.APPLICATION_JSON), List.class));
        
        // Create a new string set by intersecting two uploaded sets
        int intersectionId = target(String.format("set/%d/%d/create_intersection", id, id)).request().get(Integer.class);
        assertEquals(sampleList, target(String.format("set/%d", intersectionId)).request().get(List.class));
        
        // Delete a given set of strings
        assertEquals(sampleList, target(String.format("set/%d/delete", intersectionId)).request().get(List.class));        
    }
    
}
