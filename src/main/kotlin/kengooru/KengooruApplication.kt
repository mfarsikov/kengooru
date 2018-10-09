package kengooru

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.time.LocalDate
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany

@SpringBootApplication
class KengooruApplication

fun main(args: Array<String>) {
    runApplication<KengooruApplication>(*args)
}

@RestController
class ItineraryController(
        val itineraryRepository: ItineraryRepository
) {
    @PostMapping("/itineraries")
    fun create(@RequestBody itinerary: Itinerary): ResponseEntity<Unit> {
        itineraryRepository.save(itinerary)
        return ResponseEntity.created(URI("/itineraries/${itinerary.id}")).build()
    }

    @GetMapping("/itineraries")
    fun readAll(): List<Itinerary> {
        return itineraryRepository.findAll()
    }

    @GetMapping("/itineraries/{id}")
    fun getItinerary(@PathVariable id: UUID): Itinerary {
        return itineraryRepository.getOne(id)
    }
}

interface ItineraryRepository : JpaRepository<Itinerary, UUID>

@Entity
class Itinerary(
        val ownerId: String,
        val title: String,
        val date: LocalDate,
        @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        val points: List<Point>
) {
    @Id
    val id = UUID.randomUUID()
}

@Entity
class Point(
        val text: String,
        val lat: Double,
        val lon: Double
) {
    @Id
    val id = UUID.randomUUID()
}

@Configuration
class Config {
    @Bean
    fun init(itineraryRepository: ItineraryRepository): ApplicationRunner {
        return ApplicationRunner {
            itineraryRepository.save(Itinerary(title = "Some title",
                                               ownerId = "123",
                                               date = LocalDate.now(),
                                               points = listOf(Point(text = "some text", lat = 0.0, lon = 0.0))))
        }
    }
}