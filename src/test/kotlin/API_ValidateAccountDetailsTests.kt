import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.path.xml.XmlPath
import org.slf4j.LoggerFactory
import utils.PropertiesUtil

class API_ValidateAccountDetailsTests : BehaviorSpec({

    val logger = LoggerFactory.getLogger(API_ValidateAccountDetailsTests::class.java)

    // beforeSpec is run before any tests are executed
    beforeSpec {
        RestAssured.baseURI = PropertiesUtil.getProperty("baseURI") ?: "defaultBaseUriIfNotSet"
        logger.info("Base URI set to: ${RestAssured.baseURI}")
    }

    given("the base URI is set from properties file") {
        `when`("a GET request is sent to \"/accounts/13566\"") {
            val response = RestAssured.get("/accounts/13566")

            then("status code 200 is received") {
                logger.info("Received status code: ${response.statusCode}")
                // Assert that the received status code is 200 using Kotest's "shouldBe" assertion method
                response.statusCode shouldBe 200
            }

            then("the account details are displayed in the response body") {
                // Convert the response body to a string for logging
                val responseBody = response.body.asString()
                logger.info("Response body: $responseBody")

                // Convert the string response body to an XmlPath for XML parsing
                val xmlPath = XmlPath.from(responseBody)

                // Assert that the 'id', 'customerId' and 'type' elements in the XML response match the expected values
                xmlPath.getString("**.find { it.name() == 'id' }") shouldBe "13566"
                xmlPath.getString("**.find { it.name() == 'customerId' }") shouldBe "12434"
                xmlPath.getString("**.find { it.name() == 'type' }") shouldBe "CHECKING"

                logger.info("Account details validated successfully.")
            }
        }
    }
})
