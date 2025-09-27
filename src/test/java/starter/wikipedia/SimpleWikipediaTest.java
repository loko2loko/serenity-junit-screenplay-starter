package starter.wikipedia;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import starter.pages.SimpleWikipediaPage;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SerenityJUnit5Extension.class)
public class SimpleWikipediaTest {

    @Managed(driver = "chrome")
    WebDriver driver;

    SimpleWikipediaPage wikipediaPage;

    @Test
    @Title("Search for cucumber - reliable approach")
    public void searchForCucumberReliable() {
        wikipediaPage.searchForTopic("cucumber");

        assertThat(wikipediaPage.isValidWikipediaPage()).isTrue();

        String title = wikipediaPage.getPageTitle();
        System.out.println("Found page: " + title);
        assertThat(title).isNotEmpty();
    }

    @Test
    @Title("Open specific Wikipedia articles")
    public void openSpecificArticles() {
        String[] articles = {"Java_(programming_language)", "Python_(programming_language)", "Cucumber"};

        for (String article : articles) {
            wikipediaPage.openArticle(article);

            assertThat(wikipediaPage.isValidWikipediaPage()).isTrue();

            String title = wikipediaPage.getPageTitle();
            System.out.println("Article: " + article + " -> Title: " + title);
            assertThat(title).isNotEmpty();
        }
    }

    @Test
    @Title("Search for programming topics")
    public void searchProgrammingTopics() {
        String[] topics = {"JavaScript", "HTML", "CSS", "React"};

        for (String topic : topics) {
            wikipediaPage.searchForTopic(topic);

            assertThat(wikipediaPage.isValidWikipediaPage()).isTrue();
            System.out.println("Search for " + topic + ": " + wikipediaPage.getPageTitle());
        }
    }
}