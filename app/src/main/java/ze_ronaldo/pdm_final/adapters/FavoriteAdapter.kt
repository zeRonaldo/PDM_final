package ze_ronaldo.pdm_final.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_place.view.*
import ze_ronaldo.pdm_final.R
import ze_ronaldo.pdm_final.models.Place

class FavoriteAdapter(
    private val places: List<Place>,
    private val onPlaceClicked: (Place) -> Unit
): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_place, parent, false)

        return ViewHolder(view, onPlaceClicked)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(places[position])
    }

    class ViewHolder (
        override val containerView: View,
        val onPlaceClicked: (Place) -> Unit
    ): RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(place: Place) {
            containerView.txtPlaceTitle.text = place.name
            containerView.setOnClickListener { onPlaceClicked(place) }
        }
    }
}