package com.scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.log4j.Logger;

import com.frames.ToDoPage;
import utils.ExcelUtils;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

public class ToDoList_test {

	String projectPath = System.getProperty("user.dir");
	String path = System.setProperty("webdriver.chrome.driver",
			projectPath + "\\src\\test\\java\\com\\drivers\\chromedriver.exe");
	WebDriver driver = new ChromeDriver();

	ToDoPage ToDoPageObj;

	ExcelUtils readExcelFileObj;

	Logger log = Logger.getLogger("ToDoList_test");

	@BeforeTest
	public void Setup() throws Exception {
		log.info(" Let's begin the test ");
		driver.get("https://todo-list-login.firebaseapp.com/");
		driver.manage().window().maximize();
		Thread.sleep(5000);
	}

	@AfterTest
	public void closeBrowser() {
		driver.close();
	}

	@Test(testName = "Add To Do List")
	public void add_ToDo_lists() throws Exception {
		
		log.info(" ---- Read data files ----");
		readExcelFileObj = new ExcelUtils(driver);
		String accountType = readExcelFileObj.getValuesfromDataFiles("testcasedata.xlsx", "ToDoLists", 1, 0);
		log.info("Account Type: " + accountType);
		String username = readExcelFileObj.getValuesfromDataFiles("testcasedata.xlsx", "ToDoLists", 1, 1);
		log.info("Username: " + username);
		String password = readExcelFileObj.getValuesfromDataFiles("testcasedata.xlsx", "ToDoLists", 1, 2);
		try {
			log.info(" ---- Read data files ----");
			readExcelFileObj = new ExcelUtils(driver);
			log.info("Password: " + password);
			ToDoPageObj = new ToDoPage(driver);
			log.info("This is the Test Project, it is automated following instruction below");
			log.info("--------- 1. Login using your github account -----------");
			ToDoPageObj.ToDoList_LogIn(accountType, username, password);
			Thread.sleep(3000);
			log.info("--------- 2. Create 10 to do list with random String -----------");
			log.info("-----> Generate a random string using UUID library");
			for (int i = 1; i <= 10; i++) {
				String RandomString = UUID.randomUUID().toString();
				ToDoPageObj.ToDoList_addList(RandomString);
				Thread.sleep(5000);
			}
			log.info("----------- 3. Upon completion log out. -----------");
			ToDoPageObj.ToDoList_LogOut();
			Thread.sleep(3000);
			log.info("----------- 4. Login again with the same account. -----------");
			ToDoPageObj.ToDoList_LogIn("Github", "thetai2911@gmail.com", "Password.2911");
			Thread.sleep(3000);
			log.info("----------- 5. Delete your list from 5 - 10. ----------- ");
			List<WebElement> getLists = driver.findElements(By.xpath("//li[contains(@class,'disney1 ng-scope')]"));
			log.info("-------> Total To Do Lists display:");
			for (int i = 1; i <= getLists.size(); i++) {
				String listName = driver.findElement(By.xpath("//li[contains(@class,'disney1 ng-scope')]" + "[" + i
						+ "]" + "//button/parent::div/preceding-sibling::div/a")).getText().toString();
				log.info(i + ". " + listName);
			}

			int j = 5;
			int count = 1;
			while (count <= 6) {
				WebElement deleteButton = driver.findElement(
						By.xpath("//li[contains(@class,'disney1 ng-scope')]" + "[" + j + "]" + "//button"));
				String listName = driver.findElement(By.xpath("//li[contains(@class,'disney1 ng-scope')]" + "[" + j
						+ "]" + "//button/parent::div/preceding-sibling::div/a")).getText().toString();
				Thread.sleep(2000);
				log.info(">> Delete list: " + listName);
				Thread.sleep(2000);
				deleteButton.click();
				Thread.sleep(2000);
				log.info(">> Verify if the list is deleted");
				Thread.sleep(5000);
				if (driver
						.findElements(By.xpath("//li[contains(@class,'disney1 ng-scope')]" + "[" + j + "]"
								+ "//button/parent::div/preceding-sibling::div/a[contains(text(),'" + listName + "')]"))
						.isEmpty()) {
					log.info(">>>> " + listName + " is deleted successfully");
				} else {
					log.info(">>>> " + listName + " is NOT deleted succesfully");
				}
				count++;
			}

			log.info("----------- 6. Logout upon completion -----------");
			Thread.sleep(3000);
			ToDoPageObj.ToDoList_LogOut();
			log.info("---------- END OF TEST CASE ----------");
		} finally {
			
			log.info(" >>>>>>>> Start test case clean up <<<<<<<<< ");
			log.info(">> Re-login to delete remaining lists");
			Thread.sleep(3000);
			ToDoPageObj.ToDoList_LogIn(accountType, username, password);
			Thread.sleep(3000);
			List<WebElement> remainingLists = driver
					.findElements(By.xpath("//li[contains(@class,'disney1 ng-scope')]"));
			for (int l = 1; l <= remainingLists.size(); l++) {
				String listName = driver.findElement(By.xpath("//li[contains(@class,'disney1 ng-scope')]" + "[" + l
						+ "]" + "//button/parent::div/preceding-sibling::div/a")).getText().toString();
				log.info(" Remaining lists: ");
				log.info(l + ". " + listName);
			}
			int k = 1;
			int count = 1;
			while (count <= 4) {
				Thread.sleep(2000);
				WebElement deleteButton = driver.findElement(By.xpath("//li[contains(@class,'disney1 ng-scope')]" + "["
						+ k + "]" + "//button[contains(@ng-click,'delete')]"));
				String listName = driver.findElement(By.xpath("//li[contains(@class,'disney1 ng-scope')]" + "[" + k
						+ "]" + "//button/parent::div/preceding-sibling::div/a")).getText().toString();
				Thread.sleep(1000);
				deleteButton.click();
				Thread.sleep(2000);
				log.info(">> Verify if the list is deleted");
				Thread.sleep(5000);
				if (driver
						.findElements(By.xpath("//li[contains(@class,'disney1 ng-scope')]" + "[" + k + "]"
								+ "//button/parent::div/preceding-sibling::div/a[contains(text(),'" + listName + "')]"))
						.isEmpty()) {
					log.info(">>>> " + listName + " is deleted successfully");
				} else {
					log.info(">>>> " + listName + " is NOT deleted succesfully");
				}
				count++;
			}
			log.info(" >>>>>>>> Finish cleaning up <<<<<<<<< ");
		}

	}
}
