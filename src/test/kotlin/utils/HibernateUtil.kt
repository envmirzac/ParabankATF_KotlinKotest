package utils

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.slf4j.LoggerFactory

object HibernateUtil {
    private val logger = LoggerFactory.getLogger(HibernateUtil::class.java)
    val sessionFactory: SessionFactory = try {
        Configuration().configure().buildSessionFactory().also {
            logger.info("SessionFactory created successfully.")
        }
    } catch (ex: Throwable) {
        logger.error("Failed to create sessionFactory object.", ex)
        throw ExceptionInInitializerError(ex)
    }
}
