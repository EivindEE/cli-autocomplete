package no.eivind.autocomplete;

import java.util.Comparator;

public class CommandEntryLastUsedComparator implements Comparator<CommandEntry> {

	@Override
	public int compare(CommandEntry o1, CommandEntry o2) {
		return o2.getLastUsed().compareTo(o1.getLastUsed());
	}

}
