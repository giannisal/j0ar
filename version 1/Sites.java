package joarLib;

public class Sites {

  private int id_site;
	private String site;

	public Sites(int id_site, String site ) {
		this.site = site;
    this.id_site = id_site;
	}

  public Sites(String site ) {
    this.site = site;
  }

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public int getIdSite() {
		return id_site;
	}

	public void setIdSite(int id_site) {
		this.id_site = id_site;
	}
}
