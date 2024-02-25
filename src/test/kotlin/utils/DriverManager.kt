package utils

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.slf4j.LoggerFactory

object DriverManager {
    private val logger = LoggerFactory.getLogger(DriverManager::class.java)
    private var driver: WebDriver? = null

    fun getDriver(): WebDriver {
        if (driver == null) {
            val browserType = PropertiesUtil.getProperty("browser.type")
            val headless = PropertiesUtil.getProperty("browser.headless").toBoolean()

            if (browserType != null) {
                driver = when (browserType?.uppercase()) {
                    "CHROME" -> {
                        System.setProperty("webdriver.chrome.driver", PropertiesUtil.getProperty("webdriver.chrome.driver"))
                        ChromeDriver(ChromeOptions().apply { if (headless) addArguments("--headless") })
                    }

                    "FIREFOX" -> {
                        System.setProperty("webdriver.gecko.driver", PropertiesUtil.getProperty("webdriver.gecko.driver"))
                        FirefoxDriver(FirefoxOptions().apply { if (headless) addArguments("--headless") })
                    }

                    else -> throw IllegalArgumentException("Unsupported browser type: $browserType")
                }.also { logger.info("$browserType browser started") }
            }
        }
        return driver!!
    }

    fun quitDriver() {
        driver?.quit()
        driver = null
    }
}
