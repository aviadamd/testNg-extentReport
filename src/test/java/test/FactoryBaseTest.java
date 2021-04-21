package test;

import base.Base;
import base.driverManager.DriverManager;
import base.driverManager.DriverManagerFactory;
import listeners.ExtentReportListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import test.accountManagement.AccountManagementUi;
import test.flightPageRegistration.FlightUi;

@Listeners(ExtentReportListener.class)
public class FactoryBaseTest extends Base {

    private DriverManager driverManager;
    public static FlightUi flightUi;
    public static AccountManagementUi aManUi;

    @BeforeClass(description = "start sessions")
    public void beforeClass() {
        driverManager = DriverManagerFactory.getManager(getProperty.platformType);
        driver = driverManager.getDriver();
        InitElementsManager elements = new InitElementsManager();
        elements.initElements(driver);
    }

    @AfterClass(description = "quit sessions")
    public void afterClass() {

        driverManager.quitDriver();
    }
}
