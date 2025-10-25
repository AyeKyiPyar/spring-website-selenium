package com.akps.exapp.selenium;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminSeleniumTest
{

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    void setUp() 
    {
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // increased wait to handle loader
        driver.manage().window().maximize();
    }

    @Test
    void testAdminLoginSuccess() 
    {
        // 1️. Open login page
        driver.get("http://localhost:8080/admin");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myDiv")));
        
////        // 2. Fill login form
//        driver.findElement(By.id("email")).sendKeys("admin@gmail.com");
//        driver.findElement(By.id("password")).sendKeys("123");
//        driver.findElement(By.id("btnLogin")).click();

//        // 2️⃣ Wait until the login form is visible (loader disappears)
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("myDiv")));

        // 3️⃣ Wait until email input is clickable and enter credentials
        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        emailInput.sendKeys("admin@gmail.com");

        WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        passwordInput.sendKeys("123");

        // 4️⃣ Click login button
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnLogin")));
        loginBtn.click();

        // 5️⃣ //3. Wait until redirected to dashboard
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        // 6️⃣ //4. Assert on the dashboard
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/dashboard"),
                "Should redirect to admin dashboard");
    }


    
    @Test
    void testAddCategory() 
    {
        driver.get("http://localhost:8080/admin/category/add/form");

        // Wait for form and type category name
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
        nameInput.sendKeys("Flowers");
        
        // Wait for form and type category description
        WebElement descriptionInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("description")));
        descriptionInput.sendKeys("This is Flowers");

        WebElement addBtn = driver.findElement(By.id("btnAdd"));
        addBtn.click();

        // Verify redirect to category list page
        wait.until(ExpectedConditions.urlContains("/admin/category"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/admin/category"));
    }

    @Test
    void testEditCategory() 
    {
        driver.get("http://localhost:8080/admin/category");

        // Click first edit link
//        WebElement editLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".editBtn")));
        WebElement editLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("editBtn")));
        editLink.click();

        // Wait for form and update name
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
        nameInput.clear();
        nameInput.sendKeys("Updated Flowers");

        WebElement updateBtn = driver.findElement(By.id("btnUpdate"));
        updateBtn.click();

        wait.until(ExpectedConditions.urlContains("/admin/category"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/admin/category"));
    }

    @Test
    void testDeleteCategory() 
    {
        driver.get("http://localhost:8080/admin/category");

        // Click first delete link
        WebElement deleteLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteBtn")));
        deleteLink.click();
   
        // ✅ Handle JavaScript confirm() popup
        try 
        {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept(); // Clicks "OK"
        } 
        catch (TimeoutException e)
        {
            System.out.println("No confirmation alert appeared.");
        }

        wait.until(ExpectedConditions.urlContains("/admin/category"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/admin/category"));
    }

    @Test
    public void testAddProduct() throws InterruptedException 
    {
        // Navigate to the product form page
        driver.get("http://localhost:8080/admin/product/add/form"); // Replace with your actual URL

        // Fill Name
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        nameField.sendKeys("Test Product");

        // Fill Description
        WebElement descriptionField = driver.findElement(By.id("description"));
        descriptionField.sendKeys("This is a test product");

        // Fill Quantity
        WebElement quantityField = driver.findElement(By.id("quantity"));
        quantityField.sendKeys("10");

        // Fill Price
        WebElement priceField = driver.findElement(By.id("price"));
        priceField.sendKeys("99");

        // Upload Image
        WebElement fileField = driver.findElement(By.id("file"));
        fileField.sendKeys("C:\\Users\\Linux Lab\\Pictures\\dse1.png"); // Replace with your local image path

        // Select Category
        WebElement categoryDropdown = driver.findElement(By.id("category"));
        Select selectCategory = new Select(categoryDropdown);
        selectCategory.selectByIndex(0); // Select first category, or use selectByValue("id")

        // Submit
        WebElement submitBtn = driver.findElement(By.id("btnSubmit"));
        submitBtn.click();

        // Wait for redirect or success message
        wait.until(ExpectedConditions.urlContains("/admin/product")); // Replace with your redirect page

        // Verify product added (example: check success alert)
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
        Assertions.assertTrue(successMessage.getText().contains("Product added successfully"));
    }
    
    @Test
	public void testLoginCustomer() 
	{
	   
		driver.get("http://localhost:8080/customer/login/form"); // URL of your frontend
		
        // Fill login form
        driver.findElement(By.id("email")).sendKeys("alice@example.com");
        driver.findElement(By.id("password")).sendKeys("pass123");
        driver.findElement(By.id("btnSubmit")).click();

        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	   
     // Wait for redirect or success message
        wait.until(ExpectedConditions.urlContains("/customer/index")); // Replace with your redirect page
	   
	     // Wait for product cards to exist in DOM
	     List<WebElement> productCards = wait.until(
	         ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("product-card"))
	     );

	     // Assert that at least one card exists
	     Assertions.assertFalse(productCards.isEmpty(), "No product cards found!");
	     System.out.println("Found " + productCards.size() + " product cards.");

	}
    @AfterAll
    void tearDown() 
    {
        if (driver != null) 
        {
            driver.quit();
        }
    }
}
