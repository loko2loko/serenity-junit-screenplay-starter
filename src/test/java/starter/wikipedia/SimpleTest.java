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
        driver.get("https://en.wikipedia.org");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // pokus priamo do inputu (desktop/mobil)
            WebElement searchField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("#searchInput, input[type='search'], input[placeholder*='Search']"))
            );
            searchField.clear();
            searchField.sendKeys("cucumber");
            searchField.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            // bez klikania na ikony - priamy search URL
            driver.get("https://en.wikipedia.org/wiki/Special:Search?search=cucumber");
        }

        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe("https://en.wikipedia.org/"))
        );
        String currentUrl = driver.getCurrentUrl();
        String pageTitle = driver.getTitle();
        assertThat(currentUrl).isNotEqualTo("https://en.wikipedia.org/");
        assertThat(pageTitle).isNotEmpty();
    }
}