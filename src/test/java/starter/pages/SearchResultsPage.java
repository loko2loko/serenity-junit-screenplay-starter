package starter.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

public class SearchResultsPage extends PageObject {

    @FindBy(id = "firstHeading")
    private WebElementFacade articleHeading;

    @FindBy(css = ".mw-search-results")
    private WebElementFacade searchResultsList;

    @FindBy(css = ".searchresults")
    private WebElementFacade searchResults;

    @FindBy(css = ".mw-search-result-heading")
    private WebElementFacade firstSearchResult;

    public boolean isArticlePage() {
        try {
            return articleHeading.isCurrentlyVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSearchResultsPage() {
        try {
            return searchResultsList.isCurrentlyVisible() ||
                    searchResults.isCurrentlyVisible() ||
                    firstSearchResult.isCurrentlyVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public String getArticleHeading() {
        if (isArticlePage()) {
            return articleHeading.getText();
        }
        return "";
    }

    public boolean hasSearchResults() {
        return isSearchResultsPage();
    }

    public String getPageType() {
        if (isArticlePage()) {
            return "Article Page";
        } else if (isSearchResultsPage()) {
            return "Search Results Page";
        } else {
            return "Unknown Page";
        }
    }

    public void waitForPageToLoad() {
        waitABit(2000);
        // Additional wait conditions could be added here
    }
}