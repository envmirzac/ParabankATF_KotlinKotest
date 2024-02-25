package tests

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.slf4j.LoggerFactory
import pages.OverviewPage
import utils.DriverManager

class UI_OverviewTests : BehaviorSpec({
    // Initialize logger, web driver, and pages
    val logger = LoggerFactory.getLogger(UI_OverviewTests::class.java)
    val driver = DriverManager.getDriver()
    val overviewPage = OverviewPage(driver)

    // Assuming a scenario context where the new account number is stored
    val newAccountNumber = "12345" // Replace with dynamic retrieval from context

    given("the user is on the Account Overview page") {
        // You might want to include steps to ensure the user is logged in and navigated to the overview page

        `when`("the account $newAccountNumber is expected to be visible") {
            then("it should be displayed on the Account Overview page") {
                val isVisible = overviewPage.isAccountVisible(newAccountNumber)
                logger.takeIf { isVisible }?.info("Account number $newAccountNumber is visible on the Account Overview page")
                logger.takeUnless { isVisible }?.error("Account number $newAccountNumber is NOT visible on the Account Overview page")

                // Assert that the account number is visible
                isVisible shouldBe true
            }
        }

        `when`("navigation to the account overview is initiated") {
            overviewPage.submitAccountOverviewButton()
            then("the Overview page should be displayed") {
                val isAtOverview = overviewPage.isAtAccountsOverview()
                logger.info("Successfully navigated to the Overview page")
                isAtOverview shouldBe true
            }
        }
    }

    afterSpec {
        // Clean up and close the WebDriver
        logger.info("Closing the WebDriver")
        DriverManager.quitDriver()
    }
})
