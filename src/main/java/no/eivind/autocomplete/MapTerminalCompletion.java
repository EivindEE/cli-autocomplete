package no.eivind.autocomplete;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;

public class MapTerminalCompletion implements TerminalCompletion {

	private TreeMap<String,CommandEntry> hayStack = new TreeMap<>();
	private final Comparator<CommandEntry> comparator;

	public MapTerminalCompletion() {
		this(SortBy.USAGE);
	}

	public MapTerminalCompletion(SortBy lastUsed) {
		switch (lastUsed) {
		case USAGE:
			this.comparator = new CommandEntryUsageComparator();
			break;
		case LAST_USED:
			this.comparator = new CommandEntryLastUsedComparator();
			break;
		default:
			this.comparator = new CommandEntryUsageComparator();
			break;
		}
	}

	@Override
	public void addToHistory(String command) {
		if (hayStack.containsKey(command)) {
			hayStack.get(command).used();
		} else {
			this.hayStack.put(command, new CommandEntry(command));
		}

	}

	@Override
	public ArrayList<String> find(String needle) {

		Ordering<String> ordering = Ordering.from(comparator).onResultOf(Functions.forMap(this.hayStack));
		ImmutableSortedMap<String, CommandEntry> orderedHay = ImmutableSortedMap.copyOf(this.hayStack, ordering);

		ArrayList<String> needles = new ArrayList<>();
		for (String straw : orderedHay.keySet()) {
			if (straw.contains(needle)) {
				needles.add(straw);
			}
		}
		return needles;
	}

}
