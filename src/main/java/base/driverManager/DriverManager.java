package base.driverManager;

import base.Base;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.SneakyThrows;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import java.net.InetAddress;
import java.util.Iterator;

public abstract class DriverManager extends Base {

    protected abstract void createDriver();
    protected abstract void stopDriver();

    public WebDriver getDriver() {
        if (driver == null) {
            createDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            stopDriver();
            driver = null;
        }
    }

    @SneakyThrows
    public static Proxy seleniumProxy() {
        mobProxy = new BrowserMobProxyServer();
        mobProxy.setTrustAllServers(true);
        mobProxy.start(0);
        mobProxy.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        mobProxy.enableHarCaptureTypes(
                CaptureType.REQUEST_HEADERS, CaptureType.REQUEST_CONTENT,
                CaptureType.RESPONSE_BINARY_CONTENT, CaptureType.REQUEST_COOKIES,
                CaptureType.RESPONSE_HEADERS, CaptureType.RESPONSE_CONTENT,
                CaptureType.RESPONSE_COOKIES
        );
        seleniumProxy = ClientUtil.createSeleniumProxy(mobProxy);
        String hostIp = InetAddress.getLocalHost().getHostAddress();
        seleniumProxy.setHttpProxy(hostIp + ":" + mobProxy.getPort());
        seleniumProxy.setSslProxy(hostIp + ":" + mobProxy.getPort());
        mobProxy.addRequestFilter((request, contents, messageInfo) -> {
            System.out.println(request.getUri());
            System.out.println(request.getMethod().toString());
            HttpHeaders headers = request.headers();
            Iterator<?> test = headers.iterator();
            Object ob;

            while (test.hasNext()) {
                ob = test.next();
                System.out.println(ob.toString());
            }

            return null;
        });

        return seleniumProxy;
    }
}
