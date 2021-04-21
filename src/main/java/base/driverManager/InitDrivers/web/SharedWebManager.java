package base.driverManager.InitDrivers.web;

import base.Base;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.openqa.selenium.Cookie;

import java.util.Set;

public class SharedWebManager extends Base {

    public void stopProxy() {
        if (mobProxy != null && ((BrowserMobProxyServer) mobProxy).isStopped()) {
            mobProxy.stop();
        }
    }

    public void clearCache() {
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie cookie : cookies) {
            driver.manage().deleteCookieNamed(cookie.getName());
        }
        cookies = driver.manage().getCookies();
        if (cookies.size() == 0) {
            System.out.println("clear browser cache");
        }
    }
}
