package test.flightPageRegistration.components;

import lombok.extern.slf4j.Slf4j;
import test.FactoryBaseTest;

@Slf4j
public class RegistrationConfirmationComponent extends FactoryBaseTest {

    public void goToFlightDetailsPage() {
        utilities.uiActions().elementPresented(flightUi.registrationConfirmationPage.signinLink,5);
        utilities.uiActions().click(flightUi.registrationConfirmationPage.signinLink);
    }
}
