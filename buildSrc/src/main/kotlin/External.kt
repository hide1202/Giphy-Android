import java.io.FileInputStream
import java.util.*

private fun readApiKey(): String = try {
    val file = FileInputStream("external.properties")
    val properties = Properties()
    properties.load(file)
    properties["giphy.api.key"] as String
} catch (t: Throwable) {
    "DEFAULT_API_KEY"
}

object External {
    val apiKey = readApiKey()
}