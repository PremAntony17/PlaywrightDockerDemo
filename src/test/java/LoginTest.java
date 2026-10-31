import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class LoginTest {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeClass
    public void setup() {

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
                .setHeadless(true)
        );

        page = browser.newPage();
        page.setDefaultTimeout(15000);
    }

    @Test
    public void validLoginTest() {

        page.navigate("https://www.saucedemo.com/");

        System.out.println("Website opened: " + page.url());

        page.locator("#user-name").fill("standard_user");
        page.locator("#password").fill("secret_sauce");

        page.locator("#login-button").click();

        // Wait for Products heading
        Locator title = page.locator(".title");

        title.waitFor();
        

        // Debug information
        System.out.println("Current URL: " + page.url());
        System.out.println("Page heading: " + title.innerText());

        // Verify inventory page
        Assert.assertEquals(
            title.innerText(),
            "Products",
            "Products page title should be visible"
        );

        System.out.println("LOGIN TEST PASSED");
    }

    @AfterClass
    public void teardown() {

        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}