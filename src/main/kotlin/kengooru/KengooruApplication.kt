package kengooru

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@SpringBootApplication
class KengooruApplication

fun main(args: Array<String>) {
    runApplication<KengooruApplication>(*args)
}

@RestController
class ItineraryController(
        val itineraryRepository: ItineraryRepository
) {
    @GetMapping("/itineraries/{id}")
    fun getItinerary(@PathVariable id: String): Itinerary {
        return itineraryRepository.findAll().first()
    }
}

interface ItineraryRepository : JpaRepository<Itinerary, Int>

@Entity
class Itinerary(
        val title: String,
        val date: LocalDate,
        val points: List<Point>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null
}

class Point(
        val text: String,
        val lat: Double,
        val lon: Double
)

@Configuration
class Config {
    @Bean
    fun init(itineraryRepository: ItineraryRepository): ApplicationRunner {
        return ApplicationRunner {
            itineraryRepository.save(Itinerary(title = "Some title",
                                               date = LocalDate.now(),
                                               points = listOf(Point(text = "some text", lat = 0.0, lon = 0.0))))
        }
    }
}