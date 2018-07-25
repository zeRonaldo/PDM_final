package ze_ronaldo.pdm_final.models



import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


import java.io.Serializable

@Entity(tableName = "place")
class Place(
    val name: String,
    @SerializedName("vicinity")
        val address: String,
    val description: String?,
    @SerializedName("formatted_phone_number")
        val telephoneNumber: String?,
    val geometry: Geometry,
    @PrimaryKey
    @SerializedName("place_id")
        val placeId: String,
    val rating: Float,
    val photos: List<Photo>?

) : Serializable  {

    //Getters
    fun getLat(): String{
        return this.geometry.location.lat
    }
    fun getLng(): String{
        return this.geometry.location.lng
    }
    fun getPhotoRef(num: Int):String{
        if (this.photos?.get(num)  != null){
        val photoRef = this.photos.get(num).photoRefence
            return  photoRef
        }else{
            return ""
        }
    }


    //ToString
    override fun toString(): String {
        return "Nome: ${name} " +
                "\n Endereço:${address} " +
                "\n Descrição: ${description} " +
                "\n Telefone: ${telephoneNumber}" +
                "\n Classificação: ${rating}" +
                "\n Coordenadas: Lat(${getLat()}) , Lon(${getLng()})" +
                "\n ID: ${placeId}"
    }
}



