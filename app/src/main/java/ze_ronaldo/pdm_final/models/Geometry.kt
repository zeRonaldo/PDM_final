package ze_ronaldo.pdm_final.models


import android.arch.persistence.room.Entity
import java.io.Serializable

@Entity
public class Geometry(
    val location: Location
):  Serializable