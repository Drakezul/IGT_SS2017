package Util;

public class StringUtil {

	/**
	 * Calculates the matching between two strings
	 */
	public static double calculateStringmatching(String s1, String s2) {
		//we don't want the matching degree, not the difference
		int maxLength = Math.max(s1.length(), s2.length());
		return (maxLength - computeLevenshteinDistance(s1, s2)) / (double) maxLength;
	}

	/**
	 * Example implementation of the Levenshtein Edit Distance See
	 * https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
	 */
	public static int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {
		int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

		for (int i = 0; i <= lhs.length(); i++)
			distance[i][0] = i;
		for (int j = 1; j <= rhs.length(); j++)
			distance[0][j] = j;

		for (int i = 1; i <= lhs.length(); i++)
			for (int j = 1; j <= rhs.length(); j++)
				distance[i][j] = minimum(distance[i - 1][j] + 1, distance[i][j - 1] + 1,
						distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));

		return distance[lhs.length()][rhs.length()];
	}

	private static int minimum(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}

}
