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
	public List<String> find(String needle) {
		return this.hayStack;
	}

}
