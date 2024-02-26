package utils

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.slf4j.LoggerFactory

// Define an object named HibernateUtil to ensure it's a singleton
object HibernateUtil {
    private val logger = LoggerFactory.getLogger(HibernateUtil::class.java)


    val sessionFactory: SessionFactory = try {
        // Attempt to create a Configuration object and configure it based on the hibernate.cfg.xml file.
        // Then, build a SessionFactory from this configuration.
        // The "also block" is executed if the sessionFactory creation is successful, logging a success message.
        Configuration().configure().buildSessionFactory().also {
            logger.info("SessionFactory created successfully.")
        }
    } catch (ex: Throwable) {
        logger.error("Failed to create sessionFactory object.", ex)
        throw ExceptionInInitializerError(ex)
    }
}
