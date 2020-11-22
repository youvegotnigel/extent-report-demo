## How to use
**Pre-Requisites:**
1. Java
2. Maven

**Steps:**
1. Add "**MaxSoft Extent Reporter**" dependency into "**pom.xml**".
```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
	
    <dependencies>
        <dependency>
            <groupId>com.github.osandadeshan</groupId>
            <artifactId>maxsoft-extent-reporter</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
```

2. Create "**extent.properties**" in "***src/test/resources/extent.properties***".
```xml
# Extent Report Configs
extent_reporter_theme=dark
capture_screenshot_on_failure=true
extent_document_title=Test Execution Report
extent_reporter_name=Test Execution Report
application_name=OrangeHRM
environment=Production
browser=Chrome
operating_system=Windows 10 - 64 Bit
test_developer=youvegotnigel
```

3. In the test automation code, find the place you are launching the WebDriver.

4. Pass your WebDriver object to the "**setDriver()**" method which can be imported from "***com.maxsoft.extentreport.DriverHolder.setDriver***".
```java
WebDriver driver = new ChromeDriver();
setDriver(driver);
```

5.  Update the places where your are using WebDriver object, into "**getDriver()**" method which can be imported from "***com.maxsoft.extentreport.DriverHolder.getDriver***".
```java
getDriver().manage().window().maximize();
```

6. An example test class.
```java
package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.maxsoft.extentreport.DriverHolder.getDriver;
import static com.maxsoft.extentreport.DriverHolder.setDriver;
import static com.maxsoft.extentreport.PropertyFileReader.getProperty;
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

```

7. Create the "**TestNG.xml**" by adding the "**MaxSoft Extent Reporter listener**" class.
```xml
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >
<suite name="Regression Test Suite">
    <listeners>
        <listener class-name="com.maxsoft.extentreport.ExtentReportListener"/>
    </listeners>
    <test name="Regression Test">
        <classes>
            <class name="LoginTest" />
        </classes>
    </test>
</suite>
```

8. Run the "**TestNG.xml**".
