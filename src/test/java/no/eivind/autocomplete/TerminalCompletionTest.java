package no.eivind.autocomplete;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TerminalCompletionTest {
	private TerminalCompletion completion;
	@Before
	public void setUp() throws Exception {
		this.completion = new MapTerminalCompletion();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void findExactMatches() {
		String command = "command";
		completion.addToHistory(command);
		assertTrue(completion.find(command).contains(command));
	}
	
	@Test
	public void ignoresNonMatchingCommands() {
		String command = "command";
		completion.addToHistory(command);
		String nonMatchingCommand = command + " not command";
		assertFalse(completion.find(nonMatchingCommand).contains(command));
	}

}
