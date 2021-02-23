package com.afiniti.webcrawler.model;

import java.util.HashSet;

public class Page {

    private String url;
    private HashSet<Page> subPages;
    private int depth;

    public Page() {
    }

    public Page(String url, int depth) {
        this.url = this.trimUrl(url);
        this.depth = depth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = this.trimUrl(url);
    }

    public HashSet<Page> getSubPages() {
        return subPages;
    }

    public void setSubPages(HashSet<Page> subPages) {
        this.subPages = subPages;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    private String trimUrl(String url) {
        if (url != null && !"".equals(url) && "/".equals(url.substring(url.length() - 1))) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }
}
