package kengooru

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class City(
        val name: String,
        val imageUrl: String?
) {
    @Id
    val id = UUID.randomUUID()
}

interface CityRepository : JpaRepository<City, UUID>