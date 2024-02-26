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
        get() = driver.findElement(By.name("username"))

    private val passwordField: WebElement
        get() = driver.findElement(By.name("password"))

    private val loginButton: WebElement
        get() = driver.findElement(By.xpath("//input[@value='Log In']"))

    fun navigateToLoginPage() {
        val baseUrl = PropertiesUtil.getProperty("baseUrl")
        driver.get(baseUrl ?: "")
    }

    // Define a function to enter a username into the username field.
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
