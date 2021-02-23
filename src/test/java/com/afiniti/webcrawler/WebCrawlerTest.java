package com.afiniti.webcrawler;

import com.afiniti.webcrawler.model.Page;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebCrawlerTest {

    @Test
    void addSubPage_shouldAddSubPage_whenSetIsEmpty() {
        //Given
        Page aPage = new Page();
        Page aSubPage = new Page();

        //When
        WebCrawler sut = new WebCrawler();
        sut.addSubPage(aPage, aSubPage);

        //Then
        assertEquals(1, aPage.getSubPages().size());
    }

    @Test
    void shouldVisit_shouldReturnFalse_whenAlreadyVisited() {
        //Given
        WebCrawler sut = new WebCrawler();
        Page aPage = new Page("http://www.afiniti.com", 1);
        sut.getVisitedLinks().add(aPage.getUrl());

        //When
        boolean actual = sut.shouldVisit(aPage);

        //Then
        assertFalse(actual);
    }

    @Test
    void shouldVisit_shouldReturnFalse_whenUrlIsNotInDomain() {
        //Given
        WebCrawler sut = new WebCrawler();
        Page aPage = new Page("https://afiniti.facebook.com", 1);
        sut.setDomain("afiniti.com");

        //When
        boolean actual = sut.shouldVisit(aPage);

        //Then
        assertFalse(actual);
    }

    @Test
    void shouldVisit_shouldReturnFalse_whenDomainOccursInParams() {
        //Given
        WebCrawler sut = new WebCrawler();
        Page aPage = new Page("https://www.mafinity.com?u=www.afiniti.com", 1);
        sut.setDomain("afiniti.com");

        //When
        boolean actual = sut.shouldVisit(aPage);

        //Then
        assertFalse(actual);
    }

    @Test
    void shouldVisit_shouldReturnTrue_whenNotVisitedAndInDomain() {
        //Given
        WebCrawler sut = new WebCrawler();
        Page aPage = new Page("https://careers.afiniti.com", 1);
        sut.setDomain("afiniti.com");

        //When
        boolean actual = sut.shouldVisit(aPage);

        //Then
        assertTrue(actual);
    }

    @Test
    void pageSeenBefore_shouldReturnTrue_whenFirstAppearanceDepthIsLowerThanCurrentPage() {
        //Given
        WebCrawler sut = new WebCrawler();
        Page aPage = new Page("https://careers.afiniti.com", 3);
        sut.getFirstAppearanceDepth().put(aPage.getUrl(), 2);

        //When
        boolean actual = sut.pageSeenBefore(aPage);

        //Then
        assertTrue(actual);
    }

    @Test
    void pageSeenBefore_shouldReturnFalse_whenNotAppearedBefore() {
        //Given
        WebCrawler sut = new WebCrawler();
        Page aPage = new Page("https://careers.afiniti.com", 3);

        //When
        boolean actual = sut.pageSeenBefore(aPage);

        //Then
        assertFalse(actual);
    }

    @Test
    void pageSeenBefore_shouldReturnFalse_whenCurrentDepthIsEqualToFirstAppearanceDepth() {
        //Given
        WebCrawler sut = new WebCrawler();
        Page aPage = new Page("https://careers.afiniti.com", 3);
        sut.getFirstAppearanceDepth().put(aPage.getUrl(), 3);

        //When
        boolean actual = sut.pageSeenBefore(aPage);

        //Then
        assertFalse(actual);
    }

    @Test
    void isLinkValid_shouldReturnFalse_whenUrlIsIdentical() {
        //Given
        WebCrawler sut = new WebCrawler();

        //When
        boolean actual = sut.isLinkValid("http://afiniti.com", "http://afiniti.com");

        //Then
        assertFalse(actual);
    }

    @Test
    void isLinkValid_shouldReturnTrue_whenUrlIsValid() {
        //Given
        WebCrawler sut = new WebCrawler();

        //When
        boolean actual = sut.isLinkValid("http://afiniti.com", "http://careers.afiniti.com");

        //Then
        assertTrue(actual);
    }

    @Test
    void isLinkValid_shouldReturnFalse_whenUrlIsForMail() {
        //Given
        WebCrawler sut = new WebCrawler();

        //When
        boolean actual = sut.isLinkValid("http://afiniti.com", "mailto:test@test.com");

        //Then
        assertFalse(actual);
    }

    @Test
    void isLinkValid_shouldReturnFalse_whenUrlIsForPhone() {
        //Given
        WebCrawler sut = new WebCrawler();

        //When
        boolean actual = sut.isLinkValid("http://afiniti.com", "tel:1234567890");

        //Then
        assertFalse(actual);
    }
}
