package ze_ronaldo.pdm_final.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import ze_ronaldo.pdm_final.models.Place

@Dao
interface PlaceDAO {

    @Query("SELECT * FROM place")
    fun getAll(): List<Place>

    @Query("SELECT * FROM place WHERE placeid LIKE :id LIMIT 1")
    fun findByID(id: String): Place?

    @Insert
    fun insertAll(place: List<Place>)

    @Insert
    fun insertPlace(place: Place)

    @Delete
    fun delete(place: Place)

}