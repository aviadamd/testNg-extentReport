package test;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import test.accountManagement.AccountManagementUi;
import test.flightPageRegistration.FlightUi;

public class InitElementsManager extends FactoryBaseTest {

    public void initElements(WebDriver driver) {
        if (driver != null) {
            if (driver instanceof AndroidDriver<?>) {
                aManUi = new AccountManagementUi(driver);
            } else if (driver instanceof ChromeDriver || driver instanceof FirefoxDriver) {
                navigateToUrl(getProperty.url);
                flightUi = new FlightUi(driver);
            } else throw new RuntimeException("error driver initiation");
        } else throw new RuntimeException("driver is null");
    }
}
