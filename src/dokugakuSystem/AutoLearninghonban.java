package dokugakuSystem;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

public class AutoLearninghonban {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jim\\Downloads\\chromedriver-win64\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://plus.dokuzemi.com/login");

		// 定位並填充用户名和密码字段
		WebElement loginIdField = driver.findElement(By.name("login_id"));
		System.out.println("Start to input ID");
		Thread.sleep(1000);

		loginIdField.sendKeys("xxxx@xxxx");

		WebElement passwordField = driver.findElement(By.name("password"));
		System.out.println("Start to input PASSWORD");
		Thread.sleep(1000);

		passwordField.sendKeys("xxxxxxxx");

		// 单击“登录”按钮
		WebElement loginButton = driver.findElement(By.id("login_button"));
		System.out.println("Start to LOGIN");
		Thread.sleep(1000);
		loginButton.click();
		Thread.sleep(2000);
		driver.get("https://plus.xxxxx.com/course/time/xxxxx");
		Thread.sleep(2000);

		while (true) {
			try {

				List<WebElement> learnButtons = findButtonsByText(driver, "学習開始");
				if (!learnButtons.isEmpty()) {
					System.out.println("Total counts of buttons are " + learnButtons.size());
					Thread.sleep(2000);
					// 點擊第一個找到的學習開始按鈕
					System.out.println("Start to click");
					Thread.sleep(3000);
					learnButtons.get(0).click();

					Thread.sleep(3900000);

					// 刷新網頁
					System.out.println("Start to refresh");
					Thread.sleep(2000);
					driver.navigate().refresh();
					Thread.sleep(3000);
				} else {
					System.out.println("No need to learn");
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static List<WebElement> findButtonsByText(WebDriver driver, String buttonText) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		List<WebElement> buttons = (List<WebElement>) js.executeScript(
				"return Array.from(document.querySelectorAll('button')).filter(button => button.textContent.trim() === arguments[0]);",
				buttonText);
		return buttons;
	}

}
