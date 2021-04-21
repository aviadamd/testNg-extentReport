package base.driverManager.InitDrivers.web;

import base.driverManager.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Description;
import java.util.function.Supplier;

@Description("use as a class that extends DriverManager abstract class template")
public class FirefoxDriverManager extends DriverManager {

    @Override
    protected void createDriver() {
        firefoxDriverSupplier.get();
        new SharedWebManager().clearCache();
    }

    @Override
    protected void stopDriver() {
        new SharedWebManager().stopProxy();
        driver.quit();
    }

    private final Supplier<WebDriver> firefoxDriverSupplier = () -> {
        Proxy seleniumProxy = seleniumProxy();
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver(firefoxOptions(seleniumProxy));
        return driver;
    };

    private static FirefoxOptions firefoxOptions(Proxy seleniumProxy) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("disable-restore-session-state");
        firefoxOptions.addArguments("disable-notifications");
        firefoxOptions.addArguments("disable-infobars");
        firefoxOptions.addArguments("start-maximized");
        DesiredCapabilities seleniumCapabilities = new DesiredCapabilities();
        seleniumCapabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        seleniumCapabilities = DesiredCapabilities.firefox();
        seleniumCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        firefoxOptions.merge(seleniumCapabilities);
        return firefoxOptions;
    }
}
