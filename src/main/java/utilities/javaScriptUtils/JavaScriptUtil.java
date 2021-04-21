package utilities.javaScriptUtils;

import base.Base;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import java.util.Collections;
import java.util.List;

@Slf4j
public class JavaScriptUtil extends Base {

  private JavascriptExecutor getJavascriptExecutor() {
    return ((JavascriptExecutor) driver);
  }
  private void executeUsingJavaScript(String executionScript, WebElement element) {
    getJavascriptExecutor().executeScript(executionScript, element);
  }

  public void flash(WebElement element) {
    flash(Collections.singletonList(element));
  }

  public void flash(List<WebElement> elements) {
    for (WebElement element : elements) {
      changeColor("rgb(0,200,0)", element); // 1
      changeColor(element.getCssValue("backgroundColor"), element); // 2
    }
  }

  private void changeColor(String color, WebElement element) {
    executeUsingJavaScript("arguments[0].style.backgroundColor = '" + color + "'", element);
    try {
      Thread.sleep(20);
    } catch (InterruptedException e) {
      log.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public void drawBorder(WebElement element) {
    executeUsingJavaScript("arguments[0].style.border='3px solid red'", element);
  }

  public void generateAlert(String message) {

    getJavascriptExecutor().executeScript("alert('" + message + "')");
  }

  public void clickElementByJS(WebElement element) {

    executeUsingJavaScript("arguments[0].click();", element);
  }

  public void refreshBrowserByJS() {

    getJavascriptExecutor().executeScript("history.go(0)");
  }

  public String getTitleByJS() {
    return getJavascriptExecutor().executeScript("return document.title;").toString();
  }

  public String getPageInnerText() {
    return getJavascriptExecutor().executeScript("return document.documentElement.innerText;").toString();
  }

  public void scrollPageDown() {
    getJavascriptExecutor().executeScript("window.scrollTo(0,document.body.scrollHeight)");
  }

  public void scrollIntoView(WebElement element) {
    executeUsingJavaScript("arguments[0].scrollIntoView(true);", element);
  }

  public String getBrowserInfo() {
    return getJavascriptExecutor().executeScript("return navigator.userAgent;").toString();
  }

  public void sendKeysUsingJSWithId(String id, String value) {
    getJavascriptExecutor().executeScript("document.getElementById('" + id + "').value='" + value + "'");
  }

  public void sendKeysUsingJSWithName(String name, String value) {
    getJavascriptExecutor().executeScript("document.getElementByName('" + name + "').value='" + value + "'");
  }

  public void checkPageIsReady() {
    //given if condition will check ready state of page.
    if (getJavascriptExecutor().executeScript("return document.readyState").toString().equals("complete")) {
      log.debug("page Is loaded.");
      return;
    }

    for (int i = 0; i < 15; i++) {
      sleep(1000);
      // check page ready state.
      if (getJavascriptExecutor().executeScript("return document.readyState").toString().equals("complete")) {
        log.debug("page Is loaded.");
        break;
      }
    }
  }

  private void sleep(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException interruptedException) {
      log.error(interruptedException.getMessage());
      Thread.currentThread().interrupt();
    }
  }
}
