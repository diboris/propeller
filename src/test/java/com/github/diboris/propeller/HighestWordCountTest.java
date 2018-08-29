package com.github.diboris.propeller;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class HighestWordCountTest {

    private static final boolean CLOSE_BROWSER_AFTER_TEST = true;

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public static void setupClass() {
        // Configure nice logging in console
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        // Auto-install chrome driver
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @After
    public void tearDown() {
        if (driver != null && CLOSE_BROWSER_AFTER_TEST) {
            driver.quit();
        }
    }

    @Test
    public void should_output_highest_word_count_blog_title() {
        driver.get("https://www.propelleraero.com/");
        driver.findElement(By.xpath("//a[text()='Blog']")).click();
        Integer size = driver.findElements(By.xpath("//a[@class='post-bg']")).size();
        Integer highestWordCount = 0;
        String highestBlogTitle = "";
        Integer number = 1;
        while (number < size + 1) {
            driver.findElement(By.xpath("(//a[@class='post-bg'])[" + number + "]")).click();
            String text = driver.findElement(By.xpath("//div[@class='entry-content']")).getText();
            String blogTitle = driver.findElement(By.xpath("//h1[@class='entry-title']")).getText();
            Integer wordCount = StringUtils.countMatches(text, " ");
            if (wordCount > highestWordCount) {
                highestWordCount = wordCount;
                highestBlogTitle = blogTitle;
            }
            driver.navigate().back();
            number = number + 1;
        }
        System.out.println("Highest Blog Title: " + highestBlogTitle);
    }
}
