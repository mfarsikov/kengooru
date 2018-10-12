package kengooru

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany


interface ItineraryRepository : JpaRepository<Itinerary, UUID> {
    fun findByCityId(cityId: UUID): List<Itinerary>
    fun findByUserId(userId: UUID): List<Itinerary>
}

data class ItineraryDto(
        val id: UUID,
        val userId: UUID,
        val cityId: UUID,
        val title: String,
        val date: LocalDate,
        val points: List<Point>,
        val photoUrl: String,
        val firstName: String,
        val lastName: String
)

@Entity
data class Itinerary(
        val userId: UUID,
        val cityId: UUID,
        val title: String,
        val date: LocalDate,
        @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        val points: List<Point>
) {
    @Id
    val id = UUID.randomUUID()
}

@Entity
data class Point(
        val text: String,
        val lat: Double,
        val lon: Double
) {
    @Id
    val id = UUID.randomUUID()
}
