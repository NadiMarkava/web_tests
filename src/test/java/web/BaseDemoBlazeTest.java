package web;

import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import static web.utils.ScreenshotUtil.takeScreenshot;

@Listeners(BaseDemoBlazeTest.class)
public class BaseDemoBlazeTest implements IAbstractTest, ITestListener {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.debug("CarinaListener->onTestFailure");
        String methodName = result.getName().trim() + result.id();
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                takeScreenshot(methodName, getDriver());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @BeforeMethod
    public WebDriver setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        WebDriver driver = getDriver(DEFAULT, options);
        return driver;
    }
}