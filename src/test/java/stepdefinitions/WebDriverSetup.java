package stepdefinitions;

import io.cucumber.java.BeforeAll;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * WebDriver setup pre automatické sťahovanie ChromeDriver
 */
public class WebDriverSetup {

    @BeforeAll
    public static void setupWebDriver() {
        try {
            // WebDriverManager automaticky stiahne správnu verziu ChromeDriver
            WebDriverManager.chromedriver().setup();
            System.out.println("✅ ChromeDriver successfully configured by WebDriverManager");

            // Debug info - len základné informácie (bez protected methods)
            System.out.println("WebDriverManager version: 5.6.4");
            System.out.println("ChromeDriver will be downloaded automatically if needed");

            // Overenie že systém property je nastavené
            String chromeDriverPath = System.getProperty("webdriver.chrome.driver");
            if (chromeDriverPath != null && !chromeDriverPath.isEmpty()) {
                System.out.println("ChromeDriver path: " + chromeDriverPath);
            } else {
                System.out.println("ChromeDriver will be managed automatically by WebDriverManager");
            }

        } catch (Exception e) {
            System.err.println("❌ Failed to setup ChromeDriver: " + e.getMessage());
            // Suppress stack trace v teste, ale ukáž hlavnú správu
            if (System.getProperty("maven.test.debug") != null) {
                e.printStackTrace();
            }
        }
    }
}