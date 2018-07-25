package ze_ronaldo.pdm_final.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_option.*
import ze_ronaldo.pdm_final.R
import ze_ronaldo.pdm_final.models.Option
import kotlin.math.absoluteValue

class OptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        //btSubmit.setOnClickListener(PersistOption())
    }

//    private fun PersistOption(): View.OnClickListener? {
//        var option = Option( ,sbDistance.progress)
//
//    }
}
