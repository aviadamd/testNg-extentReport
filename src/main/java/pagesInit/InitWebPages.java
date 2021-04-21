package pagesInit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class InitWebPages {

    public InitWebPages(WebDriver driver) {

        PageFactory.initElements(driver,this);
    }
}
