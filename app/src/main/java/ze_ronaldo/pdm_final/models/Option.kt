package ze_ronaldo.pdm_final.models


import java.io.Serializable

class Option (var placeType: String,
              var maxDistance: Int,
              var id: Int =1 ):Serializable{
    override fun toString(): String {
        return "${this.id} - Place Type: ${this.placeType} \n\t \n\t ${this.maxDistance}"
    }
}