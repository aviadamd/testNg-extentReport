package pagesInit;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class InitMobilePages {

    public InitMobilePages(WebDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }
}
