package com.set.webservice;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.set.model.SetHelper;
import com.set.model.SetStatistics;
import com.set.model.StringSet;

@Path("/set")
@Produces(MediaType.APPLICATION_JSON)
public class StringSetOpnService {
	
	/**
     * Get the object of StringSetHelper class.
     * @return our data store.
     */
    protected SetHelper getSetHelperObj() {
        return SetHelper.setHelperObj;
    }
    
    /**
     * Get all the uploaded string sets.
     * 
     * @return a map of all the data in the system with ids as keys, and string sets as values
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Integer,Set<String>> list() {
        return getSetHelperObj().getMap();
    }
    
    /**
     * Upload a string set.
     * 
     * @param strings the strings to upload
     * @return the id of the newly created string set.
     * @throws BadRequestException If an uploaded string set contains duplicate strings
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/upload")
    public int upload(@QueryParam("strings") List<String> strings) {
        Set<String> set = new LinkedHashSet<String>();
        for (String s : strings) {
            if (set.add(s)) continue;
            throw new BadRequestException("Duplicated string: " + s);
        }
        try {
            return getSetHelperObj().create(set);
        } catch (IllegalArgumentException ex) {
        	throw new BadRequestException(ex.getMessage());
        }
    }
    
    /**
     * Get a string set by it's id
     * @param id
     * @return the string set
     * @throws NotFoundException if the string set does not exist
     */
    @GET
    @Path("/{id}")
    public Set<String> get(@PathParam("id") int id) {
        StringSet set = getSetHelperObj().get(id);
        if (set == null) {        	
        	throw new NotFoundException();
        }
        else
            return set.getSet();
    }
    
    /**
     * Search for string sets containing a specific string.
     * 
     * @param search the string to search for in string sets
     * @return a list of string set ids which contain the search string
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/search")
    
    public List<Integer> search(@QueryParam("searchString") String searchString) {
        return getSetHelperObj().search(searchString);
    }
    
    /**
     * Delete a string set by it's id
     * @param id
     * @return the deleted string set
     * @throws NotFoundException if the string set does not exist
     */
    @GET
    @Path("{id}/delete")
    public Set<String> delete(@PathParam("id") int id) {
        StringSet deleted = getSetHelperObj().delete(id);
        if(deleted == null) {
            throw new NotFoundException();
        } else {
            return deleted.getSet();
        }
    }
    
    /**
     * Get statistics for a string set.
     * @param id the id of the string set
     * @return statistics on the string set
     */
    @GET
    @Path("{id}/set_statistic")
    public SetStatistics setStatistics(@PathParam("id") int id) {
        StringSet set = getSetHelperObj().get(id);
        if (set == null) return null;
        else return set.getStatistics();
    }
    
    /**
     * Get an alphabetically sorted list of the most common strings in string sets.
     * 
     * @return an alphabetically sorted list of the most common strings in string sets
     */
    @GET
    @Path("most_common")
    public List<String> mostCommon() {
        return getSetHelperObj().getMostCommon();
    }
    
    /**
     * Get an alphabetically sorted list of the longest strings in string sets.
     * 
     * @return an alphabetically sorted list of the longest strings in string sets
     */
    @GET
    @Path("longest")
    public List<String> longest() {
        return getSetHelperObj().getLongest();
    }
    
    /**
     * Get an alphabetically sorted list of strings in exactly count string sets.
     * 
     * @param count the numer of string sets the string should be in
     * @return an alphabetically sorted list of strings exactly in count string sets
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("exactly_in")
    public List<String> exactlyIn(@QueryParam("count") int count) {
        return getSetHelperObj().getExactlyIn(count);
    }
    
    /**
     * Create a new string set as an intersection of two existing string sets.
     * 
     * @param a id of first string set
     * @param b id of second string set
     * @return id of newly created string set
     * @throws BadRequestException if either id does not exist, or if the created string set would be empty
     */
    @GET
    @Path("{ida}/{idb}/create_intersection")
    public int createIntersection(@PathParam("ida") int a, @PathParam("idb") int b) {
        try {
            return getSetHelperObj().createIntersection(a, b);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}