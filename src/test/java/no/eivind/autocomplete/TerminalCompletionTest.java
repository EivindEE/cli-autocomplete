package no.eivind.autocomplete;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

	@Test
	public void findPartialMatches() {
		String command = "command";
		completion.addToHistory(command);
		String partialyMatchingCommand = command.substring(command.length() / 2);
		assertTrue(completion.find(partialyMatchingCommand).contains(command));
	}

	@Test
	public void findMultipelMatches() throws InterruptedException {
		String partialMatch = "cd ";
		String command1 = partialMatch + "/etc/";
		String command2 = partialMatch + "~";
		String command3 = partialMatch + "~/development";

		String[] commands = { command1, command2, command3 };
		for (String command : commands) {
			completion.addToHistory(command);
			Thread.sleep(0,1);
		}

		assertEquals(commands.length, completion.find(partialMatch).size());
	}

	@Test
	public void findMultipelMatchesIgnoresNonMatching() throws InterruptedException {
		String partialMatch = "cd ";
		String command1 = partialMatch + "/etc/";
		String command2 = partialMatch + "~";
		String command3 = partialMatch + "~/development";

		String[] commands = { command1, command2, command3 };
		for (String command : commands) {
			completion.addToHistory(command);
			Thread.sleep(0,1);
		}

		// Add a non matching command
		completion.addToHistory(partialMatch.substring(1));

		assertEquals(commands.length, completion.find(partialMatch).size());
	}

	@Test
	public void findMultipelMatchesSortedByUsage() {

		String partialMatch = "cd ";
		String command1 = partialMatch + "/etc/";
		String command2 = partialMatch + "~";
		String command3 = partialMatch + "~/development";

		String[] commands = { command1, command2, command3 };
		for (int i = 0; i < commands.length; i++) {
			for (int j = 0; j <= i; j++) {
				completion.addToHistory(commands[i]);
			}
		}

		assertEquals(commands[commands.length -1], completion.find(partialMatch).get(0));
	}

	@Test
	public void findMultipelMatchesSortedByLastUsed() throws InterruptedException {
		this.completion = new MapTerminalCompletion(SortBy.LAST_USED);
		String partialMatch = "cd ";
		String command1 = partialMatch + "/etc/";
		String command2 = partialMatch + "~";


		completion.addToHistory(command2);
		completion.addToHistory(command2);
		Thread.sleep(0,1);
		String expectedCommand = command1;
		completion.addToHistory(expectedCommand);

		assertEquals(expectedCommand, completion.find(partialMatch).get(0));
	}
}
