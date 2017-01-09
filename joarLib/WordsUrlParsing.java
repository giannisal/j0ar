package joarLib;

import java.util.*;
import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Η κλάση WordsUrlParsing μέσω τεσσάρων μεθόδων διατρέχει το HTML αρχείο κάθε
 * URL που είναι αποθηκευμένο στην βάση. Από το HTML αρχείο αφαιρούνται όλα τα
 * tags, τα σημεία στίξης, τα σύμβολα καθώς και οι αριθμοί. Στη συνέχεια οι
 * λέξεις αποθηκεύονται μία μία σε ένα πίνακα String. Οι λέξεις που δεν
 * χρειάζεται να αποθηκευτούν στη βάση (stop words και μονά γράμματα)
 * απορρίπτονται, ενώ οι υπόλοιπες αποθηκεύονται στη βάση.
 *
 */

public class WordsUrlParsing {

	private static final String[] stopWords = { "about", "above", "above", "across", "after", "afterwards", "again",
			"against", "all", "almost", "alone", "along", "already", "also", "although", "always", "am", "among",
			"amongst", "amoungst", "amount", "an", "and", "another", "any", "anyhow", "anyone", "anything", "anyway",
			"anywhere", "are", "around", "as", "at", "back", "be", "became", "because", "become", "becomes", "becoming",
			"been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond",
			"bill", "both", "bottom", "but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt",
			"cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either",
			"eleven", "else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything",
			"everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former",
			"formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has",
			"hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers",
			"herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed",
			"interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less",
			"ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most",
			"mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless",
			"next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off",
			"often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours",
			"ourselves", "out", "over", "own", "part", "per", "perhaps", "please", "put", "rather", "re", "same", "see",
			"seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since",
			"sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes",
			"somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves",
			"then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they",
			"thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus",
			"to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until",
			"up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence",
			"whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether",
			"which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within",
			"without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the" };

	/**
	 * Η μέθοδος main αρχικά συνδέεται με τη βάση όπου είναι αποθηκευμένα τα URL
	 * που έχει βρει ο Crawler. Για κάθε URL αποθηκεύει στη βάση τον τίτλο, την
	 * περιγραφή, τις λέξεις κλειδιά και τις λέξεις που περιέχονται ως μορφή
	 * κειμένου.
	 * 
	 */

	public static void main(String args[]) throws Exception {
		try {
			Parsing_DB database = new Parsing_DB();
			database.open();
			List<Sites> sites = database.returnAllSites();
			String title, description;

			for (Sites site : sites) {
				String url = site.getSite();
				URL inputURL = new URL(url);
				BufferedReader in = new BufferedReader(new InputStreamReader(inputURL.openStream(), "UTF8"));

				String input, texturl = "";
				while ((input = in.readLine()) != null)
					texturl += input.toLowerCase();
				in.close();

				title = returnTitle(texturl);
				System.out.println(title);
				description = returnDescription(texturl);
				System.out.println(description);
				database.updateSite(url, title, description);

				String[] keywords = returnKeywords(texturl);
				if (keywords == null) {
					System.out.println("No keywords found.");
				} else {
					for (String word : keywords) {
						System.out.println(word);
					}
				}

				WordsUrlParsing w = new WordsUrlParsing();
				texturl = w.removeTags(texturl);

				String[] a;
				a = texturl.split("\\s+");

				rejectStopwords(a);

				for (String word : a) {
					if (!word.equals("")) {
						System.out.println(word);

						if (Boolean.FALSE.equals(database.validWord(word))) {
							database.importWord(word, 1);
							database.importSiteword(url, word, 1);
						} else if (Boolean.FALSE.equals(database.validSiteword(url, word))) {
							database.updateWord(word);
							database.importSiteword(url, word, 1);
						} else {
							database.updateWord(word);
							database.updateSiteword(url, word);
						}
					}
				}
				System.out.printf("%d\n", a.length);
			}
			database.close();
		} catch (Exception e1) {
			System.out.println(e1);
		}
	}

	/**
	 * Η μέθοδος removeTags αφαιρεί από το κώδικα HTML που παίρνει ως όρισμα
	 * κατά σειρά: τα σημεία "<" και ">" τα σχόλια HTML της μορφής <!-- --> τα
	 * tags που περιέχουν script, style και iframe καθώς και το κείμενο ανάμεσά
	 * τους όλα τα υπόλοιπα tags κρατώντας μόνο το κείμενο ενδιάμεσα στο αρχικό
	 * και τελικό tag τα σημεία στίξης και σύμβολα τους αριθμούς.
	 * 
	 * @param texturl
	 *            κώδικας HTML αποθηκευμένος σε μεταβλητή τύπου String
	 * @return τροποποιημένος κώδικας HTML
	 */

