package ze_ronaldo.pdm_final.models


import android.arch.persistence.room.Entity
import java.io.Serializable

@Entity
 class Location(
    val lat: String,
    val lng: String
): Serializable