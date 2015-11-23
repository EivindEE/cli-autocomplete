package no.eivind.autocomplete;

import java.util.Date;

public class CommandEntry {

	private String command;
	private Date lastUsed;
	private int usedCount;

	public CommandEntry(String command) {
		this.command = command;
		this.lastUsed = new Date();
		this.usedCount = 1;
	}

	public int getUsedCount() {
		return this.usedCount;
	}

	public String getCommand() {
		return command;
	}

	public Date getLastUsed() {
		return this.lastUsed;
	}

	public void used() {
		this.usedCount += 1;
		this.lastUsed = new Date();
	}

}
