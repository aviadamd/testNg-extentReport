package test.flightPageRegistration.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pagesInit.InitWebPages;

public class RegistrationConfirmationPage extends InitWebPages {

    public RegistrationConfirmationPage(WebDriver driver){
        super(driver);
    }

    @FindBy(partialLinkText = "sign-in")
    public WebElement signinLink;

    @FindBy(linkText = "Flights")
    public WebElement flightsLink;

}
