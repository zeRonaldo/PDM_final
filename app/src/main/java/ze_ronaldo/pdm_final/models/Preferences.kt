package ze_ronaldo.pdm_final.models

import java.io.Serializable

class Preferences (var placeType: String,
                  var priceRange: Int,
                  var maxDistance: Int,
                  var id: Int =1 ): Serializable {
    override fun toString(): String {
        return "${this.id} - Place Type: ${this.placeType} \n\t $: ${this.priceRange} \n\t ${this.maxDistance}"
    }
}