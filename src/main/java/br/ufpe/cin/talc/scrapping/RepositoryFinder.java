package br.ufpe.cin.talc.scrapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RepositoryFinder extends Scraper {

	private static final String BASE_URL = "https://github.com/topics/java";
	
	private List<Repository> foundRepositories;
	
	public RepositoryFinder() {
		this.foundRepositories = new ArrayList<>();
	}

	@Override
	public void scrap() {
		Document doc;
		Elements repositories = new Elements();
		
		String page;
		int pageIndex = 6;
		do {
			try {
				page = "?page="+pageIndex;
				
				doc = fetchPage(BASE_URL+page);

				repositories = doc.getElementsByTag("article");

				for (Element repository : repositories) {
					Elements h1Elements = repository.getElementsByTag("h1");
					
					for (Element h1Element : h1Elements) {
						Elements anchors = h1Element.getElementsByTag("a");
						
						for (Element anchor : anchors) {
							if (anchor.hasAttr("data-ga-click")) {
								if (anchor.getElementsByAttribute("data-ga-click").attr("data-ga-click").contains("go to repository,")) {
									String repoHref = anchor.attr("href");
									
									String repoName = repoHref.substring(repoHref.lastIndexOf('/'));
									
									this.addNewRepository(repoName, "", "https://github.com"+repoHref);
								}
							}
						}
					}
				}

				pageIndex++;
			} 
			catch (HttpStatusException e) {
				repositories = new Elements();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		} while (repositories.size() > 0);
	}
	
	private void addNewRepository(String title, String description, String url) {
		this.foundRepositories.add(new Repository(title, description, url));
	}
	
	public List<Repository> getFoundRepositories() {
		return foundRepositories;
	}

}