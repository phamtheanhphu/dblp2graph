package com.dblp2graph.tool.crawler.text_analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.dblp2graph.adapter.text.TextFileAdapter;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class WordTokenizer {

	// TODO Auto-generated method stub
	private static final String TEXT_PATTERN_REGEX = "[^A-Za-z0-9_ ]";

	private SpecTermRecognizer specTermRecognizer;
	private StopwordFilter stopwordFilter;
	private Properties props;
	private StanfordCoreNLP pipeline;

	private boolean toLowerCase;
	private boolean removeSpecialChar;
	private boolean removeStopword;
	private boolean wordStemming;
	private boolean specTermRegconize;

	private List<String> allowedPOSList;
	private int allowedWordMinLength;

	public WordTokenizer(boolean toLowerCase, boolean removeSpecialChar, boolean removeStopword, boolean wordStemming,
			boolean specTermRegconize, List<String> allowedPOSList, int allowedWordMinLength) {

		super();
		// TODO Auto-generated constructor stub
		this.stopwordFilter = new StopwordFilter();

		this.toLowerCase = toLowerCase;
		this.removeSpecialChar = removeSpecialChar;

		this.removeStopword = removeStopword;

		this.wordStemming = wordStemming;
		this.allowedPOSList = allowedPOSList;

		if (this.wordStemming || this.allowedPOSList != null) {
			this.props = new Properties();
			this.props.put("annotators", "tokenize, ssplit, pos, lemma");
			this.pipeline = new StanfordCoreNLP(props, false);
		}

		this.specTermRegconize = specTermRegconize;

		if (this.specTermRegconize) {
			this.specTermRecognizer = new SpecTermRecognizer();
		}

		this.allowedWordMinLength = allowedWordMinLength;

	}

	// proceedFile
	public List<String> proceedFile(String inputFile) {

		TextFileAdapter textFileAdapter = new TextFileAdapter();
		return this.proceedText(textFileAdapter.parseSingleFileToString(inputFile));

	}

	// proceedText
	public List<String> proceedText(String text) {

		if (this.specTermRegconize) {
			text = this.specTermRecognizer.execute(text);
		}

		List<String> analyzedTokens = new ArrayList<>();

		if (this.wordStemming || this.allowedPOSList != null) {

			Annotation document = this.pipeline.process(text);

			if (document != null) {

				for (CoreMap sentence : document.get(SentencesAnnotation.class)) {

					for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

						String returnToken = token.get(TextAnnotation.class);

						// stemming
						if (this.wordStemming) {
							returnToken = token.get(LemmaAnnotation.class);
						}

						// allowedWordMinLength
						if (returnToken.length() < this.allowedWordMinLength) {
							continue;
						}

						// removeSpecialChar
						if (this.removeSpecialChar) {
							returnToken = returnToken.replaceAll(TEXT_PATTERN_REGEX, "");
						}

						// toLowerCase
						if (this.toLowerCase) {
							returnToken = returnToken.toLowerCase();
						}

						// remove stopword
						if (this.removeStopword) {
							if (this.stopwordFilter.isStopword(returnToken)) {
								continue;
							}
						}

						// only allow some type of POS
						if (allowedPOSList != null) {
							String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
							if (!allowedPOSList.contains(pos)) {
								continue;
							}
						}

						analyzedTokens.add(returnToken);
						// out.println(returnToken);

					}

				}

			}

		} else {

			String[] tokens = text.split(" ");

			for (String token : tokens) {

				String returnToken = token.trim();


				// allowedWordMinLength
				if (returnToken.length() < this.allowedWordMinLength) {
					continue;
				}

				// removeSpecialChar
				if (this.removeSpecialChar) {
					returnToken = returnToken.replaceAll(TEXT_PATTERN_REGEX, "");
				}

				// toLowerCase
				if (this.toLowerCase) {
					returnToken = returnToken.toLowerCase();
				}

				// remove stopword
				if (this.removeStopword) {
					if (this.stopwordFilter.isStopword(returnToken)) {
						continue;
					}
				}

				analyzedTokens.add(returnToken);
				// out.println(returnToken);

			}

		}

		return analyzedTokens;

		// return null;
	}

}
