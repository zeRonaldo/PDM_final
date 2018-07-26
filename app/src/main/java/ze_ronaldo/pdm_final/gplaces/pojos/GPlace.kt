package ze_ronaldo.pdm_final.gplaces.pojos


import com.google.gson.annotations.SerializedName
import ze_ronaldo.pdm_final.models.Place
import java.io.Serializable

class GPlace(
    @SerializedName("place_id") val placeId: String,
    @SerializedName("vicinity") val address: String?,
    @SerializedName("formatted_phone_number") val telephoneNumber: String?,
    val name: String,
    val description: String?,
    val rating: Float,
    private val geometry: GPlaceGeometry,
    private val photos: List<GPlacePhoto>?
) : Serializable {

    val latitude
        get() = geometry.location.lat

    val longitude
        get() = geometry.location.lng

    val images
        get() = photos?.map { it.photoReference } ?: emptyList()

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

    fun toPlace(): Place {
        return Place(
            placeId = placeId,
            address = address,
            telephoneNumber = telephoneNumber,
            name = name,
            description = description,
            rating = rating,
            latitude = latitude,
            longitude = longitude,
            firstImage = images.getOrNull(0),
            secondImage = images.getOrNull(1)
        )
    }
}



