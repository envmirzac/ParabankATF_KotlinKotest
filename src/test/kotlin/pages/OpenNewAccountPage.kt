package pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait

class OpenNewAccountPage(private val driver: WebDriver) {
    private val wait = WebDriverWait(driver, 30L) // Initializes WebDriverWait with a 30-second timeout

    // Using delegated properties to initialize WebElements upon first access
    @FindBy(xpath = "//select[starts-with(@class, 'input')]")
    private lateinit var accountTypeDropdown: WebElement

    @FindBy(xpath = "//select[@id='fromAccountId']")
    private lateinit var fromAccountDropdown: WebElement

    @FindBy(xpath = "//a[@href='/parabank/openaccount.htm' and text()='Open New Account']")
    private lateinit var openNewAccountLink: WebElement

    @FindBy(xpath = "//input[@type='submit']")
    private lateinit var openNewAccountButton: WebElement

    @FindBy(xpath = "//p[text()='Congratulations, your account is now open.']")
    private lateinit var confirmationMessage: WebElement

    @FindBy(xpath = "//a[@id='newAccountId']")
    private lateinit var newAccountNumber: WebElement

    init {
        PageFactory.initElements(driver, this) // Initializes fields annotated with @FindBy
    }

    fun selectAccountType(accountType: String) {
        wait.until(ExpectedConditions.visibilityOf(accountTypeDropdown))
        Select(accountTypeDropdown).selectByVisibleText(accountType) // Selects the account type from the dropdown
    }

    fun selectFromAccount(accountNumber: String) {
        wait.until(ExpectedConditions.visibilityOf(fromAccountDropdown))
        Select(fromAccountDropdown).selectByVisibleText(accountNumber) // Selects the from account from the dropdown
    }

    fun submitNewAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(openNewAccountButton)).click() // Clicks the open new account button
    }

    fun getConfirmationMessage(): String {
        return wait.until(ExpectedConditions.visibilityOf(confirmationMessage)).text // Returns the text of the confirmation message
    }

    fun navigateToOpenNewAccountPage() {
        wait.until(ExpectedConditions.elementToBeClickable(openNewAccountLink)).click() // Navigates to the open new account page
    }

    fun getNewAccountNumber(): String {
        return wait.until(ExpectedConditions.visibilityOf(newAccountNumber)).text // Returns the text of the new account number
    }

    fun isOnOpenNewAccountPage(): Boolean {
        return wait.until(ExpectedConditions.visibilityOf(openNewAccountLink)).isDisplayed // Returns true if on the open new account
    }
}
