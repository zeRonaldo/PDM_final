package ze_ronaldo.pdm_final.gplaces.pojos

import com.google.gson.annotations.SerializedName

class GPlacePhoto(
    val height: Int,
    val width: Int,
    @SerializedName("photo_reference") val photoReference: String
)