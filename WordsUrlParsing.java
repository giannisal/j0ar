import java.util.*;
import java.net.*;
import java.io.*;
import java.util.regex.*;

public class TestHtml {

	private static final String[] stopWords= {"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the"};

	public static void main(String args[]) throws Exception {
    try {
			Joar_DB database = new Joar_DB();
			database.open();
			List<Sites> sites = database.returnAllSites();
			int pr;
			Siteword sitedata;

			for (Sites site: sites) {
				pr = 0;
				String url = site.getSite();
				URL inputURL = new URL(url);
				BufferedReader in = new BufferedReader(
					new InputStreamReader(inputURL.openStream(), "UTF8"));

				String input, texturl="";
				while ((input = in.readLine()) != null)
					 texturl += input.toLowerCase();
				in.close();

				texturl = texturl.replaceAll("\"<\"", "");
        texturl = texturl.replaceAll("\">\"", "");
				texturl = texturl.replaceAll("<!--.+?-->"," ");
				texturl = texturl.replaceAll("<script.+?</script>"," ");
				texturl = texturl.replaceAll("<style.+?/style>"," ");
				texturl = texturl.replaceAll("<iframe.+?/iframe>", " ");
				texturl = texturl.replaceAll("<[^><]*>"," ");
				texturl = texturl.replaceAll("[`~!@#$%^&*()_+\\;\',./{}|:\"<>?]", " ");
				texturl = texturl.replaceAll("&.+?;", " ");
				// texturl = modifyString(texturl);
				texturl = texturl.replaceAll("[0-9]"," ");
				texturl = texturl.replaceAll("\\W"," ");

				//System.out.println(texturl);
				//System.out.println();

				String[] a;
				a = texturl.split("\\s+");

				for (int i = 0; i < a.length; i++) {
					for(int j = 0; j < stopWords.length; j++) {
						if (a[i].equals(stopWords[j])) {
							a[i] = "";
							break;
						}
					}
					if (a[i].length()==1) {
						a[i] = "";
					}
				}

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
				System.out.printf("%d/n", a.length);
			}
			database.close();
		} catch (Exception e1) {
			System.out.println(e1);
		}
	}

	public static String modifyString(String htmlCode) {
		String strToConvert = "";
		boolean status = false;

		for (char ch: htmlCode.toCharArray()) {
			if (ch == '&' || status == true) {
				strToConvert += ch;
				status = true;
				if (ch == ';'){
					htmlCode = htmlCode.replace(strToConvert," ");
					strToConvert = "";
					status = false;
				}
			}
		}
		return htmlCode;
	}
}
