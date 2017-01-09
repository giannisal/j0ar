package joarLib;

public class Sites {

  private int id_site;
	private String site,title,description;

	public Sites(int id_site, String site, String title, String description ) {
		this.site = site;
        this.id_site = id_site;
        this.title = title;
        this.description = description;
	}

  public Sites(String site , String title, String description) {
    this.site = site;
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

	public int getIdSite() {
		return id_site;
	}

	public void setIdSite(int id_site) {
		this.id_site = id_site;
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
}
