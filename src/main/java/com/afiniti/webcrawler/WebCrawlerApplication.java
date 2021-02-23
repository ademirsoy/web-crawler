package com.afiniti.webcrawler;

import com.afiniti.webcrawler.model.SiteMap;

import java.net.URISyntaxException;

public class WebCrawlerApplication {

    public static final String ROOT_LINK = "https://www.afiniti.com/";

    public static void main(String[] args) throws URISyntaxException {

        long now = System.currentTimeMillis();
        String mainLink = args.length > 0 ? args[0] : ROOT_LINK;
        System.out.println(mainLink);

        WebCrawler webCrawler = new WebCrawler();
        SiteMap siteMap = webCrawler.constructAndPrintSiteMap(mainLink);

        long duration = (System.currentTimeMillis() - now) / 100;
        System.out.println("Constructed site map in " + duration + " seconds.");
    }
}
