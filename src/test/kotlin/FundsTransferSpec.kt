import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.slf4j.LoggerFactory

class FundsTransferSpec : BehaviorSpec({

    val logger = LoggerFactory.getLogger(FundsTransferSpec::class.java)

    beforeSpec {
        RestAssured.baseURI = PropertiesUtil.getProperty("baseURI") ?: "defaultBaseUriIfNotSet"
        logger.info("Base URI set to: ${RestAssured.baseURI}")
    }

    //  a list of maps representing the test parameters
    val transferParameters = listOf(
        mapOf("fromAccountId" to 13455, "toAccountId" to 13566, "amount" to 100),
    )

    // Iterate over each set of parameters
    transferParameters.forEach { params ->
        Given("the base URI is set from properties file and parameters are set for fromAccountId: ${params["fromAccountId"]}, toAccountId: ${params["toAccountId"]}, amount: ${params["amount"]}") {
            logger.info("Base URI set to: ${RestAssured.baseURI}")

            val fromAccountId = params["fromAccountId"]
            val toAccountId = params["toAccountId"]
            val amount = params["amount"]

            When("a POST request is sent to \"/transfer\" with query parameters") {
                val response = RestAssured.given()
                    .contentType(ContentType.URLENC)
                    .queryParam("fromAccountId", fromAccountId)
                    .queryParam("toAccountId", toAccountId)
                    .queryParam("amount", amount)
                    .log().all()
                    .post("/transfer")

                Then("status code 200 is received") {
                    logger.info("Received status code: ${response.statusCode}")
                    response.statusCode shouldBe 200
                }

                And("the message \"Successfully transferred \$$amount from account #$fromAccountId to account #$toAccountId\" is displayed") {
                    val responseBody = response.body.asString()
                    logger.info("Response body: $responseBody")
                    responseBody shouldBe "Successfully transferred \$$amount from account #$fromAccountId to account #$toAccountId"
                }
            }
        }
    }
})
