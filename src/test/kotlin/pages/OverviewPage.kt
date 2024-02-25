package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class OverviewPage(private val driver: WebDriver) {
    private val wait = WebDriverWait(driver, 30L) // Initializes WebDriverWait with a 30-second timeout

    // Using delegated properties to initialize WebElements upon first access
    @FindBy(xpath = "//h1[contains(text(), 'Accounts Overview')]")
    private lateinit var accountsOverviewHeader: WebElement

    @FindBy(xpath = "//a[@href='/parabank/overview.htm' and text()='Accounts Overview']")
    private lateinit var accountsOverviewButton: WebElement

    init {
        PageFactory.initElements(driver, this) // Initializes fields annotated with @FindBy
    }

    fun isAtAccountsOverview(): Boolean {
        wait.until(ExpectedConditions.visibilityOf(accountsOverviewHeader))
        return accountsOverviewHeader.isDisplayed // Checks if the accounts overview header is displayed
    }

    fun isAccountVisible(accountNumber: String): Boolean {
        val xpath = String.format("//a[contains(text(), '%s')]", accountNumber)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
        return true // Assumes visibility means the account is visible; might require further validation
    }

    fun submitAccountOverviewButton() {
        wait.until(ExpectedConditions.elementToBeClickable(accountsOverviewButton)).click() // Navigates to the accounts overview
    }
}
