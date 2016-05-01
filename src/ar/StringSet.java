package ar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StringSet {
	private String id;
	private int size = 0;;
	private Map<Integer, Set<String>> strings = new TreeMap<Integer, Set<String>>();
	
	public StringSet(String id) {
		this.id = id;
	}
	
	public boolean add(String s) {
		Set<String> set = strings.get(s.length());
		if(set == null) {
			set = new HashSet<String>();
		}
		if(set.contains(s)) {
			return false;
		} else {
			set.add(s);
			
			size++;
			strings.put(s.length(), set);
			return true;
		}
	}
	
	public boolean contains(String string) {
		Set<String> set = strings.get(string.length());
		if(set == null) {
			return false;
		} else {
			return set.contains(string);
		}
	}
	
	public String getStatistic() {
		List<Integer> sortedLength = new ArrayList<Integer>();
		sortedLength.addAll(strings.keySet());
		Collections.sort(sortedLength);
		int max = 0;
		int min = Integer.MAX_VALUE;
		double median = 0;
		int sum = 0;
		int stringNum = 0;
		for(int i=0;i<sortedLength.size();++i) {
			int length = sortedLength.get(i);
			max = length > max ? length : max;
			min = length < min ? length : min;
			sum += length * strings.get(length).size();
			stringNum += strings.get(length).size();
			if(median == 0) {
				if(size % 2 == 0) {
					if(stringNum == size / 2) {
						median = (double)(sortedLength.get(i) + sortedLength.get(i + 1)) / 2;
					} else if(stringNum > size / 2) {
						median = (double)sortedLength.get(i);
					}
				} else {
					if(stringNum > size / 2) {
						median = (double)sortedLength.get(i);
					}
				}
			}
		}
		return ("SIZE: " + size + "\n" 
				+ "MAX: " + max + "\n" 
				+ "MIN: " + min + "\n" 
				+ "AVERAGE: " + (double)(sum / size) + "\n" 
				+ "MEDIAN: " + median + "\n");
	}
	
	public int getLongestLength() {
		int longest = 0;
		for(int length : strings.keySet()) {
			longest = length > longest ? length : longest;
		}
		return longest;
	}
	
	public Set<String> getStrings(int length) {
		return strings.get(length);
	}
	
	public String showAll() {
		StringBuilder re = new StringBuilder();
		re.append(id + ":\n");
		for(Set<String> set : strings.values()) {
			for(String s : set) {
				re.append(s + "\n");
			}
		}
		return re.toString();
	}
	
	public String getId() {
		return id;
	}
	
	public int getSize() {
		return size;
	}

	public Map<Integer, Set<String>> getStrings() {
		return strings;
	}
	
}
