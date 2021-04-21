package base.driverManager.InitDrivers;

import base.driverManager.DriverManager;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class RemoteDriverManager extends DriverManager {

    @Override
    protected void createDriver() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        switch (getProperty.remotePlatformType) {
            case BrowserType.CHROME:
                desiredCapabilities = DesiredCapabilities.chrome();
                break;
            case BrowserType.FIREFOX:
                desiredCapabilities = DesiredCapabilities.firefox();
                break;
            case BrowserType.ANDROID:
                desiredCapabilities = DesiredCapabilities.android();
                break;
        }
        driver = remoteDriverInitiation(desiredCapabilities);
    }

    @Override
    protected void stopDriver() {
        driver.quit();
    }

    @SneakyThrows
    private WebDriver remoteDriverInitiation(DesiredCapabilities capabilities) {
        URL url = new URL("http://"+getProperty.hubHost+":4444/wd/hub");
        return new RemoteWebDriver(url, capabilities);
    }
}
