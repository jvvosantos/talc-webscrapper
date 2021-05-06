package br.ufpe.cin.talc.scrapping;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RepositoryNavigator extends Scraper {

	private List<Repository> repositories;

	public RepositoryNavigator(List<Repository> repositories) {
		this.repositories = repositories;
	}

	@Override
	public void scrap() {
		String repositoryUrl;
		for (Repository repository : repositories) {
			repositoryUrl = repository.getUrl();

			try {
				int printingOcurrences = searchRepository(repositoryUrl);

				repository.setPrintingOccurrences(printingOcurrences);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private int searchRepository(String url) throws IOException, InterruptedException {
		Document doc = fetchPage(url);
		Elements files = doc.getElementsByClass("js-navigation-open");

		String fileName;

		int occurrences = 0;
		for (Element file : files) {
			Thread.sleep(100);
			fileName = file.text();

			if (fileName.contains("Jump to")) {
				continue;
			}

			if (fileName.equals(". .")) {
				continue;
			}
			else {
				Elements links = file.getElementsByTag("a");
				String href = "";
				for (Element link : links) {
					href = link.getElementsByAttribute("href").attr("href");
				}
				if (fileName.endsWith(".java")) {
					href = href.replace("blob/", "");
					return countPrintingOccurrences(href);
				}
				else {
					occurrences += searchRepository("https://github.com"+href);
				}

			}
		}

		return occurrences;
	}

	public int countPrintingOccurrences(String href) throws IOException {
		String url = "https://raw.githubusercontent.com"+href;
		Document doc = fetchPage(url);

		String javaClass = doc.text();

		Pattern p = Pattern.compile("System.out.print");
		Matcher m = p.matcher(javaClass);
		int count = 0;
		while (m.find()){
			count +=1;
		}
		
		return count;
	}

}
