package dao

import model.User
import org.hibernate.Session
import org.hibernate.Transaction
import org.slf4j.LoggerFactory
import utils.SessionManager

object UserDAO {

    private val logger = LoggerFactory.getLogger(UserDAO::class.java)

    fun saveUser(user: User) {
        var transaction: Transaction? = null
        val session: Session = SessionManager.getSession()
        try {
            transaction = session.beginTransaction()
            session.save(user)
            transaction.commit()
            logger.info("User saved successfully: {}", user.username)
        } catch (e: Exception) {
            transaction?.rollback()
            logger.error("Transaction rolled back due to error while saving user: {}", user.username, e)
        }
    }

    fun getUserByUsername(username: String): User? {
        val session = SessionManager.getSession()
        return try {
            session.createQuery("FROM User WHERE username = :username", User::class.java)
                .setParameter("username", username)
                .uniqueResult().also {
                    logger.info("Retrieved user by username: {}", username)
                }
        } catch (e: Exception) {
            logger.error("Error retrieving user by username: {}", username, e)
            null
        }
    }
}
