
package acme.components;

import java.util.Arrays;
import java.util.List;

public class SpamDetector {

	private List<String>	spamTerms;
	private double			spamThreshold;


	public SpamDetector() {
		this.spamTerms = List.of("sex", "viagra", "cialis", "one million", "you’ve won", "nigeria");
		this.spamThreshold = 0.1;
	}

	public SpamDetector(final String spamTerms, final double spamThreshold) {

		String[] spamArray = spamTerms.split(",\\s*");
		List<String> spamTermsList = Arrays.asList(spamArray);
		this.spamTerms = spamTermsList;
		this.spamThreshold = spamThreshold;
	}

	public boolean isSpam(String text) {
		text = text.trim().toLowerCase().replaceAll("\\s+", " ") + " ";
		String[] words = text.split("\\s+");
		int totalWords = words.length;
		int spamCount = 0;

		for (String term : this.spamTerms) {
			term = term + " ";
			if (text.contains(term.toLowerCase())) {
				int termCount = text.split(term.toLowerCase(), -1).length - 1;
				spamCount += termCount;
			}
		}
		double spamPercentage = (double) spamCount / totalWords;
		return spamPercentage > this.spamThreshold;
	}

}
