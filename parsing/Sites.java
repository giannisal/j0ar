public class Sites {

  private int id_site;
	private String site;
  private String title;
  private String description;


	public Sites(String site, String title, String description) {
    this.id_site = id_site;
    this.title = title;
    this.description = description;
	}

  public Sites(String site) {
    this.site = site;
  }


	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

  public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

	public int getIdSite() {
		return id_site;
	}

	public void setIdSite(int id_site) {
		this.id_site = id_site;
	}
}
