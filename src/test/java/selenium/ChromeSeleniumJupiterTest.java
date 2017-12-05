package selenium;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.bonigarcia.SeleniumExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * https://bonigarcia.github.io/selenium-jupiter/
 */
@ExtendWith(SeleniumExtension.class)
class FirefoxSeleniumJupiterTest {

  @BeforeEach
  void setUp() {
    System.setProperty("webdriver.gecko.driver", "F:/geckodriver.exe");
  }

  @Test
  void testWithOneFirefox() {
    FirefoxDriver driver=new FirefoxDriver();
    driver.get("https://bonigarcia.github.io/selenium-jupiter/");

    assertTrue(driver.getTitle().startsWith("selenium-jupiter"));
    driver.quit();
  }

  @Test
  void testWithTwoFirefoxs() {
    WebDriver driver = new FirefoxDriver();
    WebDriver driver2 = new FirefoxDriver();
    driver.get("http://www.seleniumhq.org/");
    driver2.get("http://junit.org/junit5/");

    assertTrue(driver.getTitle().startsWith("Selenium"));
    assertTrue(driver2.getTitle().equals("JUnit 5"));

    driver.quit();
    driver2.quit();
  }

}
