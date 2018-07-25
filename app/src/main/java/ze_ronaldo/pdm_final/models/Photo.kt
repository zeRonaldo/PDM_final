package ze_ronaldo.pdm_final.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

import java.io.Serializable

@Entity
public class Photo(
    @Ignore
    val height: Int,
    @Ignore
    val width: Int,
    @PrimaryKey
    @SerializedName("photo_reference") val photoRefence: String
): Serializable{
}