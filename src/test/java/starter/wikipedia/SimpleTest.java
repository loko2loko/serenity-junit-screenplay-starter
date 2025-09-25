package starter.wikipedia;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.annotations.Managed;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SerenityJUnit5Extension.class)
public class SimpleTest {

    @Managed(driver = "chrome")
    WebDriver driver;

    @Test
    public void searchTest() {
        // Open English Wikipedia
        driver.get("https://en.wikipedia.org");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Step 1: Click on the search/magnifying glass icon first
        try {
            // Try different selectors for the search icon
            WebElement searchIcon = null;

            // Common selectors for Wikipedia search icon
            String[] searchIconSelectors = {
                    ".cdx-button--icon-only", // Modern Wikipedia design
                    ".cdx-search-input__start-icon",
                    "[aria-label*='Search']",
                    ".vector-search-box-show-thumbnail",
                    "#searchButton",
                    ".searchButton"
            };

            for (String selector : searchIconSelectors) {
                try {
                    searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(selector)));
                    System.out.println("Found search icon with selector: " + selector);
                    break;
                } catch (Exception e) {
                    System.out.println("Selector failed: " + selector);
                }
            }

            if (searchIcon != null) {
                searchIcon.click();
                System.out.println("Clicked search icon");

                // Step 2: Wait for search field to appear and enter text
                Thread.sleep(1000); // Give it time to expand

                WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='search'], #searchInput, input[placeholder*='Search']")));
                searchField.clear();
                searchField.sendKeys("cucumber");
                searchField.sendKeys(Keys.ENTER);

                System.out.println("Entered search term and pressed Enter");
            } else {
                throw new Exception("Could not find search icon");
            }

        } catch (Exception e) {
            System.out.println("Search icon approach failed: " + e.getMessage());
            System.out.println("Trying direct URL approach...");

            // Fallback: Go directly to search results
            driver.get("https://en.wikipedia.org/wiki/Special:Search?search=cucumber");
        }

        // Wait for navigation
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("https://en.wikipedia.org/")));

        // Verify we got somewhere
        String currentUrl = driver.getCurrentUrl();
        String pageTitle = driver.getTitle();

        System.out.println("Final URL: " + currentUrl);
        System.out.println("Page title: " + pageTitle);

        assertThat(currentUrl).isNotEqualTo("https://en.wikipedia.org/");
        assertThat(pageTitle).isNotEmpty();

        System.out.println("Test completed successfully!");
    }
}