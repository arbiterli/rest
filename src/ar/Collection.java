package ar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

@Path("collection")
public class Collection {
	private static Map<String, StringSet> sets = new HashMap<String, StringSet>();
	
	@POST
	@Path("/upload")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes("application/x-www-form-urlencoded")
	public String upload(@FormParam("id") String id, @FormParam("strings") String strings) { // separated by ','
		StringSet stringSet = sets.get(id);
		if(stringSet != null) {
			return "this set already exists!";
		} else {
			stringSet = new StringSet(id);
			for(String s : strings.split(",")) {
				boolean re = stringSet.add(s);
				if(re == false) {
					return "invalid set";
				}
			}
			sets.put(id, stringSet);
			return "success";
		}
	}
	
	@GET
	@Path("/show/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String show(@PathParam("id") String id) {
		StringSet set = sets.get(id);
		if(set == null) {
			return "no set";
		} else {
			return set.showAll();
		}
	}

	@GET
	@Path("/search/{string}")
	@Produces(MediaType.TEXT_PLAIN)
	public String search(@PathParam("string") String string) {
		StringBuilder sb = new StringBuilder();
		for(String id : sets.keySet()) {
			StringSet stringSet = sets.get(id);
			if(stringSet.contains(string)) {
				sb.append(id + "\n");
			}
		}
		return sb.toString();
	}
	
	@GET
	@Path("/delete/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String delete(@PathParam("id") String id) {
		StringSet stringSet = sets.get(id);
		if(stringSet == null){
			return "no this set";
		} else {
			sets.remove(id);
			return "success";
		}
	}
	
	@GET
	@Path("/set_statistic/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String statistic(@PathParam("id") String id) {
		return sets.get(id).getStatistic();
	}
	
	@GET
	@Path("/most_common")
	@Produces(MediaType.TEXT_PLAIN)
	public String common() {
		int occurNum = 0;
		List<String> mostCommon = new ArrayList<String>();
		Map<String, Integer> countMap = new HashMap<String, Integer>();
		for(StringSet stringSet : sets.values()) {
			for(Set<String> set : stringSet.getStrings().values()) {
				for(String string : set) {
					Integer count = countMap.get(string);
					count = count == null ? 1 : count + 1;
					countMap.put(string, count);
					if(occurNum == count) {
						mostCommon.add(string);
					} else if(count > occurNum) {
						mostCommon.clear();
						mostCommon.add(string);
						occurNum = count;
					}
				}
			}
		}
		StringBuilder re = new StringBuilder();
		for(String s : mostCommon) {
			re.append(s + "\n");
		}
		return re.toString();
	}
	
	@GET
	@Path("/longest")
	@Produces(MediaType.TEXT_PLAIN)
	public String longest() {
		int longest = 0;
		Set<String> longestStrings = null;
		for(StringSet stringSet : sets.values()) {
			int length = stringSet.getLongestLength();
			if(length > longest) {
				longest = length;
				longestStrings = stringSet.getStrings(longest);
			}
		}
		StringBuilder re = new StringBuilder();
		for(String s : longestStrings) {
			re.append(s + "\n");
		}
		return re.toString();
	}
	
	@GET
	@Path("/create_intersection/{id1}/{id2}")
	@Produces(MediaType.TEXT_PLAIN)
	public String intersection(@PathParam("id1") String id1, @PathParam("id2") String id2) {
		StringSet set1 = sets.get(id1);
		StringSet set2 = sets.get(id2);
		if(set1 == null || set2 == null) {
			return "no these sets!";
		}
		StringSet set3 = new StringSet(id1 + "x" + id2); 
		for(Set<String> set : set2.getStrings().values()) {
			for(String string : set) {
				if(set1.contains(string)) {
					set3.add(string);
				}
			}
		}
		if(!sets.containsKey(id1 + "x" + id2)) {
			sets.put(id1 + "x" + id2, set3);
		} else {
			return "already exist!";
		}
		return set3.showAll();
	}
	
	@GET
	@Path("/longest_chain")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLongestChain() {
		
	}
}
