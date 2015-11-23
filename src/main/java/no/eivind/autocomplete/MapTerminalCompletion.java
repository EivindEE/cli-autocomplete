package no.eivind.autocomplete;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;

/**
 * An implementation of {@link TerminalCompletion} using a Map as a backing data
 * structure. You can select the ordering of the results using the
 * {@link SortBy} enum.
 * 
 * @author eivindelseth
 *
 */
public class MapTerminalCompletion implements TerminalCompletion {

	private static final int DEFAULT_HISTORY_SIZE = 100;
	private static final SortBy DEFAULT_SORTING = SortBy.USAGE;
	private TreeMap<String, CommandEntry> hayStack = new TreeMap<>();
	private final Comparator<CommandEntry> comparator;
	private int historySize;
	private CommandEntryLastUsedComparator lastUsedComparator = new CommandEntryLastUsedComparator();

	/**
	 * Creates a MapTerminalCompletion object with the default sorting and size
	 */
	public MapTerminalCompletion() {
		this(DEFAULT_SORTING, DEFAULT_HISTORY_SIZE);
	}

	/**
	 * Creates a MapTerminalCompletion object with the given sorting and default
	 * size
	 * 
	 * @param sorting
	 */
	public MapTerminalCompletion(SortBy sorting) {
		this(sorting, DEFAULT_HISTORY_SIZE);
	}

	/**
	 * Creates a MapTerminalCompletion object with the default sorting and given
	 * size
	 * 
	 * @param historySize
	 */
	public MapTerminalCompletion(int historySize) {
		this(DEFAULT_SORTING, historySize);
	}

	/**
	 * Creates a MapTerminalCompletion object with the given sorting and size
	 * 
	 * @param sorting
	 * @param historySize
	 */
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
			removeLeastRecentlyUsedCommand();
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

	private synchronized void removeLeastRecentlyUsedCommand() {
		Ordering<String> ordering = Ordering.from(lastUsedComparator).onResultOf(Functions.forMap(this.hayStack));
		ImmutableSortedMap<String, CommandEntry> orderedHay = ImmutableSortedMap.copyOf(this.hayStack, ordering);
		String oldestKey = orderedHay.keySet().last();

		hayStack.remove(oldestKey);
	}
}
