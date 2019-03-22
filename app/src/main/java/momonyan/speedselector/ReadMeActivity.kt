package momonyan.speedselector

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.readme_layout.*

class ReadMeActivity : AppCompatActivity() {
    private var marginListScore:Int = 0
    private var marginListScore2:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.readme_layout)

        scoreModeReadList.visibility = View.INVISIBLE
        val marginLayoutParams1 = scoreModeReadList.layoutParams
        marginListScore = marginLayoutParams1.height
        marginLayoutParams1.height = 0
        scoreModeReadList.layoutParams = marginLayoutParams1


        pointModeReadList.visibility = View.INVISIBLE
        val marginLayoutParams2 = pointModeReadList.layoutParams
        marginListScore2 = marginLayoutParams2.height
        marginLayoutParams2.height = 0
        pointModeReadList.layoutParams = marginLayoutParams2


        readMeBackButton.setOnClickListener {
            finish()
        }
        scoreModeReadButton.setOnClickListener {
            if (scoreModeReadList.visibility != View.VISIBLE) {
                scoreModeReadList.visibility = View.VISIBLE
                marginLayoutParams1.height = marginListScore
                scoreModeReadList.layoutParams = marginLayoutParams1
            } else {
                scoreModeReadList.visibility = View.INVISIBLE
                marginLayoutParams1.height = 0
                scoreModeReadList.layoutParams = marginLayoutParams1
            }

            pointModeReadList.visibility = View.INVISIBLE
            marginLayoutParams2.height = 0
            pointModeReadList.layoutParams = marginLayoutParams2
        }


        pointModeReadButton.setOnClickListener {
            if (pointModeReadList.visibility != View.VISIBLE) {
                pointModeReadList.visibility = View.VISIBLE
                marginLayoutParams2.height = marginListScore2
                pointModeReadList.layoutParams = marginLayoutParams2
            } else {
                pointModeReadList.visibility = View.INVISIBLE
                marginLayoutParams2.height = 0
                pointModeReadList.layoutParams = marginLayoutParams2
            }

            scoreModeReadList.visibility = View.INVISIBLE
            marginLayoutParams1.height = 0
            scoreModeReadList.layoutParams = marginLayoutParams1

        }
    }
}