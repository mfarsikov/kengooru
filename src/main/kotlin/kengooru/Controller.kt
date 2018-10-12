package kengooru

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*


@RestController
class ItineraryController(
        val itineraryRepository: ItineraryRepository,
        val userRepository: UserRepository,
        val cityRepository: CityRepository
) {
    @PostMapping("/itineraries")
    fun create(@RequestBody itinerary: Itinerary): ResponseEntity<Unit> {
        itineraryRepository.save(itinerary)
        return ResponseEntity.created(URI("/itineraries/${itinerary.id}")).build()
    }

    @GetMapping("/cities/{cityId}/itineraries")
    fun readByCityId(@PathVariable cityId: UUID): List<ItineraryDto> {
        val itineraries = itineraryRepository.findByCityId(cityId)
        val userIds = itineraries.map { it.userId }.distinct()
        val userIdToUser = userRepository.findByIdIn(userIds).associateBy { it.id }

        return itineraries.map {
            val user = userIdToUser[it.userId]!!
            ItineraryDto(
                    id = it.id,
                    userId = it.userId,
                    cityId = it.cityId,
                    title = it.title,
                    date = it.date,
                    points = it.points,
                    photoUrl = user.photoUrl,
                    firstName = user.firstName,
                    lastName = user.lastName
            )
        }
    }

    @GetMapping("/users/{userId}/itineraries")
    fun readByUserId(@PathVariable userId: UUID): List<ItineraryDto> {
        val itineraries = itineraryRepository.findByUserId(userId)
        val user = userRepository.findById(userId).orElse(null)

        return itineraries.map {
            ItineraryDto(
                    id = it.id,
                    userId = it.userId,
                    cityId = it.cityId,
                    title = it.title,
                    date = it.date,
                    points = it.points,
                    photoUrl = user.photoUrl,
                    firstName = user.firstName,
                    lastName = user.lastName
            )
        }
    }

    @GetMapping("/itineraries/{id}")
    fun getItinerary(@PathVariable id: UUID): Itinerary? {
        return itineraryRepository.findById(id).orElse(null)
    }

    @GetMapping("/cities")
    fun getCities(): List<City> {
        return cityRepository.findAll()
    }
}