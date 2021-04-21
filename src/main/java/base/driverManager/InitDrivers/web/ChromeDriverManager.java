package base.driverManager.InitDrivers.web;

import base.driverManager.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Description;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;

@Description("use as a class that extends DriverManager abstract class template")
public class ChromeDriverManager extends DriverManager {

    @Override
    protected void createDriver() {
        chromeDriverSupplier.get();
        new SharedWebManager().clearCache();
    }

    @Override
    protected void stopDriver() {
        new SharedWebManager().stopProxy();
        driver.quit();
    }

    private final Supplier<WebDriver> chromeDriverSupplier = () -> {
        Proxy seleniumProxy = seleniumProxy();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions(seleniumProxy));
        return driver;
    };

    private ChromeOptions chromeOptions(Proxy seleniumProxy) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("disable-notifications");
        options.addArguments("disable-infobars");
        options.addArguments("start-maximized");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", singletonList("enable-automation"));
        DesiredCapabilities seleniumCapabilities = new DesiredCapabilities();
        seleniumCapabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        seleniumCapabilities = DesiredCapabilities.chrome();
        seleniumCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.merge(seleniumCapabilities);
        return options;
    }
}
