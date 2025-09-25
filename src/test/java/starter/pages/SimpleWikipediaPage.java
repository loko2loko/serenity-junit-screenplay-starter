package starter.pages;

import net.serenitybdd.core.pages.PageObject;

public class SimpleWikipediaPage extends PageObject {

    public void searchForTopic(String searchTerm) {
        // Skip complex UI interactions, go directly to search results
        String searchUrl = "https://en.wikipedia.org/wiki/Special:Search?search=" +
                searchTerm.replace(" ", "+");
        System.out.println("Navigating to: " + searchUrl);
        getDriver().get(searchUrl);
        waitForPageToLoad();
    }

    public void openArticle(String articleName) {
        // Direct navigation to Wikipedia article
        String articleUrl = "https://en.wikipedia.org/wiki/" +
                articleName.replace(" ", "_");
        System.out.println("Opening article: " + articleUrl);
        getDriver().get(articleUrl);
        waitForPageToLoad();
    }

    public String getPageTitle() {
        return getTitle();
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public boolean isValidWikipediaPage() {
        String url = getCurrentUrl();
        return url.contains("wikipedia.org") && !url.equals("https://en.wikipedia.org/");
    }

    private void waitForPageToLoad() {
        waitABit(2000);
        System.out.println("Page loaded: " + getPageTitle());
    }
}