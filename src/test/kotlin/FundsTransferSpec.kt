import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.slf4j.LoggerFactory

// a test class extending "BehaviorSpec" for BDD style testing
class FundsTransferSpec : BehaviorSpec({

    val logger = LoggerFactory.getLogger(FundsTransferSpec::class.java)

    // a setup that runs before the entire specification
    beforeSpec {
        RestAssured.baseURI = PropertiesUtil.getProperty("baseURI") ?: "defaultBaseUriIfNotSet"
        logger.info("Base URI set to: ${RestAssured.baseURI}")
    }

    //  test parameters for funds transfer tests as a "list of maps"
    val transferParameters = listOf(
        mapOf("fromAccountId" to 13455, "toAccountId" to 13566, "amount" to 100),
    )

    transferParameters.forEach { params ->
        Given("the base URI is set from properties file and parameters are set for fromAccountId: ${params["fromAccountId"]}, toAccountId: ${params["toAccountId"]}, amount: ${params["amount"]}") {
            logger.info("Base URI set to: ${RestAssured.baseURI}")

            // Extract  parameters to use in the request
            val fromAccountId = params["fromAccountId"]
            val toAccountId = params["toAccountId"]
            val amount = params["amount"]

            When("a POST request is sent to \"/transfer\" with query parameters") {
                val response = RestAssured.given()
                    .contentType(ContentType.URLENC) // Set content type as URL encoded
                    .queryParam("fromAccountId", fromAccountId) // Add fromAccountId as query parameter
                    .queryParam("toAccountId", toAccountId) // Add toAccountId as query parameter
                    .queryParam("amount", amount) // Add amount as query parameter
                    .log().all() // Log the entire request for debugging
                    .post("/transfer") // Send the request to the "/transfer" endpoint

                Then("status code 200 is received") {
                    logger.info("Received status code: ${response.statusCode}")
                    // Using the "shouldBe" assert of Kotest
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
