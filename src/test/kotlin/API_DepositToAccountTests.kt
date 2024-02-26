import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.slf4j.LoggerFactory
import utils.PropertiesUtil

class API_DepositToAccountTests : BehaviorSpec({

    val logger = LoggerFactory.getLogger(API_DepositToAccountTests::class.java)

    beforeSpec {
        RestAssured.baseURI = PropertiesUtil.getProperty("baseURI") ?: "defaultBaseUriIfNotSet"
        logger.info("Base URI set to: ${RestAssured.baseURI}")
    }

    given("the base URI is set from properties file") {
        val accountId = 13566
        val amount = 100

        `when`("a POST request is sent to \"/deposit\" with account parameters") {
            val response = RestAssured.given()
                .contentType(ContentType.URLENC)
                .queryParam("accountId", accountId)
                .queryParam("amount", amount)
                .log().all()
                .post("/deposit")

            then("status code 200 is received") {
                logger.info("Received status code: ${response.statusCode}")
                response.statusCode shouldBe 200
            }

            then("the message \"Successfully deposited \"$amount\" to account \"#$accountId\"\" is displayed") {
                val expectedMessage = "Successfully deposited \$$amount to account #$accountId"
                val actualMessage = response.body.asString()
                logger.info("Response body: $actualMessage")
                actualMessage shouldBe expectedMessage
            }
        }
    }
})
