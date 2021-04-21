package utilities.uiActions;

import base.Base;
import com.aventstack.extentreports.Status;
import utilities.verfications.Verifications;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.function.BiConsumer;
import static listeners.ExtentReportListener.extentAndLog;

@Slf4j
public class UiActions extends Base {

    public void perform(String text, BiConsumer<UiActions, Verifications> actionsConsumer) {
        log.debug(text);
        actionsConsumer.accept(utilities.uiActions(), utilities.verifications());
    }

    public void click(WebElement element) {
        if (elementToBeClickable(element)) {
            extentAndLog(Status.INFO,"attempt click on " + element.getText());
            element.click();
        } else extentAndLog(Status.FAIL,"fail click on " + element.toString());
    }

    public void clickOptional(WebElement element) {
        if (elementToBeClickable(element) || elementPresented(element,1)) {
            click(element);
        }
    }

    public boolean elementPresented(WebElement element, int timeOut) {
        return webDriverWait(timeOut, ExpectedConditions.visibilityOf(element), element);
    }

    public boolean elementToBeClickable(WebElement element) {
       return webDriverWait(10, ExpectedConditions.elementToBeClickable(element), element);
    }

    public void sendKeys(WebElement element, String text) {
        if (elementToBeClickable(element)) {
            element.sendKeys(text);
            extentAndLog(Status.INFO,"send " + text + " to " + element.getText());
        } else extentAndLog(Status.FAIL,"fail send keys to " + element.toString());
    }

    public void selectByVisibleText(WebElement element, String text) {
        Select value = new Select(element);
        value.selectByVisibleText(text);
    }

    public void selectByValue(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void mouseHoverElements(WebElement element1, WebElement element2) {
        elementPresented(element1,5);
        Actions actions = new Actions(driver);
        actions.moveToElement(element1)
                .moveToElement(element2)
                .click()
                .build()
                .perform();
    }

    public <T> boolean webDriverWait(int timeOut, ExpectedCondition<T> conditions, WebElement element) {
        try {
            new WebDriverWait(driver, timeOut).until(conditions.compose(driver -> {
                extentAndLog(Status.INFO,"condition :" + element.getText() +
                        conditions.toString() + " is true");
                return null;
            }));
            return true;
        } catch (TimeoutException | NullPointerException e) {
            extentAndLog(Status.WARNING,
                    conditions.toString() + " is false " + e.getMessage());
        } catch (WebDriverException w) {
            extentAndLog(Status.WARNING,
                    conditions.toString() + " is false " + w.getAdditionalInformation());
        }
        return false;
    }


    public void clear(WebElement element) {
        click(element);
        element.clear();
    }

    public void elementScreenShot(WebElement imageElement, String imageName) {
        try {
            imageScreenShot = new AShot()
                    .coordsProvider(new WebDriverCoordsProvider())
                    .takeScreenshot(driver,imageElement);
            ImageIO.write(imageScreenShot.getImage(),"png",
                    new File("./imageRepository/" + imageName));
        } catch (IOException ioException) {
            log.debug(ioException.getMessage());
        }
    }

    public static String cleanElementString(String element) {
        return element.replaceAll("[.*?]","");
    }
}
