package no.eivind.autocomplete;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapTerminalCompletion implements TerminalCompletion {

	private HashMap<String,Integer> hayStack = new HashMap<>();
	private Comparator comparator = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return hayStack.get(o1).compareTo(hayStack.get(o2));
		};
	}; 

	@Override
	public void addToHistory(String command) {
		Integer timesUsed;
		if (hayStack.containsKey(command)) {
			timesUsed = hayStack.get(command) + 1;
		} else {
			timesUsed = 1;
		}
		this.hayStack.put(command, timesUsed);

	}

	@Override
	public ArrayList<String> find(String needle) {
		ArrayList<String> needles = new ArrayList<>();
		TreeMap<String, Integer> sortedHay = new TreeMap<>();
		for (String straw : this.hayStack.keySet()) {
			if (straw.contains(needle)) {
				needles.add(straw);
			}
		}
		return needles;
	}

}
