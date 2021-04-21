package test.flightPageRegistration.components;

import lombok.extern.slf4j.Slf4j;
import test.FactoryBaseTest;

@Slf4j
public class RegistrationComponent extends FactoryBaseTest {

    public void landingPage() {
        utilities.uiActions().elementPresented(flightUi.registrationPage.firstNameTxt,5);
    }

    public void enterUserDetails(String firstName, String lastName){
        utilities.uiActions().sendKeys(flightUi.registrationPage.firstNameTxt, firstName);
        utilities.uiActions().sendKeys(flightUi.registrationPage.lastNameTxt, lastName);
    }

    public void enterUserCredentials(String username, String password){
        utilities.uiActions().sendKeys(flightUi.registrationPage.usernameTxt, username);
        utilities.uiActions().sendKeys(flightUi.registrationPage.passwordTxt, password);
        utilities.uiActions().sendKeys(flightUi.registrationPage.confirmPasswordTxt, password);
    }

    public void submit(){
        utilities.uiActions()
                .click(flightUi.registrationPage.submitBtn);
    }
}
