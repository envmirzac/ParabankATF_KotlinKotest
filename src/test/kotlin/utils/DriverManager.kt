package utils

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.slf4j.LoggerFactory

// Define an object named DriverManager, ensuring it is a singleton
object DriverManager {
    private val logger = LoggerFactory.getLogger(DriverManager::class.java)
    // Declare a nullable WebDriver variable ("WebDriver?") to hold the browser driver instance.
    private var driver: WebDriver? = null

    // Define a function to return the current WebDriver instance, creating it if it doesn't exist.
    fun getDriver(): WebDriver {
        // Check if the driver is not already initialized.
        if (driver == null) {
            val browserType = PropertiesUtil.getProperty("browser.type")
            val headless = PropertiesUtil.getProperty("browser.headless").toBoolean()

            // Initialize the WebDriver based on the browser type.
            driver = when (browserType?.uppercase()) {
                "CHROME" -> {
                    System.setProperty("webdriver.chrome.driver", PropertiesUtil.getProperty("webdriver.chrome.driver") ?: "")
                    ChromeDriver(ChromeOptions().apply { if (headless) addArguments("--headless") })
                }
                "FIREFOX" -> {
                    System.setProperty("webdriver.gecko.driver", PropertiesUtil.getProperty("webdriver.gecko.driver") ?: "")
                    FirefoxDriver(FirefoxOptions().apply { if (headless) addArguments("--headless") })
                }
                // Throw an exception if the browser type is not supported.
                else -> throw IllegalArgumentException("Unsupported browser type: $browserType")
            }.also { logger.info("$browserType browser started") }
        }
        // Return the initialized WebDriver instance ("!!" means the driver is not null )
        return driver!!
    }

    fun quitDriver() {
        driver?.quit()
        driver = null
    }
}

