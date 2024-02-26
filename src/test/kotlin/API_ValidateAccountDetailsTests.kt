import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.path.xml.XmlPath
import org.slf4j.LoggerFactory
import utils.PropertiesUtil

class API_ValidateAccountDetailsTests : BehaviorSpec({

    val logger = LoggerFactory.getLogger(API_ValidateAccountDetailsTests::class.java)

    beforeSpec {
        RestAssured.baseURI = PropertiesUtil.getProperty("baseURI") ?: "defaultBaseUriIfNotSet"
        logger.info("Base URI set to: ${RestAssured.baseURI}")
    }

    given("the base URI is set from properties file") {
        `when`("a GET request is sent to \"/accounts/13566\"") {
            val response = RestAssured.get("/accounts/13566")

            then("status code 200 is received") {
                logger.info("Received status code: ${response.statusCode}")
                response.statusCode shouldBe 200
            }

            then("the account details are displayed in the response body") {
                val responseBody = response.body.asString()
                logger.info("Response body: $responseBody")

                // Convert response body to XmlPath for easy access
                val xmlPath = XmlPath.from(responseBody)

                // Verify each detail in the response
                xmlPath.getString("**.find { it.name() == 'id' }") shouldBe "13566"
                xmlPath.getString("**.find { it.name() == 'customerId' }") shouldBe "12434"
                xmlPath.getString("**.find { it.name() == 'type' }") shouldBe "CHECKING"

                logger.info("Account details validated successfully.")
            }
        }
    }
})
