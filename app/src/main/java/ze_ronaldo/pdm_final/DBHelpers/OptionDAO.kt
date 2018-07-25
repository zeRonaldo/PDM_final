package ze_ronaldo.pdm_final.DBHelpers

import android.content.ContentValues
import android.content.Context
import ze_ronaldo.pdm_final.models.Option


class OptionDAO(var context: Context){
    private lateinit var dbHelper: DBHelper

    init {
        this.dbHelper = DBHelper(context)
    }



    fun insert(cli: Option){
        val columns = arrayOf("id", "placeType","maxDistance")
        val cursor = this.dbHelper.readableDatabase.query("Option", columns,null,null,null,null,null)
        if (cursor.count == 0) {
            val cv = ContentValues()
            cv.put("placeType", cli.placeType)
            cv.put("maxDistance", cli.maxDistance)
            this.dbHelper.writableDatabase.insert("Option", null, cv)
        }else{
            update(cli)
        }
    }

    fun read(): Option{
        var cli : Option
        val columns = arrayOf("id", "placeType","maxDistance")
        val cursor = this.dbHelper.readableDatabase.query("Client", columns,null,null,null,null,null)

        cursor.moveToFirst()
        if (cursor.count > 0) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val placeType = cursor.getString(cursor.getColumnIndex("placeType"))
                val maxDistance = cursor.getInt(cursor.getColumnIndex("maxDistance"))
                cli = ( Option( placeType,maxDistance, id) )
            } while (cursor.moveToNext())
        }else{
            cli = Option("",0)
        }
        return cli
    }

    fun update(cli: Option){
        val cv = ContentValues()
        cv.put("id", cli.id)
        cv.put("placeType", cli.placeType)
        cv.put("maxDistance", cli.maxDistance)

        this.dbHelper.writableDatabase.update("Client", cv, "id = ?", arrayOf("1"))

    }
}