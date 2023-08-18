package tool;

import java.util.Comparator;

import bean.EntryExitInfo;

public class SortEnEx implements Comparator<EntryExitInfo> {

	@Override
	public int compare(EntryExitInfo en1, EntryExitInfo en2) {
		return (en1.getCount() < en2.getCount() ? -1 : 1);
	}
}
