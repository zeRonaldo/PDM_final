package ze_ronaldo.pdm_final.activities

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_favorites.*
import ze_ronaldo.pdm_final.R
import ze_ronaldo.pdm_final.adapters.FavoriteAdapter
import ze_ronaldo.pdm_final.models.Place
import ze_ronaldo.pdm_final.room.PlaceDatabase

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        recyclerFavorites.layoutManager = LinearLayoutManager(this)
        LoadFavoriteAsyncTask().execute()
    }

    fun showFavorites(favorites: List<Place>) {
        recyclerFavorites.adapter = FavoriteAdapter(favorites){ place -> openPlaceActv(place) }
    }

    private fun openPlaceActv(place: Place) {
        startActivity(PlaceActivity.getCallingIntent(this, place))
    }

    @SuppressLint("StaticFieldLeak")
    inner class LoadFavoriteAsyncTask : AsyncTask<Void, Void, List<Place>>() {
        private val placesDao = PlaceDatabase.getInstance(this@FavoritesActivity)?.placeDAO()

        override fun doInBackground(vararg params: Void): List<Place> {
            return placesDao?.getAll() ?: emptyList()
        }

        override fun onPostExecute(favorites: List<Place>) {
            showFavorites(favorites)
        }
    }
}
