package utilities.verfications;

import base.Base;
import com.aventstack.extentreports.Status;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.Collections;

import static listeners.ExtentReportListener.extentAndLog;

@Slf4j
public class Verifications extends Base {

    public void load(WebElement element) {
        load(new ArrayList<>(Collections.singleton(element)));
    }

    public void load(ArrayList<WebElement> elements) {
        for (WebElement pageEle : elements) {
            if (utilities.uiActions().elementPresented(pageEle, 5)) {
                extentAndLog(Status.INFO,"load " + pageEle.toString());
            } else {
                extentAndLog(Status.FAIL, "fail load " + pageEle.toString());
            }
        }
    }

    public void verifyNumberOfElements(ArrayList<WebElement> elements, int numbers) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver,10);
            webDriverWait.until(
                    ExpectedConditions.visibilityOf(elements.get(elements.size() -1)));
            Assert.assertEquals(elements.size(), numbers);
        } catch (WebDriverException webDriverException) {
            log.debug(webDriverException.getMessage());
            Assert.fail(webDriverException.getMessage());
        }
    }

    public boolean compereTexts(ArrayList<WebElement> elements, ArrayList<String> text) {
        if (elements.size() != text.size()) {
            Assert.fail(elements.size() + " not equals to " + text.size());
            return false;
        }

        for (int i = 0; i < elements.size(); i++) {
            if (isTextEquals(elements.get(i), text.get(i))) {
                log.debug("actual text " + elements.get(i).getText() +
                        " equals expected test " + text.get(i));
                return true;
            } else {
                log.debug("actual text " + elements.get(i).getText() +
                        " not equals expected test " + text.get(i));
            }
        }

        return false;
    }

    public boolean isTextEquals(WebElement actual, String expectedText) {
        load(actual);
        String text = actual.getText().trim();
        return text.equals(expectedText) ||
                text.contentEquals(expectedText) ||
                text.contains(expectedText);
    }

}
