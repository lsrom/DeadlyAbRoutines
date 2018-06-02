package cz.lsrom.deadlyabroutine

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import cz.lsrom.deadlyabroutine.model.RoutineStorage
import cz.lsrom.deadlyabroutine.model.Time
import kotlinx.android.synthetic.main.activity_result.*

/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
class ResultActivity : BaseActivity() {
    companion object {
        private val TIME_TAG = "time"
        private val ROUTINE_TAG = "routine"

        fun startIntent(time: Time, routine: Int, context: Context): Intent {
            return Intent(context, ResultActivity::class.java)
                .putExtra(TIME_TAG, time)
                .putExtra(ROUTINE_TAG, routine)
        }
    }

    private var routine: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_result)
        routine = intent.getIntExtra(ROUTINE_TAG, 0)

        var timeHolder = intent.getParcelableExtra<Time>(TIME_TAG)

        when (routine) {
            RoutineStorage.BEGINNER -> {

            }
        }

        spManager.addSet((timeHolder.minutes * 60) + timeHolder.seconds, routine)

        var time = spManager.getAverageTime(routine)
        var avgMins = 0
        var avgSecs = 0
        if (time != 0) {
            avgMins = time / 60
            avgSecs = time % 60
        }

        txtFinishedIn.text = String.format(
            getString(R.string.result_finished_in),
            timeHolder.minutes,
            timeHolder.seconds
        )
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
