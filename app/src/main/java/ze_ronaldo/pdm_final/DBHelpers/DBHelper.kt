package ze_ronaldo.pdm_final.DBHelpers


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


val VERSAO = 1
class DBHelper(context: Context?) : SQLiteOpenHelper(context, "acaso.sqlite", null, VERSAO){
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE OPTION(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "placeType STRING," +
                "maxDistance INTEGER"+
                ");"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}