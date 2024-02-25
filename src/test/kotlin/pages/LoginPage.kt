package pages

import utils.PropertiesUtil
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class LoginPage(private val driver: WebDriver) {
    private val wait: WebDriverWait = WebDriverWait(driver, 30L)

    private val usernameField: WebElement
        get() = driver.findElement(By.name("username")) // Fixed

    private val passwordField: WebElement
        get() = driver.findElement(By.name("password")) // Fixed

    private val loginButton: WebElement
        get() = driver.findElement(By.xpath("//input[@value='Log In']")) // Fixed

    fun navigateToLoginPage() {
        val baseUrl = PropertiesUtil.getProperty("baseUrl")
        driver.get(baseUrl ?: "") // Handling potential null
    }

    fun enterUsername(username: String) {
        wait.until(ExpectedConditions.elementToBeClickable(usernameField))
        usernameField.clear()
        usernameField.sendKeys(username)
    }

    fun enterPassword(password: String) {
        wait.until(ExpectedConditions.elementToBeClickable(passwordField))
        passwordField.clear()
        passwordField.sendKeys(password)
    }

    fun clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click()
    }
}
