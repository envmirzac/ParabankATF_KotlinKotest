package tests

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import model.User
import dao.UserDAO
import utils.SessionManager
import java.util.*

class DB_UserDatabaseOperationsTests : BehaviorSpec({

    val userDao = UserDAO

    beforeSpec {
        SessionManager.openSession()
    }

    given("a new user with a random username") {
        val username = "VictorMirzac" + Random().nextInt(9000 + 100)
        val password = "password" // Replace with actual password retrieval logic if necessary
        val user = User(username = username, password = password)

        `when`("the user is persisted in the database") {
            userDao.saveUser(user)

            then("the record should exist in the users table with that random username") {
                val retrievedUser = userDao.getUserByUsername(username)
                retrievedUser shouldNotBe null
                retrievedUser?.username shouldBe username
            }
        }
    }

    afterSpec {
        SessionManager.closeSession()
    }
})