	public String removeTags(String texturl) {

		texturl = texturl.replaceAll("\"<\"", "");
		texturl = texturl.replaceAll("\">\"", "");
		texturl = texturl.replaceAll("<!--.+?-->", " ");
		texturl = texturl.replaceAll("<script.+?</script>", " ");
		texturl = texturl.replaceAll("<style.+?/style>", " ");
		texturl = texturl.replaceAll("<iframe.+?/iframe>", " ");
		texturl = texturl.replaceAll("<[^><]*>", " ");
		texturl = modifyString(texturl);
		texturl = texturl.replaceAll("[^a-zA-Z^\\p{InGreek}]", " ");
		// texturl = texturl.replaceAll("[`~!@#$%^&*()_+\\;\',./{}|:\"<>?]", "
		// ");
		// texturl = texturl.replaceAll("[0-9]", " ");
		// texturl = texturl.replaceAll("\\W"," ");

		return texturl;
	}

	/**
	 * Η μέθοδος rejectStopwords διατρέχει τον πίνακα String που δέχεται ως
	 * όρισμα και αντικαθιστά τις stop words και τα μονά γράμματα με κενό.
	 * 
	 * @param a
	 *            πίνακας String που περιέχει μία λέξη σε κάθε θέση
	 */

	public static void rejectStopwords(String[] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < stopWords.length; j++) {
				if (a[i].equals(stopWords[j])) {
					a[i] = "";
					break;
				}
			}
			if (a[i].length() == 1) {
				a[i] = "";
			}
		}
	}

	/**
	 * Η μέθοδος modifyString αφαιρεί τις HTML entities τύπου &entity; από το
	 * κώδικα HTML που δέχεται ως όρισμα.
	 * 
	 * @param htmlCode
	 *            κώδικας HTML
	 * @return τροποποιημένος κώδικας HTML
	 */

	public static String modifyString(String htmlCode) {
		String strToConvert = "";
		boolean status = false;

		for (char ch : htmlCode.toCharArray()) {
			if (ch == '&' || status == true) {
				strToConvert += ch;
				status = true;
				if (ch == ';') {
					htmlCode = htmlCode.replace(strToConvert, " ");
					strToConvert = "";
					status = false;
				}
			}
		}
		return htmlCode;
	}

	/**
	 * Η μέθοδος returnTitle δέχεται τον κώδικα HTML ως όρισμα και επιστρέφει
	 * τον τίτλο που βρίσκεται ανάμεσα στα tag <title> και </title>.
	 * 
	 * @param url:
	 *            κώδικας HTML
	 * @return title: τίτλος ιστοσελίδας
	 */

	public static String returnTitle(String url) {
		String title;
		title = url.substring(url.indexOf("<title>") + 7, url.indexOf("</title>"));
		title = title.replaceAll("[^a-zA-Z~!@#$%^&*()_+;,.{}|:<>?^\\p{InGreek}]", " ");

		return title;
	}

	/**
	 * Η μέθοδος returnDescription δέχεται τον κώδικα HTML ως όρισμα
	 * και επιστρέφει την περιγραφή της ιστοσελίδας που βρίσκεται 
	 * ως τμήμα των tag <meta>
	 * 
	 * @param url: κώδικας HTML
	 * @return x: περιγραφή ιστοσελίδας
	 */
	
	public static String returnDescription(String url) {
		String patternString = "<meta\\s*[^>]+\\s*+[^>]\\s*>";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(url);

		while (matcher.find()) {
			String x = matcher.group();

			if (x.contains("name=\"description\"")) {
				x = x.replaceAll("name=\"description\"", "");
				if(x.contains("content=")){
					x = x.substring(x.indexOf("content=") + 9, x.indexOf(">"));
					x = x.replaceAll("[^a-zA-Z~!@#$%^&*()_+;,.{}|:<>?^\\p{InGreek}]", "");
					return x;
				} else {
					return " ";
				}
			}
		}
		return null;
	}

	/**
	 * Η μέθοδος returnKeywords δέχεται τον κώδικα HTML ως όρισμα και επιστρέφει
	 * τις λέξεις κλειδιά της ιστοσελίδας που βρίσκονται ως τμήμα των tag
	 * <meta>. Οι λέξεις κλειδιά αποθηκεύονται σε έναν πίνακα String, ο οποίος
	 * επιστρέφεται.
	 * 
	 * @param url:
	 *            κώδικας HTML
	 * @return keywords: πίνακας String με keywords
	 */

	public static String[] returnKeywords(String url) {
		String patternString = "<meta\\s*[^>]+\\s*+[^>]\\s*>";
		String[] keywords;

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(url);

		while (matcher.find()) {
			String x = matcher.group();

			if (x.contains("name=\"keywords\"")) {
				x = x.replaceAll("name=\"keywords\"", "");
				x = x.substring(x.indexOf("content=") + 9, x.indexOf(">"));

				x = x.replaceAll("[^a-zA-Z^\\p{InGreek}]", " ");

				keywords = x.split("\\s+");
				return keywords;
			}
		}
		return null;
	}
}