package utils

import org.hibernate.Session
import org.slf4j.LoggerFactory

// Define an object ("SessionManager") to manage Hibernate sessions. It also ensures a singleton pattern
object SessionManager {
    private val logger = LoggerFactory.getLogger(SessionManager::class.java)
    // Declare a nullable Session variable (Session?) to hold the Hibernate session.
    private var session: Session? = null

    // Define a function to open a new Hibernate session (if one isn't already open)
    fun openSession() {
        try {
            // Check if the session is either not initialized or not open.
            if (session == null || !session!!.isOpen) {
                // Open a new session from the sessionFactory and assign it to the session variable.
                session = HibernateUtil.sessionFactory.openSession()
                logger.info("Database session successfully opened.")
            }
        } catch (e: Exception) {
            logger.error("Failed to open database session: {}", e.message)
        }
    }

    // Define a function to close the current  session if it's open.
    fun closeSession() {
        try {
            // Use a safe-call to check if the session is not null.
            session?.let {
                if (it.isOpen) {
                    it.close()
                    logger.info("Database session successfully closed.")
                }
            }
        } catch (e: Exception) {
            logger.error("Failed to close database session: {}", e.message)
        }
    }

    // Define a function to retrieve the current session.
    fun getSession(): Session {
        // If it's null, throw an IllegalStateException.
        return session ?: throw IllegalStateException("Session has not been initialized. Call openSession() first.")
    }
}
