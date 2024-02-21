import java.util.Properties

object PropertiesUtil {
    private val properties: Properties = Properties()

    init {
        val inputStream = this::class.java.classLoader.getResourceAsStream("application.properties")
        properties.load(inputStream)
    }

    fun getProperty(key: String): String? {
        return properties.getProperty(key)
    }
}