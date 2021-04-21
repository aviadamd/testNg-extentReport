package test.accountManagement.test;

import org.testng.annotations.Test;
import test.FactoryBaseTest;
import test.accountManagement.component.AccountManagementLoginComponent;

public class AccountManagementLogin extends FactoryBaseTest {

    @Test
    public void loginTest() {
        AccountManagementLoginComponent loginComponent
                = new AccountManagementLoginComponent();
        loginComponent.loadLoginPage();
    }
}
