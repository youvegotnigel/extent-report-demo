import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.maxsoft.extentreport.DriverHolder.getDriver;
import static com.maxsoft.extentreport.DriverHolder.setDriver;
import static org.testng.Assert.assertEquals;

public class LoginTest {
    private WebElement usernameTextBox;
    private WebElement passwordTextBox;
    private WebElement logInButton;

    @BeforeMethod
    public void before() {

        //System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        setDriver(new ChromeDriver());
        getDriver().manage().window().maximize();
        getDriver().get("https://opensource-demo.orangehrmlive.com/");
        usernameTextBox = getDriver().findElement(By.id("txtUsername"));
        passwordTextBox = getDriver().findElement(By.id("txtPassword"));
        logInButton = getDriver().findElement(By.id("btnLogin"));
    }

    @Test(description = "Verify that a valid user can login to the application")
    public void testValidLogin() {
        usernameTextBox.sendKeys("Admin");
        passwordTextBox.sendKeys("admin123");
        logInButton.click();
        assertEquals(getDriver().findElement(By.id("welcome")).getText(), "Welcome Paul");
    }

    @Test(description = "Verify that an invalid user cannot login to the application")
    public void testInvalidLogin() {
        usernameTextBox.sendKeys("Admin");
        passwordTextBox.sendKeys("admin");
        logInButton.click();
        assertEquals(getDriver().getTitle(), "OrangeHRM");
    }

    @AfterMethod
    public void after() {
        getDriver().quit();
    }
}
