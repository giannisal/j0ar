package joarLib;

public class Siteword {

	private String site;
	private String word;
	private int frequency;

	public Siteword(String site, String word, int frequency) {
		this.site = site;
		this.word = word;
		this.frequency = frequency;
	}

	public Siteword(String site) {
			this.site = site;

	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
}
