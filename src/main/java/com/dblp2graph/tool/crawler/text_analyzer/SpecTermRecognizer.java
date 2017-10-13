package com.dblp2graph.tool.crawler.text_analyzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpecTermRecognizer {

	private static final String currentUserDirPath = System.getProperty("user.dir");
	private static final boolean DISPLAY_LOG = false;
	private List<String> dicData = new ArrayList<>();

	public SpecTermRecognizer() {

		dicData.addAll(new DictionaryInitializer(currentUserDirPath + "/nlp/dictionary/computing-dic_en.txt")
				.getData());

		/*dicData.addAll(new DictionaryInitializer(currentUserDirPath + "/data/corpus/dictionary/computing-terms_en.txt")
				.getData());*/
		
		dicData.addAll(new DictionaryInitializer(currentUserDirPath + "/nlp/dictionary/artificial_intelligence.txt")
				.getData());
		
		dicData.addAll(new DictionaryInitializer(currentUserDirPath + "/nlp/dictionary/data_mining.txt")
				.getData());

		// remove duplications
		Set<String> hs = new HashSet<>();
		hs.addAll(dicData);
		dicData.clear();
		dicData.addAll(hs);

		System.out.println("**Info: loading dictionary data: " + this.dicData.size() + " (records)");
		System.out.println("**Info: longest term's length: " + this.getLongestTermLength() + " (words)");
	}

	public String execute(String input) {
		if (dicData.size() > 0) {
			int totalSpecTermDetected = 0;
			int maxTermLength = this.getLongestTermLength();
			StringBuilder sb = new StringBuilder();
			String[] segments = input.split(" ");

			int current_pos = 0;
			while (current_pos < segments.length) {
				String combinedTerm = segments[current_pos];

				List<String> termStack = new ArrayList<>();
				termStack.add(combinedTerm);
				for (int j = 1; j <= maxTermLength; j++) {
					if ((current_pos + j) < segments.length) {
						combinedTerm = combinedTerm + "_" + segments[current_pos + j];

						if (this.dicData.contains(combinedTerm.toLowerCase())) {
							if (DISPLAY_LOG) {
								System.out.println("**INFO: detecting -> [" + combinedTerm + "]");
							}
							// current_pos = current_pos+j+1;
							termStack.add(combinedTerm);
						} else {
							continue;
						}
					} else {
						break;
					}

				}
				if (termStack.size() > 1) {
					sb.append(termStack.get(termStack.size() - 1)).append(" ");
					totalSpecTermDetected++;
					current_pos = current_pos + termStack.size();
				} else {
					sb.append(segments[current_pos]).append(" ");
					current_pos++;
				}

			}
			// System.out.println("**INFO: total detected spec-term -> [" +
			// totalSpecTermDetected + "]");
			return sb.toString();
		}
		return input;
	}

	private int getLongestTermLength() {
		int maxLength = 0;
		for (String dicTerm : dicData) {
			if (dicTerm.split("_").length > maxLength) {
				maxLength = dicTerm.split("_").length;
			}
		}
		return maxLength;
	}
}
