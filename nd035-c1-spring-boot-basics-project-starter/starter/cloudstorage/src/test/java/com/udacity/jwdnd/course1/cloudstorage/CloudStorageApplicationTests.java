package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


import java.io.File;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void unAuthorizedLoginTest() {

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

	}

	@Test
	@Order(2)
	public void signUpTest() throws InterruptedException {

		driver.get("http://localhost:" + this.port + "/signup");
		driver.findElement(By.id("inputFirstName")).sendKeys("hanan");
		driver.findElement(By.id("inputLastName")).sendKeys("mohammed");
		driver.findElement(By.id("inputUsername")).sendKeys("hanan1");
		driver.findElement(By.id("inputPassword")).sendKeys("123");
		Thread.sleep(4000);
		driver.findElement(By.id("buttonSignUp")).click();

		loginTest();
		Assertions.assertEquals("Home", driver.getTitle());

		driver.findElement(By.id("buttonLogOut")).click();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

	}

	@Test
	@Order(3)
	public void loginTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		driver.findElement(By.id("inputUsername")).sendKeys("hanan1");
		driver.findElement(By.id("inputPassword")).sendKeys("123");
		driver.findElement(By.id("login-button")).click();
		Thread.sleep(4000);
		Assertions.assertEquals("Home", driver.getTitle());

	}

	@Test
	@Order(5)
	public void notesCRUDTest() throws InterruptedException {
		loginTest();

		// create new note
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("buttonAddNewNote")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("note-title")).sendKeys("note test");
		driver.findElement(By.id("note-description")).sendKeys("- this is a note test");
		driver.findElement(By.id("buttonNoteSubmit")).click();
		Thread.sleep(2000);
		Assertions.assertEquals("Result", driver.getTitle());
		driver.findElement(By.id("buttonHomePage")).click();
		Assertions.assertEquals("Home", driver.getTitle());

		// edit note
		Thread.sleep(2000);
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000);

		driver.findElement(By.id("buttonEditNote")).click();

		Thread.sleep(2000);
		driver.findElement(By.id("note-title")).sendKeys(" edit note");
		driver.findElement(By.id("note-description")).sendKeys("  - this is edit test");
		driver.findElement(By.id("buttonNoteSubmit")).click();
		Thread.sleep(2000);
		Assertions.assertEquals("Result", driver.getTitle());
		driver.findElement(By.id("buttonHomePage")).click();
		Assertions.assertEquals("Home", driver.getTitle());


		// delete note
		Thread.sleep(2000);
		driver.findElement(By.id("nav-notes-tab")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("deleteNote")).click();
		Assertions.assertEquals("Result", driver.getTitle());

	}

	@Test
	@Order(6)
	public void createCredentialTest() throws InterruptedException {
		loginTest();

		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("buttonNewCredential")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("credential-url")).sendKeys("/home");
		driver.findElement(By.id("credential-username")).sendKeys("hanan1");
		driver.findElement(By.id("credential-password")).sendKeys("password");
		driver.findElement(By.id("buttonCredentialSubmit")).click();
		Thread.sleep(2000);
		Assertions.assertEquals("Result", driver.getTitle());
	}

	@Test
	@Order(7)
	public void updateCredentialTest() throws InterruptedException {
		loginTest();

		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("buttonEditC")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("credential-url")).sendKeys("/test");
		driver.findElement(By.id("credential-username")).sendKeys(" edit");
		driver.findElement(By.id("credential-password")).sendKeys(" edit");
		driver.findElement(By.id("buttonCredentialSubmit")).click();
		Thread.sleep(2000);
		Assertions.assertEquals("Result", driver.getTitle());
	}

	@Test
	@Order(8)
	public void deleteCredentialTest() throws InterruptedException {
		loginTest();

		driver.findElement(By.id("nav-credentials-tab")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("deleteCredential")).click();
		Assertions.assertEquals("Result", driver.getTitle());
	}


	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
		driver.findElement(By.id("login-link")).click();
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	@Order(4)
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
