package ze_ronaldo.pdm_final.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ze_ronaldo.pdm_final.models.Place

@Database(entities = arrayOf(Place::class), version = 1)
abstract class PlaceDatabase : RoomDatabase() {

    abstract fun placeDAO(): PlaceDAO

    companion object {
        private var INSTANCE: PlaceDatabase? = null

        fun getInstance(context: Context): PlaceDatabase? {
            if (INSTANCE == null) {
                synchronized(PlaceDatabase::class) {
                    INSTANCE =
                            Room
                                .databaseBuilder(
                                    context.applicationContext,
                                    PlaceDatabase::class.java,
                                    "places.db"
                                )
                                .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}