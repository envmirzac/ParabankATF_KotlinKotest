package tests

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.openqa.selenium.WebDriver
import org.slf4j.LoggerFactory
import pages.LoginPage
import pages.OpenNewAccountPage
import pages.OverviewPage
import utils.DriverManager
import utils.PropertiesUtil

class UI_OpenNewAccountTests : BehaviorSpec({
    val logger = LoggerFactory.getLogger(UI_OpenNewAccountTests::class.java)
    val driver: WebDriver = DriverManager.getDriver()
    val loginPage = LoginPage(driver)
    val openNewAccountPage = OpenNewAccountPage(driver)
    val overviewPage = OverviewPage(driver)

    beforeSpec {
        logger.info("Logging in and navigating to the Open New Account page")
        loginPage.navigateToLoginPage()
        val username = PropertiesUtil.getProperty("username") ?: throw IllegalStateException("Username not found")
        val password = PropertiesUtil.getProperty("password") ?: throw IllegalStateException("Password not found")
        loginPage.enterUsername(username)
        loginPage.enterPassword(password)
        loginPage.clickLoginButton()
    }

    // "afterEach" is used to execute code after each test
    afterEach { (_, _) ->
        // Navigates back to the open new account page after each test
        openNewAccountPage.navigateToOpenNewAccountPage()
        logger.info("Navigated back to the Open New Account page after the test")
    }

    afterSpec {
        logger.info("Quitting the WebDriver")
        DriverManager.quitDriver()
    }

    given("User is on the Open New Account page") {
        openNewAccountPage.navigateToOpenNewAccountPage()
        logger.info("Navigated to Parabank open new account page.")

        // A list of "account types" and "source accounts" used for  testing
        listOf(
            "CHECKING" to "13566",
            "SAVINGS" to "13677",
            "CHECKING" to "13788",
            "SAVINGS" to "14232"
        ).forEach { (accountType, fromAccount) ->
            `when`("the $accountType account type is selected and the source account $fromAccount is chosen") {
                openNewAccountPage.selectAccountType(accountType)
                openNewAccountPage.selectFromAccount(fromAccount)
                openNewAccountPage.submitNewAccountButton()

                then("the new account number is displayed and a confirmation message is shown") {
                    val confirmationMessage = openNewAccountPage.getConfirmationMessage()
                    confirmationMessage shouldBe "Congratulations, your account is now open."
                    val newAccountNumber = openNewAccountPage.getNewAccountNumber()
                    newAccountNumber shouldNotBe null

                    overviewPage.submitAccountOverviewButton()
                    overviewPage.isAtAccountsOverview() shouldBe true
                    overviewPage.isAccountVisible(newAccountNumber) shouldBe true
                }
            }
        }
    }
})
