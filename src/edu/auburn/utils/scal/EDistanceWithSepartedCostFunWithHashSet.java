package edu.auburn.utils.scal;

import java.util.Arrays;
import java.util.List;

public class EDistanceWithSepartedCostFunWithHashSet {

	private PhoneticSets phonetic = new PhoneticSets();

	private double[][] T;

	private double maxLen = 0;

	//
	// public double recEditDistance(char[] str1, char str2[], int len1, int
	// len2) {
	// if (len1 == str1.length) {
	// return str2.length - len2;
	// }
	// if (len2 == str2.length) {
	// return str1.length - len1;
	// }
	// return min(recEditDistance(str1, str2, len1 + 1, len2 + 1) + str1[len1]
	// == str2[len2] ? 0 : 1,
	// recEditDistance(str1, str2, len1, len2 + 1) + 1, recEditDistance(str1,
	// str2, len1 + 1, len2) + 1);
	// }

	public double getMaxLen() {
		return maxLen;
	}

	public void setMaxLen(double maxLen) {
		this.maxLen = maxLen;
	}

	/**
	 * Uses bottom up DP to find the edit distance
	 */
	public double dynamicEditDistance(List<String> strs1, List<String> strs2) {
		// char[] str1 = string1.toCharArray();
		// char[] str2 = string2.toCharArray();
		double temp[][] = new double[strs1.size() + 1][strs2.size() + 1];
		temp[0][0] = 0.0;
		for (int i = 1; i <= strs2.size(); i++) {

			temp[0][i] = cost("", strs2.get(i - 1)) + temp[0][i - 1];

		}
		for (int i = 1; i <= strs1.size(); i++) {

			temp[i][0] = cost(strs1.get(i - 1), "") + temp[i - 1][0];

		}

		// if (strs2.size() == 0 || strs1.size() == 0) return
		// temp[strs1.size()][strs2.size()];

		maxLen = Math.max(temp[strs1.size()][0], temp[0][strs2.size()]);

		for (int i = 1; i <= strs1.size(); i++) {
			for (int j = 1; j <= strs2.size(); j++) {
				String professorStr = strs1.get(i - 1);
				String studentStr = strs2.get(j - 1);

				temp[i][j] = min(temp[i - 1][j - 1] + cost(professorStr, studentStr),
						temp[i - 1][j] + cost(professorStr, ""), temp[i][j - 1] + cost("", studentStr));
				// System.out.print(temp[i][j] + " ");
			}
		}
		// System.out.println();

		T = temp;

		return temp[strs1.size()][strs2.size()];
	}

	private double cost(String professorStr, String studentStr) {
		
		if (professorStr == null || professorStr == "") {
			if (phonetic.getConsonantSet().contains(studentStr) || phonetic.getVowelsSet().contains(studentStr)) {
				return 1.0;
			} else {
				return 1.5;
			}
		}

		if (studentStr == null || studentStr == "") {
			if (phonetic.getConsonantSet().contains(professorStr) || phonetic.getVowelsSet().contains(professorStr)) {
				return 1.0;
			} else {
				return 1.5;
			}
		}
		
		if (professorStr.equals("dʒ") && studentStr.equals("d1523")) { // add more hand pick cases here if we have more 
			return 1.0;
		}
		

		if (professorStr.equals(studentStr)) {
			return 0.0;
		}

		if ((phonetic.getConsonantSet().contains(professorStr) && phonetic.getConsonantSet().contains(studentStr))
				|| (phonetic.getVowelsSet().contains(professorStr) && phonetic.getVowelsSet().contains(studentStr))) {
			return 1.0;
		}

		if ((phonetic.getConsonantSet().contains(professorStr) && phonetic.getVowelsSet().contains(studentStr))
				|| (phonetic.getVowelsSet().contains(professorStr)
						&& phonetic.getConsonantSet().contains(studentStr))) {
			return Double.MAX_VALUE;
		}

		if (professorStr.length() == 5 && studentStr.length() == 5) {
			if ((phonetic.getConsonantSet().contains(professorStr) && phonetic.getConsonantSet().contains(studentStr))
					|| (phonetic.getVowelsSet().contains(professorStr)
							&& phonetic.getVowelsSet().contains(studentStr))) {
				return 1.0;
			} else {
				return 1.5;
			}
		}

		if ((phonetic.getDicriticsSet1().contains(String.valueOf(professorStr.charAt(0)))
				&& String.valueOf(professorStr.substring(1)).equals(studentStr))
				|| (phonetic.getDicriticsSet1().contains(String.valueOf(studentStr.charAt(0))))
						&& studentStr.substring(1).equals(professorStr)) {
			return 0.5;
//				System.out.println("pppp");
		}

		if (phonetic.getDicriticsSet2().contains(String.valueOf(professorStr.charAt(professorStr.length() - 1)))
				&& professorStr.substring(0, professorStr.length() - 1).equals(studentStr)) {
			return 0.5;
//				System.out.println("pppp");
		}

		if (professorStr.length() > 1 || studentStr.length() > 1) {
			return 1.5;
		}

		return 1.0;

	}

