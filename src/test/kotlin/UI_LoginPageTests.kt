package tests

import io.kotest.core.spec.style.BehaviorSpec
import org.openqa.selenium.WebDriver
import org.slf4j.LoggerFactory
import pages.LoginPage
import utils.DriverManager
import utils.PropertiesUtil

class UI_LoginPageTests : BehaviorSpec({

    val logger = LoggerFactory.getLogger(UI_LoginPageTests::class.java)

    // Getting the WebDriver instance from the DriverManager class
    val driver: WebDriver = DriverManager.getDriver()

    // Creating an instance of the LoginPage using the driver
    val loginPage = LoginPage(driver)

    given("the Parabank login page is displayed") {
        logger.info("Navigating to the Parabank login page")

        loginPage.navigateToLoginPage()

        logger.info("Parabank login page is displayed")

        `when`("valid credentials are entered") {
            // Retrieving the username/password from properties, or throwing an exception if not found.
            val username = PropertiesUtil.getProperty("username") ?: throw IllegalStateException("Username not found")
            val password = PropertiesUtil.getProperty("password") ?: throw IllegalStateException("Password not found")

            logger.info("Entering username and password")

            loginPage.enterUsername(username)
            loginPage.enterPassword(password)
            logger.info("Credentials entered")

            then("the login button is clicked") {
                logger.info("Clicking the login button")
                loginPage.clickLoginButton()
                logger.info("Login button clicked and Overview page should now be displayed")
            }
        }
    }

    // afterSpec  is used to define cleanup actions after all tests in the spec have been run (in this case, quit the WebDriver)
    afterSpec {
        logger.info("Quitting the WebDriver")
        DriverManager.quitDriver()
    }
})
