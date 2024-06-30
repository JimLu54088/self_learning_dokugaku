package dokugakuSystem;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class AutomaticChangeDGPassword {

	public static void main(String[] args) throws Exception {
		// 設定 ChromeDriver 的路徑
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Jim\\Downloads\\chromedriver-win64\\chromedriver.exe");

		String destFileName = "D:\\test_files\\20240629Warexcecute\\IDsheet.xlsx";

		// 讀取 Excel 文件

		FileInputStream file = new FileInputStream(new File(destFileName));
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheet("Sheet1");

		for (int i = 5; i <= 9; i++) { // 從第6行到第10行
			Row row = sheet.getRow(i);
			Row rowNewPassword = sheet.getRow(5);

			String username = getCellValueAsString(row.getCell(2)); // C列
			String currentPassword = getCellValueAsString(row.getCell(3)); // D列
			String newPassword = getCellValueAsString(rowNewPassword.getCell(5)); // F列

			// 創建 WebDriver 實例
			WebDriver driver = new ChromeDriver();

			try {
				// 步驟1：進入首頁並選擇第二個選項
				driver.get("http://localhost:8081/DGMain.html");
				Thread.sleep(1000);
				WebElement dropdown = driver.findElement(By.id("redirectDropdown"));
				Select select = new Select(dropdown);

				// 選擇第二個選項（index 從 0 開始）
				select.selectByIndex(1);
				WebElement button = driver.findElement(By.id("DGMainSeawrch"));
				button.click();

				// 步驟2：點擊 "Reset password" 連結
				WebElement link = driver.findElement(By.linkText("Reset password"));
				link.click();

				WebElement usernameField = driver.findElement(By.id("username"));
				WebElement currentPasswordField = driver.findElement(By.id("password"));
				WebElement newPasswordField = driver.findElement(By.id("newpassword"));
				WebElement confirmPasswordField = driver.findElement(By.id("reEntereNewdpassword"));

				usernameField.sendKeys(username);
				currentPasswordField.sendKeys(currentPassword);
				newPasswordField.sendKeys(newPassword);
				confirmPasswordField.sendKeys(newPassword);

				WebElement submitButton = driver.findElement(By.id("submit"));
				submitButton.click();
				Thread.sleep(1000);

				// 步驟4：關閉瀏覽器
				driver.quit();

				Thread.sleep(200);

				// 步驟5：重新打開瀏覽器並驗證登入
				driver = new ChromeDriver();
				driver.get("http://localhost:8081/DGlogin.html");
				Thread.sleep(1000);
				WebElement loginUsernameField = driver.findElement(By.id("username"));
				WebElement loginPasswordField = driver.findElement(By.id("password"));

				loginUsernameField.sendKeys(username);
				loginPasswordField.sendKeys(newPassword);

				WebElement loginButton = driver.findElement(By.id("submit"));
				loginButton.click();
				Thread.sleep(1000);

				// 這裡可以添加額外的驗證邏輯來確認是否成功登入

			} finally {
				driver.quit();
			}
		}

		workbook.close();
		file.close();
	}

	// 這個方法確保無論單元格類型是什麼，都會轉換為字符串
	private static String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellTypeEnum()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString();
			} else {
				return String.valueOf(cell.getNumericCellValue());
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		case BLANK:
			return "";
		default:
			return cell.toString();
		}
	}
}
