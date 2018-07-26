package ze_ronaldo.pdm_final.models


import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ze_ronaldo.pdm_final.gplaces.pojos.GPlaceGeometry
import ze_ronaldo.pdm_final.gplaces.pojos.GPlacePhoto


import java.io.Serializable

@Entity(tableName = "place")
data class Place(
    @PrimaryKey var placeId: String,
    var address: String?,
    var telephoneNumber: String?,
    var name: String,
    var description: String?,
    var rating: Float,
    var latitude: String,
    var longitude: String,
    var firstImage: String?,
    var secondImage: String?
) : Serializable {

    //ToString
    override fun toString(): String {
        return "Nome: ${name} " +
                "\n Endereço:${address} " +
                "\n Descrição: ${description} " +
                "\n Telefone: ${telephoneNumber}" +
                "\n Classificação: ${rating}" +
                "\n Coordenadas: Lat($latitude) , Lon($longitude)" +
                "\n ID: ${placeId}"
    }
}



