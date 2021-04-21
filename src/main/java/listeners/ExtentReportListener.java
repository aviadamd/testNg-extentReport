package listeners;

import base.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.util.Arrays;
import static com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String;

@Slf4j
public class ExtentReportListener extends Base implements ITestListener {

    @Override
    public void onTestStart(ITestResult iTestResult) {
        test = extent.createTest(iTestResult.getMethod().getMethodName());
        extentAndLog(Status.INFO, "start " + iTestResult.getMethod().getMethodName());
        extent.setSystemInfo("os", "winos");
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setEncoding("utf-8");
        extentAndLog(Status.INFO,"test start method " +
                getTestMethodName(iTestResult) + " start");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        extentAndLog(Status.PASS,"test success method " +
                getTestMethodName(iTestResult) + " succeed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("fail " + result.getName());
        try {
            test.log(Status.FAIL, createScreenCaptureFromBase64String(
                    screenshot(Base.driver),
                    "screen shot result click to open " + result.getMethod().getMethodName()
            ).build());
        } catch (Exception e) {
            log.error("test fail " + e.getMessage());
        }
        test.log(Status.FAIL, MarkupHelper.createLabel(
                "<details>"
                        + "<summary>"
                        + "<b>"
                        + "<font color="
                        + "red>"
                        + "Exception :Click to see"
                        + "</font>"
                        + "</b> \n"
                        + "</summary>"
                        + Arrays.toString(result.getThrowable()
                        .getStackTrace()).replace(",", "<br>")
                        + "</details> \n"
                        + "</summary>",
                ExtentColor.CYAN)
        );
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentAndLog(Status.SKIP, result.getMethod().getMethodName()+ " is skip");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        extentAndLog(Status.WARNING,"fail with steps success " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        extent = new ExtentReports();
        spark = new ExtentSparkReporter("Report/Spark.html");
        extent.attachReporter(spark);
    }

    @Override
    public void onFinish(ITestContext context) {
        extentAndLog(Status.INFO,"close " + context.getName());
        extent.flush();
    }

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    private static String screenshot(WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        String source = ts.getScreenshotAs(OutputType.BASE64);
        return "data:image/jpg;base64, "+source;
    }

    public static void extentAndLog(Status status, String print) {
        switch (status) {
            case FAIL:
                log.error(status.toString() + " " + print);
                test.log(status, print);
                Assert.fail("fail " + print);
                break;
            case INFO: case PASS:
                log.info(status.toString() + " " + print);
                break;
            case SKIP:
                log.debug(status.toString() + " " + print);
                break;
            case WARNING:
                log.warn(status.toString() + " " + print);
                break;
        }
        test.log(status, print);
    }
}
