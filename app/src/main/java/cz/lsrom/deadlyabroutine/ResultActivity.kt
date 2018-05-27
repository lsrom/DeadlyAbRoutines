package cz.lsrom.deadlyabroutine

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.lsrom.deadlyabroutine.model.RoutineStorage
import kotlinx.android.synthetic.main.activity_result.*

/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
class ResultActivity : BaseActivity() {
    companion object {
        private val MINUTES_TAG = "mins"
        private val SECONDS_TAG = "secs"
        private val ROUTINE_TAG = "routine"

        fun startIntent(minutes: Int, seconds: Int, routine: Int, context: Context): Intent {
            return Intent(context, ResultActivity::class.java).putExtra(MINUTES_TAG, minutes)
                .putExtra(
                    SECONDS_TAG, seconds
                )
                .putExtra(ROUTINE_TAG, routine)
        }
    }

    private var routine: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_result)
        routine = intent.getIntExtra(ROUTINE_TAG, 0)

        var minutes = intent.getIntExtra(MINUTES_TAG, 0)
        var seconds = intent.getIntExtra(SECONDS_TAG, 0)

        when (routine) {
            RoutineStorage.BEGINNER -> {

            }
        }

        spManager.addSet((minutes * 60) + seconds, routine)

        var time = spManager.getAverageTime(routine)
        var avgMins = 0
        var avgSecs = 0
        if (time != 0) {
            avgMins = time / 60
            avgSecs = time % 60
        }

        txtFinishedIn.text = String.format(getString(R.string.result_finished_in), minutes, seconds)
        txtAverageTime.text = String.format(
            getString(R.string.result_average_time),
            avgMins, avgSecs
        )
        txtFinishedCount.text = String.format(
            getString(R.string.result_finished_count),
            spManager.getSetCount(routine)
        )

        btnFinish.setOnClickListener(View.OnClickListener {
            startActivity(MainActivity.startIntent(this))
            finish()
        })

        btnRepeat.setOnClickListener(View.OnClickListener {
            startActivity(
                RoutineActivity.startIntent(
                    routine, this
                )
            )
        })
    }
}
