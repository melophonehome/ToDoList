package com.frames;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.log4j.Logger;

public class ToDoPage {

	Logger log = Logger.getLogger("ToDoPage");
	WebDriver driver;

	public ToDoPage(WebDriver driver) {
		this.driver = driver;
	}

	By Username = By.xpath("//input[@name = 'login']");

	By Password = By.xpath("//input[@name = 'password']");

	By LoginBtn = By.xpath("//input[@type = 'submit']");

	By ToDoListsLabel = By.xpath("//div[text()='Todo Lists']");

	By enterLists = By.xpath("//input[@ng-model = 'home.list']");

	By addListBtn = By.xpath("//button[contains(text(),'Add List')]");

	By SignOutBtn = By.xpath("//button[text() = 'Sign out']");

	By LoginPageHeader = By.xpath("//h1[@class ='login-head']");

	public void ToDoList_addList(String lists) throws Exception {
		log.info("adding lists");
		driver.findElement(enterLists).sendKeys(lists);
		Thread.sleep(2000);
		driver.findElement(addListBtn).click();
		Thread.sleep(2000);
		WebElement AddedList = driver.findElement(By.xpath("//a[text()='" + lists + " List']"));
		Assert.assertNotNull(AddedList, "List is not added successfully");
		log.info("Added successfully: " + lists + " List");
	}

	public void ToDoList_LogIn(String accountType, String username, String password) throws Exception {
		log.info("We're in To-Do list login page");
		String winHandleBefore = driver.getWindowHandle();
		Thread.sleep(2000);
		driver.manage().window().maximize();

		WebElement loginwithGitHubacc = driver.findElement(By.xpath("//a[contains(@ng-click,'" + accountType + "')]"));
		Thread.sleep(5000);
		log.info(">> Login using existing account");
		loginwithGitHubacc.click();
		log.info(">>>> Login with" + accountType + " account");
		Thread.sleep(15000);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		if (!driver.findElements(LoginBtn).isEmpty()) {
			log.info(">> No loggin session has been made");
			Thread.sleep(3000);
			log.info(">> Enter user name and password");
			driver.findElement(Username).sendKeys(username);
			log.info(">>>> Username: " + username);
			Thread.sleep(2000);
			driver.findElement(Password).sendKeys(password);
			log.info(">>>> Password: " + password);
			log.info(">> Verify if login is successful");
			Thread.sleep(3000);
			driver.findElement(LoginBtn).click();
			Thread.sleep(10000);
			driver.switchTo().window(winHandleBefore);
			Thread.sleep(5000);
			Assert.assertNotNull(driver.findElement(ToDoListsLabel), ">> Login is not successful");
			log.info(">> Login successfully");
		} else {
			log.info(">>>> User has already logged in");
		}

	}

	public void ToDoList_LogOut() throws Exception {
		log.info(">> Logging out of To Do List");
		Thread.sleep(1000);
		driver.findElement(SignOutBtn).click();
		Thread.sleep(5000);
		Assert.assertNotNull(LoginPageHeader, ">> Log out not successfully");
		log.info(">> Log out successfully");

	}

	public void ToDoList_deleteLists(String lists) throws Exception {
		log.info(">> Start deleteting lists");
		WebElement listName = driver.findElement(By.xpath("//a[contains(text(),'" + lists + "')]"));
		WebElement deleteButton = driver.findElement(By.xpath("//a[contains(text(),'" + lists
				+ "')]//parent::div/following-sibling::div/button[contains(@class,'remove')]"));
		Thread.sleep(1000);
		deleteButton.click();
		log.info(">> Verify list is deleted");
		Thread.sleep(2000);
		Assert.assertNull(listName, ">> List is not deleted successfully");
		log.info(">> List is deleted successfully");

	}

}
