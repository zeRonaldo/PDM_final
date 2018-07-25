package ze_ronaldo.pdm_final.models

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface CustomerDao {

    @get:Query("SELECT * FROM place")
    val all: List<Place>


    @Query("SELECT * FROM place WHERE placeid LIKE :ID LIMIT 1")
    fun findByID(ID: String): Place

    @Insert
    fun insertAll(place: List<Place>)

    @Insert
    fun insertPlace(place: Place)

    @Delete
    fun delete(place: Place)

}