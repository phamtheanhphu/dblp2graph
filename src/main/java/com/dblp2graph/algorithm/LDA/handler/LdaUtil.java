/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2015/1/29 19:07</create-date>
 *
 * <copyright file="LdaUtil.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package com.dblp2graph.algorithm.LDA.handler;

import java.util.*;

import com.dblp2graph.adapter.text.TextFileAdapter;

/**
 * @author hankcs
 */
import static com.dblp2graph.algorithm.LDA.handler.LdaGibbsSampler.shadeDouble;

public class LdaUtil {

	private static TextFileAdapter textFileAdapter = new TextFileAdapter();

	/**
	 * To translate a LDA matrix to readable result
	 * 
	 * @param phi
	 *            the LDA model
	 * @param vocabulary
	 * @param limit
	 *            limit of max words in a topic
	 * @return a map array
	 */
	public static Map<String, Double>[] translate(double[][] phi, Vocabulary vocabulary, int limit) {
		limit = Math.min(limit, phi[0].length);
		Map<String, Double>[] result = new Map[phi.length];
		for (int k = 0; k < phi.length; k++) {
			Map<Double, String> rankMap = new TreeMap<Double, String>(Collections.reverseOrder());
			for (int i = 0; i < phi[k].length; i++) {
				rankMap.put(phi[k][i], vocabulary.getWord(i));
			}
			Iterator<Map.Entry<Double, String>> iterator = rankMap.entrySet().iterator();
			result[k] = new LinkedHashMap<String, Double>();
			for (int i = 0; i < limit; ++i) {
				Map.Entry<Double, String> entry = iterator.next();
				result[k].put(entry.getValue(), entry.getKey());
			}
		}
		return result;
	}

	public static Map<String, Double> translate(double[] tp, double[][] phi, Vocabulary vocabulary, int limit) {
		Map<String, Double>[] topicMapArray = translate(phi, vocabulary, limit);
		double p = -1.0;
		int t = -1;
		for (int k = 0; k < tp.length; k++) {
			if (tp[k] > p) {
				p = tp[k];
				t = k;
			}
		}
		return topicMapArray[t];
	}

	/**
	 * To print the result in a well formatted form
	 * 
	 * @param result
	 */
	public static void explain(Map<String, Double>[] result) {
		int i = 0;
		for (Map<String, Double> topicMap : result) {
			System.out.printf("topic %d :\n", i++);
			explain(topicMap);
			System.out.println();
		}
	}

	public static void explain(Map<String, Double> topicMap) {
		for (Map.Entry<String, Double> entry : topicMap.entrySet()) {
			System.out.println(entry);
		}
	}

	public static void exportPhiToLogFile(Map<String, Double>[] topicMaps, String logFilePath) {

		textFileAdapter.writeAppendToFile("[Phi]", logFilePath);

		if (topicMaps != null) {

			List<String> words = new ArrayList<>();

			int i = 0;

			for (Map<String, Double> topicMap : topicMaps) {
				textFileAdapter.writeAppendToFile("t#" + i, logFilePath);
				// System.out.printf("topic %d :\n", i++);
				for (Map.Entry<String, Double> entry : topicMap.entrySet()) {
					textFileAdapter.writeAppendToFile(entry.toString(), logFilePath);
				}
				i++;

			}

		}
	}

	public static void dispTheta(double[][] theta) {

		System.out.println();
		System.out.println();
		System.out.println("Document--Topic Associations, Theta[d][k]");
		System.out.print("d\\k\t");
		for (int m = 0; m < theta[0].length; m++) {
			System.out.print("   " + m % 10 + "    ");
		}
		System.out.println();
		for (int m = 0; m < theta.length; m++) {
			System.out.print(m + "\t");
			for (int k = 0; k < theta[m].length; k++) {
				// System.out.print(theta[m][k] + " ");
				System.out.print(shadeDouble(theta[m][k], 1) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void dispThetaToLogFile(double[][] theta, String logFilePath) {

		System.out.println();
		System.out.println();

		System.out.println("\nDocument--Topic Associations, Theta[d][k]");
		textFileAdapter.writeAppendToFile("Document--Topic Associations, Theta[d][k]", logFilePath);

		String overallTopicLine = "d\\k\t";
		for (int m = 0; m < theta[0].length; m++) {
			overallTopicLine += "   " + m % 10 + "    ";
			System.out.print("   " + m % 10 + "    ");
		}

		textFileAdapter.writeAppendToFile(overallTopicLine, logFilePath);
		System.out.println();

		for (int m = 0; m < theta.length; m++) {

			System.out.print(m + "\t");

			String eachDocLine = m + "\t";

			for (int k = 0; k < theta[m].length; k++) {
				// System.out.print(theta[m][k] + " ");
				System.out.print(shadeDouble(theta[m][k], 1) + " ");
				eachDocLine += shadeDouble(theta[m][k], 1) + " ";
			}

			textFileAdapter.writeAppendToFile(eachDocLine, logFilePath);

			System.out.println();

		}

		System.out.println();
		// textFileAdapter.writeAppendToFile("\n", logFilePath);
	}

	public static void dispThetaInNum(double[][] theta) {

		System.out.println();
		System.out.println();
		System.out.println("Document--Topic Associations, Theta[d][k]");
		System.out.print("d\\k\t");

		for (int m = 0; m < theta[0].length; m++) {
			System.out.print("\t" + m % 10 + "\t            ");
		}

		System.out.println();
		for (int m = 0; m < theta.length; m++) {
			System.out.print(m + "\t");
			for (int k = 0; k < theta[m].length; k++) {
				System.out.print(theta[m][k] + "\t");
				// System.out.print(shadeDouble(theta[m][k], 1) + " ");
			}
			System.out.println();
		}
		System.out.println();

	}

	public static void dispThetaInNumToLogFile(double[][] theta, String logFilePath) {

		textFileAdapter.writeAppendToFile("[Theta]", logFilePath);

		for (int m = 0; m < theta.length; m++) {

			String eachDocumentFile = "d#" + m + "\t";

			for (int k = 0; k < theta[m].length; k++) {
				eachDocumentFile += theta[m][k] + "\t";
				// System.out.print(shadeDouble(theta[m][k], 1) + " ");
			}

			textFileAdapter.writeAppendToFile(eachDocumentFile, logFilePath);

		}

	}

}
