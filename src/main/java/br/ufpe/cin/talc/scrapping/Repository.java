package br.ufpe.cin.talc.scrapping;

public class Repository {
	
	private String title;
	
	private String description;
	
	private String url;
	
	private int printingOccurrences;
	
	public Repository() {

	}
	
	public Repository(String title, String description, String url) {
		this.title = title;
		this.description = description;
		this.url = url;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPrintingOccurrences() {
		return printingOccurrences;
	}
	
	public void setPrintingOccurrences(int printingOccurrences) {
		this.printingOccurrences = printingOccurrences;
	}
}
