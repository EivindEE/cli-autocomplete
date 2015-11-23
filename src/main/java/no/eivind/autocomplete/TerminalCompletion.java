package no.eivind.autocomplete;

import java.util.List;
/**
 * A TerminalCompletion object stores commands, and allows finding matches and partial matches 
 * @author Eivind Elseth
 *
 */
public interface TerminalCompletion {
	
	public void addToHistory(String command);
	
	public List<String> find(String needle);
}