	/**
	 * Prints the actual edits which needs to be done.
	 */
	public String printActualEdits(List<String> strs1, List<String> strs2) {
		// char[] str1 = string1.toCharArray();
		// char[] str2 = string2.toCharArray();

		int i = T.length - 1;
		int j = T[0].length - 1;
		

		StringBuilder result = new StringBuilder();
		while (true) {
			// System.out.println(str1[i - 1 ] + " " + str2[j - 1]);
			if (i == 0 && j == 0) {
				break;
			}
			if (i == 0) {
				// result.append("d");
				if (phonetic.getConsonantSet().contains(strs2.get(j - 1)))
					result.append("%");

				else
					result.append("i");
				j--;
				continue;

			}

			if (j == 0) {
				result.append("d");
				i--;
				continue;
			}
			
			
			String professorStr = strs1.get(i - 1);
			String studentStr = strs2.get(j - 1);
			
			if (professorStr.equals(studentStr)) {

				if (phonetic.getConsonantSet().contains(professorStr)) { // &&

					result.append("|");

				} else {

					result.append("*");
				}

				i = i - 1;
				j = j - 1;
			} else if (T[i][j] == T[i - 1][j - 1] + 1) {


				result.append("s");
				i = i - 1;
				j = j - 1;
			} else if (T[i][j] == T[i - 1][j] + 1) {
				
				result.append("d");
				i = i - 1;
			} else if (T[i][j] == T[i][j - 1] + 1) {
				
				if (phonetic.getConsonantSet().contains(studentStr))
					result.append("%");

				else
					result.append("i");
				j = j - 1;

			} else if (T[i][j] == T[i][j - 1] + 0.5) {

				result.append("!");
				j = j - 1;
			} else if (T[i][j] == T[i - 1][j - 1] + 0.5) {
				result.append("!");
				i = i - 1;
				j = j - 1;

			} else {
				// throw new IllegalArgumentException("Some wrong with given
				// data");

				result.append("?");
				i = i - 1;
				j = j - 1;

			}
		}
		return result.reverse().toString();
	}

	private double min(double a, double b, double c) {
		double l = Math.min(a, b);
		return Math.min(l, c);
	}

	// public static void main(String args[]) {
	// String str1 = "kitten";
	// String str2 = "sittig";
	// String str3 = "\u0276\u0276\u0234\u02A7";
	// String str4 = "\u0276\u0276\u0288\u0153";
	// System.out.println(str3.length());
	// System.out.println(str1);
	// System.out.println(str2);
	// System.out.println(str3);
	// EDistanceWithOutputWithWeight editDistance = new
	// EDistanceWithOutputWithWeight();
	//
	// //double result = editDistance.dynamicEditDistance(str3, str4);
	// //String symbol = editDistance.printActualEdits(str3, str4);
	// //System.out.print(result);
	// System.out.println();
	// //System.out.println(symbol);
	// }

}
