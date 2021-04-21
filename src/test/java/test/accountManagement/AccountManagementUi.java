package test.accountManagement;

import org.openqa.selenium.WebDriver;
import test.accountManagement.pages.LoginPage;

public class AccountManagementUi {

    public LoginPage loginPage;

    public AccountManagementUi(WebDriver driver) {
        this.loginPage = new LoginPage(driver);
    }
}
