package no.eivind.autocomplete;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandEntryTest {
	private static final String COMMAND = "command";
	private CommandEntry entry;

	@Before
	public void setUp() throws Exception {
		this.entry = new CommandEntry(COMMAND);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void initialStateTest() throws InterruptedException {
		String expectedName = COMMAND;
		assertEquals(expectedName, entry.getCommand());

		int expectedCallCount = 1;
		assertEquals(expectedCallCount, this.entry.getUsedCount());

		Date expectedBeforeCallDate = new Date();
		Thread.sleep(0, 1);
		this.entry = new CommandEntry(COMMAND);
		Thread.sleep(0, 1);
		Date expectedAfterCallDate = new Date();
		assertTrue(this.entry.getLastUsed().after(expectedBeforeCallDate));
		assertTrue(this.entry.getLastUsed().before(expectedAfterCallDate));
	}

	@Test
	public void usedTest() throws InterruptedException {
		Thread.sleep(0, 1);
		int currentUsed = this.entry.getUsedCount();
		Date currentDate = this.entry.getLastUsed();
		this.entry.used();
		assertTrue("Used count should have incremented", currentUsed < this.entry.getUsedCount());
		assertTrue("Should have had newer last used date", currentDate.before(this.entry.getLastUsed()));
	}

}
