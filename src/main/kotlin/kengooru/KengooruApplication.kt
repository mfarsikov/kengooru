package kengooru

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@SpringBootApplication
class KengooruApplication

fun main(args: Array<String>) {
    runApplication<KengooruApplication>(*args)
}


@Configuration
class Init(
        val itineraryRepository: ItineraryRepository,
        val cityRepository: CityRepository,
        val userRepository: UserRepository
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {

        val cities = listOf(
                City("New York", ""),
                City("Los Angeles", ""),
                City("Miami", ""),
                City("San Francisco", "")
        ).associateBy { it.name }

        val users = listOf(
                Person(firstName = "John", lastName = "Doe", photoUrl = ""),
                Person(firstName = "Gina", lastName = "Doe", photoUrl = ""),
                Person(firstName = "Maya", lastName = "Doe", photoUrl = ""),
                Person(firstName = "Justin", lastName = "Doe", photoUrl = "")
        ).associateBy { it.firstName }

        val itineraries = listOf(
                Itinerary(title = "Some title",
                          userId = users["John"]!!.id,
                          cityId = cities["Los Angeles"]!!.id,
                          date = LocalDate.now(),
                          points = listOf(Point(text = "some text", lat = 0.0, lon = 0.0))),
                Itinerary(title = "Some title",
                          userId = users["Gina"]!!.id,
                          cityId = cities["Los Angeles"]!!.id,
                          date = LocalDate.now(),
                          points = listOf(Point(text = "some text", lat = 0.0, lon = 0.0))),
                Itinerary(title = "Some title",
                          userId = users["Maya"]!!.id,
                          cityId = cities["Los Angeles"]!!.id,
                          date = LocalDate.now(),
                          points = listOf(Point(text = "some text", lat = 0.0, lon = 0.0))),
                Itinerary(title = "Some title",
                          userId = users["Justin"]!!.id,
                          cityId = cities["Los Angeles"]!!.id,
                          date = LocalDate.now(),
                          points = listOf(Point(text = "some text", lat = 0.0, lon = 0.0)))
        )

        itineraryRepository.saveAll(itineraries)
        cityRepository.saveAll(cities.values)
        userRepository.saveAll(users.values)
    }
}