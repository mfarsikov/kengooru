package kengooru

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(
        val firstName: String,
        val lastName: String,
        val photoUrl: String
) {
    @Id
    val id = UUID.randomUUID()
}

interface UserRepository : JpaRepository<User, UUID> {
    fun findByIdIn(ids: List<UUID>): List<User>
}