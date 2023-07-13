package tool;

import java.util.Comparator;

import bean.EntryExitInfo;

public class SortEnExDate implements Comparator<EntryExitInfo>{
	@Override
	public int compare(EntryExitInfo en1, EntryExitInfo en2) {
		int result = (en1.getEnExDate().compareTo(en2.getEnExDate()));
		return result;
	}
}
