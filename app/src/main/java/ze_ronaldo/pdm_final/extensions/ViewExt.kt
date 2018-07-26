package ze_ronaldo.pdm_final.extensions

import android.view.View

/**
 * Disables a view.
 */
fun View.disable() {
    isEnabled = false
    alpha = 0.3f
}

/**
 * Enables a view.
 */
fun View.enable() {
    isEnabled = true
    alpha = 1.0f
}