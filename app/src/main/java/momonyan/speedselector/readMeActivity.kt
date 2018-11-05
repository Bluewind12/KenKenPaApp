package momonyan.speedselector

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.readme_layout.*

class readMeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.readme_layout)
        readMeBackButton.setOnClickListener {
            finish()
        }
    }
}