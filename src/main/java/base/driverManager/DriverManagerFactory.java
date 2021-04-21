package base.driverManager;

import base.Base;
import base.driverManager.InitDrivers.RemoteDriverManager;
import base.driverManager.InitDrivers.android.AndroidDriverManager;
import base.driverManager.InitDrivers.web.ChromeDriverManager;
import base.driverManager.InitDrivers.web.FirefoxDriverManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DriverManagerFactory extends Base {

    public static DriverManager getManager(String type) {
        DriverManager driverManager;
        switch (type) {
            case BrowserType.CHROME:
                driverManager = new ChromeDriverManager();
                break;
            case BrowserType.FIREFOX:
                driverManager = new FirefoxDriverManager();
                break;
            case BrowserType.ANDROID:
                driverManager = new AndroidDriverManager();
                break;
            case BrowserType.REMOTE:
                driverManager = new RemoteDriverManager();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        log.debug("init " + type + " before test");
        return driverManager;
    }
}
