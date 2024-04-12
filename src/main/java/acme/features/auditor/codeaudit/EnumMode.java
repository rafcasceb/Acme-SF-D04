
package acme.features.auditor.codeaudit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import acme.entities.audits.Mark;

public class EnumMode {

	public static String mode(final Collection<Mark> marks) {
		if (marks == null || marks.isEmpty())
			return null;

		Map<Mark, Integer> countMap = new HashMap<>();

		for (Mark mark : marks)
			countMap.put(mark, countMap.getOrDefault(mark, 0) + 1);

		Mark modeMark = null;
		int maxCount = 0;
		for (Map.Entry<Mark, Integer> entry : countMap.entrySet())
			if (entry.getValue() > maxCount || entry.getValue() == maxCount && entry.getKey().compareTo(modeMark) < 0) {
				modeMark = entry.getKey();
				maxCount = entry.getValue();
			}

		return modeMark.toString();

	}

}
