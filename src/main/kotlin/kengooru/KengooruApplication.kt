package kengooru

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KengooruApplication

fun main(args: Array<String>) {
    runApplication<KengooruApplication>(*args)
}
