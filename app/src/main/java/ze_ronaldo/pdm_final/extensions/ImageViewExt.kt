package ze_ronaldo.pdm_final.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Loads an image from [url].
 */
fun ImageView.loadUrl(url: String) {
    println("Carregar imagem: $url")
    Picasso.Builder(context).build().load(url).into(this)
}