package no.eivind.autocomplete;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;

public class MapTerminalCompletion implements TerminalCompletion {

	private TreeMap<String, CommandEntry> hayStack = new TreeMap<>();
	private final Comparator<CommandEntry> comparator;
	private int historySize;
	private CommandEntryLastUsedComparator lastUsedComparator = new CommandEntryLastUsedComparator();

	public MapTerminalCompletion() {
		this(SortBy.USAGE);
	}

	public MapTerminalCompletion(SortBy sorting) {
		this(sorting, 100);
	}

	public MapTerminalCompletion(int historySize) {
		this(SortBy.USAGE, historySize);
	}

	public MapTerminalCompletion(SortBy sorting, int historySize) {
		switch (sorting) {
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

		this.historySize = historySize;
	}

	@Override
	public synchronized void addToHistory(String command) {
		if (hayStack.containsKey(command)) {
			hayStack.get(command).used();
		} else {
			this.hayStack.put(command, new CommandEntry(command));
		}
		if (this.hayStack.size() > this.historySize) {
			removeLeastReasentlyUsedCommand();
		}

	}

	@Override
	public synchronized ArrayList<String> find(String needle) {

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

	private synchronized void removeLeastReasentlyUsedCommand() {
		Ordering<String> ordering = Ordering.from(lastUsedComparator).onResultOf(Functions.forMap(this.hayStack));
		ImmutableSortedMap<String, CommandEntry> orderedHay = ImmutableSortedMap.copyOf(this.hayStack, ordering);
		String oldestKey = orderedHay.keySet().last();

		hayStack.remove(oldestKey);
	}
}
