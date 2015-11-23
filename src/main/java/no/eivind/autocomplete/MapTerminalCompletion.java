package no.eivind.autocomplete;

import java.util.ArrayList;
import java.util.List;

public class MapTerminalCompletion implements TerminalCompletion {

	private ArrayList<String> hayStack = new ArrayList<>();

	@Override
	public void addToHistory(String command) {
		this.hayStack.add(command);

	}

	@Override
	public ArrayList<String> find(String needle) {
		ArrayList<String> needles = new ArrayList<>();
		for (String straw : this.hayStack) {
			if (straw.contains(needle)) {
				needles.add(straw);
			}
		}
		return needles;
	}

}
