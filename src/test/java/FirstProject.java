import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstProject {

    @Test
    public void test1() throws InterruptedException {

        // 1. Navigate to http://duotify.us-east-2.elasticbeanstalk.com/register.php
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        Thread.sleep(1000);
        driver.get("http://duotify.us-east-2.elasticbeanstalk.com/register.php");

        // 2. Verify the title is "Welcome to Duotify!"

        Assert.assertEquals(driver.getTitle(),"Welcome to Duotify!", "The title is wrong");

        // 3. Click on Signup here

        driver.findElement(By.id("hideLogin")).click();

        // 4. Fill out the form with the required info using Faker class

        Faker faker = new Faker();

        String fakeUserName = faker.name().username();
        String fakeFirstName = faker.address().firstName();
        String fakeLastName = faker.address().lastName();
        String fakeEmail = faker.internet().emailAddress();
        String fakePassword = faker.internet().password();

        driver.findElement(By.id("username")).sendKeys(fakeUserName);
        driver.findElement(By.id("firstName")).sendKeys(fakeFirstName);
        driver.findElement(By.id("lastName")).sendKeys(fakeLastName);
        driver.findElement(By.id("email")).sendKeys(fakeEmail);
        driver.findElement(By.id("email2")).sendKeys(fakeEmail);
        driver.findElement(By.id("password")).sendKeys(fakePassword);
        driver.findElement(By.id("password2")).sendKeys(fakePassword);

        //5. Click on Sign up

        driver.findElement(By.name("registerButton")).click();

        // 6. Once logged in to the application, verify that the URL is:
        // http://duotify.us-east-2.elasticbeanstalk.com/browse.php?

        String expectedUrl = "http://duotify.us-east-2.elasticbeanstalk.com/browse.php?";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "URL is wrong");

        // 7. In the left navigation bar, verify that your first and last name is the same the first and
        // last name that you used when signing up. (use getText() method to extract the text of the element)

        Assert.assertEquals(driver.findElement(By.id("nameFirstAndlast")).getText(), fakeFirstName + " " + fakeLastName);

        // 8. Click on the first and last name on the left navigation bar and verify the first and
        // last name on the main window is correct and then click logout.

        driver.findElement(By.id("nameFirstAndlast")).click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(), fakeFirstName + " " + fakeLastName, "Name verification failed");
        driver.findElement(By.id("rafael")).click();

        // 9. Verify that you are logged out by verifying the URL is:
        // http://duotify.us-east-2.elasticbeanstalk.com/register.php

        Thread.sleep(1000);
        Assert.assertEquals(driver.getCurrentUrl(), "http://duotify.us-east-2.elasticbeanstalk.com/register.php", "URL verification failed");

        // 10. Login using the same username and password when you signed up.

        Thread.sleep(1000);
        driver.findElement(By.id("loginUsername")).sendKeys(fakeUserName);
        driver.findElement(By.id("loginPassword")).sendKeys(fakePassword);
        driver.findElement(By.name("loginButton")).click();

        // 11. Verify successful login by verifying that the home page contains the text "You Might Also Like".

        Thread.sleep(1000);
        Assert.assertTrue(driver.getPageSource().contains("You Might Also Like"));

        // 12.  Log out once again and verify that you are logged out.

        Thread.sleep(1000);
        driver.findElement(By.id("nameFirstAndLast")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("rafael")).click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.getCurrentUrl(),"http://duotify.us-east-2.elasticbeanstalk.com/register.php","Wrong link");

    }

}
