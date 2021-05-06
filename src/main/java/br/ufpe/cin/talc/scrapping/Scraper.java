package br.ufpe.cin.talc.scrapping;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class Scraper {

	public abstract void scrap();
	
	protected Document fetchPage(String page) throws IOException {
		return Jsoup.connect(page).get();
	}
	
}
