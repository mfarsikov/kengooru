package kengooru

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class KengooruApplication

fun main(args: Array<String>) {
    runApplication<KengooruApplication>(*args)
}

@RestController
class PlaceController {
    @GetMapping("/hello")
    fun  hello() = "hello!"

}