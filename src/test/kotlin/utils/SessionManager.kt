package utils

import org.hibernate.Session
import org.slf4j.LoggerFactory

object SessionManager {

    private val logger = LoggerFactory.getLogger(SessionManager::class.java)
    private var session: Session? = null

    fun openSession() {
        try {
            if (session == null || !session!!.isOpen) {
                session = HibernateUtil.sessionFactory.openSession()
                logger.info("Database session successfully opened.")
            }
        } catch (e: Exception) {
            logger.error("Failed to open database session: {}", e.message)
        }
    }

    fun closeSession() {
        try {
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

    fun getSession(): Session {
        return session ?: throw IllegalStateException("Session has not been initialized. Call openSession() first.")
    }
}
