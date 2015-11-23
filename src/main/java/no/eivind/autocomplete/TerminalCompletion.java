package no.eivind.autocomplete;

import java.util.List;

/**
 * A TerminalCompletion object stores commands, and allows finding matches and
 * partial matches.
 * 
 * @author Eivind Elseth
 *
 */
public interface TerminalCompletion {
	/**
	 * Adds the given command to history
	 * 
	 * @param command
	 */
	public void addToHistory(String command);

	/**
	 * Returns a list containing the commands that the parameter is a substring
	 * of
	 * 
	 * @param needle
	 * @return a list of matching commands
	 */
	public List<String> find(String needle);
}
