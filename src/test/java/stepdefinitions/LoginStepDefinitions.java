package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginStepDefinitions {

    @Managed(driver = "chrome", options = "--headless;--no-sandbox;--disable-dev-shm-usage")
    private WebDriver webDriver;

    private Actor user = Actor.named("Test User");

    // Page elements (v reálnom projekte by boli v Page Objects)
    private static final Target LOGIN_PAGE = Target.the("login page")
            .locatedBy("//h1[text()='Login']");

    private static final Target USERNAME_FIELD = Target.the("username field")
            .located(By.id("username"));

    private static final Target PASSWORD_FIELD = Target.the("password field")
            .located(By.id("password"));

    private static final Target LOGIN_BUTTON = Target.the("login button")
            .located(By.id("loginBtn"));

    private static final Target WELCOME_MESSAGE = Target.the("welcome message")
            .located(By.className("welcome-message"));

    private static final Target ERROR_MESSAGE = Target.the("error message")
            .located(By.className("error-message"));

    @Before
    public void setup() {
        user.can(BrowseTheWeb.with(webDriver));
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        user.attemptsTo(
                Open.url("https://example.com/login") // Zmeňte na vašu URL
        );

        user.attemptsTo(
                Ensure.that(LOGIN_PAGE).isDisplayed()
        );
    }

    @When("I enter username {string}")
    public void i_enter_username(String username) {
        user.attemptsTo(
                Enter.theValue(username).into(USERNAME_FIELD)
        );
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        user.attemptsTo(
                Enter.theValue(password).into(PASSWORD_FIELD)
        );
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        user.attemptsTo(
                Click.on(LOGIN_BUTTON)
        );
    }

    @Then("I should be redirected to the dashboard")
    public void i_should_be_redirected_to_the_dashboard() {
        user.attemptsTo(
                Ensure.that(webDriver.getCurrentUrl()).contains("/dashboard")
        );
    }

    @Then("I should see welcome message {string}")
    public void i_should_see_welcome_message(String expectedMessage) {
        user.attemptsTo(
                Ensure.that(Text.of(WELCOME_MESSAGE)).isEqualTo(expectedMessage)
        );
    }

    @Then("I should see error message {string}")
    public void i_should_see_error_message(String expectedError) {
        user.attemptsTo(
                Ensure.that(Text.of(ERROR_MESSAGE)).contains(expectedError)
        );
    }

    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        user.attemptsTo(
                Ensure.that(webDriver.getCurrentUrl()).contains("/login")
        );
    }
}