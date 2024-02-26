package utils
import java.util.Properties

// Define an object named PropertiesUtil which will be a singleton
object PropertiesUtil {
    private val properties: Properties = Properties()

    // The init block is executed as soon as the object is initialized.
    init {

        val inputStream = this::class.java.classLoader.getResourceAsStream("application.properties")
        properties.load(inputStream)
    }

    // Define a function to retrieve a property value by its key.
    // The function returns a nullable String because the property may not exist.
    fun getProperty(key: String): String? {
        // If the key is not found, getProperty will return null.
        return properties.getProperty(key)
    }
}
