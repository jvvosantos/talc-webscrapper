package br.ufpe.cin.talc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ufpe.cin.talc.scrapping.Repository;
import br.ufpe.cin.talc.scrapping.RepositoryFinder;
import br.ufpe.cin.talc.scrapping.RepositoryNavigator;

public class WebscrapperApp {
	
	public static void main(String[] args) {
		RepositoryFinder finder = new RepositoryFinder();
		finder.scrap();
		
		RepositoryNavigator navigator = new RepositoryNavigator(finder.getFoundRepositories());
		navigator.scrap();
		
		List<Repository> processedRepositories = new ArrayList<>();
		
		Collections.sort(processedRepositories, new Comparator<Repository>() {

			@Override
			public int compare(Repository o1, Repository o2) {
				if (o1.getPrintingOccurrences() < o2.getPrintingOccurrences()) {
					return -1;
				}
				else if (o1.getPrintingOccurrences() > o2.getPrintingOccurrences()) {
					return 1;
				}
				else {
					return 0;
				}
			}
		});
		
		// Get the 10 repositories with most occurrences
		processedRepositories = processedRepositories.subList(0, 9);
		
		// print info
		for (Repository repository : processedRepositories) {
			System.out.println("{");
			System.out.println("\tRepository name: "+repository.getTitle());
			System.out.println("\tRepository URL: "+repository.getUrl());
			System.out.println("}");
		}
	}

}
