package no.eivind.autocomplete;

import java.util.Comparator;

public class CommandEntryUsageComparator implements Comparator<CommandEntry> {
	@Override
	public int compare(CommandEntry o1, CommandEntry o2) {
		int comparison = o2.getUsedCount() - o1.getUsedCount();
		if (comparison == 0) {
			return o2.getLastUsed().compareTo(o1.getLastUsed());
		}
		return comparison;
	};
}
