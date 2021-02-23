package com.afiniti.webcrawler;

import com.afiniti.webcrawler.model.Page;
import com.afiniti.webcrawler.model.SiteMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;

public class WebCrawler {

    private final HashSet<String> visitedLinks = new HashSet<>();
    private final HashMap<String, Integer> firstAppearanceDepth = new HashMap<>();
    private String domain;

    public SiteMap constructAndPrintSiteMap(String rootUrl) throws URISyntaxException {
        URI uri = new URI(rootUrl);
        this.domain = uri.getHost();
        this.domain = this.domain.startsWith("www.") ? this.domain.substring(4) : this.domain;

        Page entryPage = new Page(rootUrl, 0);
        SiteMap siteMap = new SiteMap();
        siteMap.setEntryPage(entryPage);
        this.firstAppearanceDepth.put(entryPage.getUrl(), entryPage.getDepth());
        this.crawlForLinks(entryPage);
        return siteMap;
    }

    private void crawlForLinks(Page page) {
        try {
            Document document = Jsoup.connect(page.getUrl()).get();
            Elements anchors = document.select("a[href]");

            for (Element anchor : anchors) {
                String link = anchor.attr("abs:href");
                if (isLinkValid(page.getUrl(), link)) {
                    Page subPage = new Page(link, page.getDepth() + 1);
                    addSubPage(page, subPage);

                    if (firstAppearanceDepth.get(subPage.getUrl()) == null) {
                        firstAppearanceDepth.put(subPage.getUrl(), subPage.getDepth());                }
                    }
            }
            if (page.getSubPages() != null) {
                for (Page p : page.getSubPages()) {
                    if (!this.pageSeenBefore(p)) {
                        this.printPage(p);
                        if (this.shouldVisit(p)) {
                            this.markVisited(p);
                            this.crawlForLinks(p);
                        }
                    }
                }
            }
        } catch (Exception e) {
            //Ignore errors
            //Do not log errors to keep the site map output clear
            //e.printStackTrace();
        }
    }

    private void printPage(Page p) {
        printDashes(p.getDepth());
        System.out.println(p.getUrl());
    }

    protected void addSubPage(Page page, Page subPage) {
        HashSet<Page> subPages = page.getSubPages();
        if (subPages == null) {
            subPages = new HashSet<>();
            page.setSubPages(subPages);
        }
        subPages.add(subPage);
    }

    protected boolean shouldVisit(Page page) {
        if (isVisited(page) || !page.getUrl().matches("https://\\w*\\." + this.domain + ".*")) {
            return false;
        }
        return true;
    }

    // If the depth of the page is marked in some depth other than current
    protected boolean pageSeenBefore(Page page) {
        Integer depthFirstAppeared = firstAppearanceDepth.get(page.getUrl());
        return depthFirstAppeared != null && !depthFirstAppeared.equals(page.getDepth());
    }

    protected boolean isLinkValid(String selfUrl, String href) {
        return !href.equals(selfUrl) && !href.contains("mailto:") && !href.contains("tel:");
    }

    private void printDashes(int times) {
        for (int i = 0; i < times; i++) {
            System.out.print("-");
        }
        System.out.print(" ");
    }

    private void markVisited(Page page) {
        this.visitedLinks.add(page.getUrl());
    }

    private boolean isVisited(Page page) {
        return this.visitedLinks.contains(page.getUrl());
    }

    public HashSet<String> getVisitedLinks() {
        return visitedLinks;
    }

    public HashMap<String, Integer> getFirstAppearanceDepth() {
        return firstAppearanceDepth;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
