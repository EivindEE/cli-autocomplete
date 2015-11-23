package no.eivind.autocomplete;

import java.util.Comparator;

public class CommandEntryLastUsedComparator implements Comparator<CommandEntry> {

	@Override
	public int compare(CommandEntry o1, CommandEntry o2) {
		int comparison = o2.getLastUsed().compareTo(o1.getLastUsed());
		if (comparison == 0) {
			return o2.getCommand().compareTo(o1.getCommand());
		}
		return comparison;
	}

}
