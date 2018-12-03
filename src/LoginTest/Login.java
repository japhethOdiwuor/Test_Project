package LoginTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Login {

	public static Properties config = new Properties();
	public static FileInputStream fis;

	public static WebDriverWait wait;
	public static String browser;
	public static String username;
	public static String password;

	WebDriver driver;

	@Test(priority = 1)
	public void openBrowser() throws InterruptedException, IOException {

		if (driver == null) {

			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\test resources\\properties\\Config.properties");

			config.load(fis);

			if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {

				browser = System.getenv("browser");
			} else {

				browser = config.getProperty("browser");

			}

			config.setProperty("browser", browser);

			if (config.getProperty("browser").equals("firefox")) {

				System.setProperty("webdriver.gecko.driver", "\\test resources\\WebDriver\\chromedriver.exe");
				driver = new FirefoxDriver();

			} else if (config.getProperty("browser").equals("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\test resources\\WebDrivers\\chromedriver.exe");
				driver = new ChromeDriver();

			} 
			driver.get(config.getProperty("baseURL"));

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
		}

	}

	@Test(priority = 2)
	public void setloginCredentials() throws InterruptedException {

		driver.findElement(By.xpath("//input[@class='_2zrpKA']")).sendKeys(config.getProperty("username"));
		
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(config.getProperty("password"));
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[contains(.,'Login')]")).click();
		Thread.sleep(2000);
	}

	@Test(priority = 3)
	public void verifyLogin() throws InterruptedException {

		Thread.sleep(2000);
		String tempText = driver.findElement(By.xpath("//span[contains(.,'Electronics')]")).getText().trim();
		 
		if (tempText.matches("Electronics")) {
			System.out.println("Login Successful");

		} else {
			System.out.println("Login Failed");
		}

	}

}
