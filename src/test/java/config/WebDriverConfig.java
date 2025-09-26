package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;

/**
 * WebDriver konfigurácia pre CI/CD prostredie
 * Použite ak máte problémy s @Managed anotáciou
 */
public class WebDriverConfig {

    public static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();

        // CI/CD argumenty (GitHub Actions, Jenkins, etc.)
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-first-run");
        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-translate");
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--disable-device-discovery-notifications");

        // Unique user data directory pre každý test run
        String userDataDir = "/tmp/chrome-test-" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + userDataDir);

        // Window size pre headless mode
        options.addArguments("--window-size=1920,1080");

        // Performance optimizations
        options.addArguments("--memory-pressure-off");
        options.addArguments("--max_old_space_size=4096");

        // Disable logging
        options.addArguments("--disable-logging");
        options.addArguments("--log-level=3");
        options.addArguments("--silent");

        // Local development override
        if (isLocalDevelopment()) {
            options = getLocalDevelopmentOptions();
        }

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return driver;
    }

    private static boolean isLocalDevelopment() {
        return System.getenv("CI") == null &&
                System.getenv("GITHUB_ACTIONS") == null;
    }

    private static ChromeOptions getLocalDevelopmentOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");
        // Pre debugging - môžete odkomentovať
        // options.addArguments("--auto-open-devtools-for-tabs");
        return options;
    }
}