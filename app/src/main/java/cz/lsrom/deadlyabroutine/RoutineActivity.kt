package cz.lsrom.deadlyabroutine

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import cz.lsrom.deadlyabroutine.`interface`.ISwipeListener
import cz.lsrom.deadlyabroutine.model.Exercises
import cz.lsrom.deadlyabroutine.model.Routine
import cz.lsrom.deadlyabroutine.model.RoutineStorage
import cz.lsrom.deadlyabroutine.model.Time
import cz.lsrom.deadlyabroutine.viewmodel.RoutineViewModel
import kotlinx.android.synthetic.main.activity_routine.*
import pl.droidsonroids.gif.GifDrawable

/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
class RoutineActivity : BaseActivity(), ISwipeListener {
    override fun leftToRight() {
        if (txtTimer.visibility == View.VISIBLE) {
            nextExercise()
        } else {
            startRoutine()
        }
    }

    override fun rightToLeft() {
        previousExercise()
    }

    private val viewModel: RoutineViewModel by lazy {
        ViewModelProviders.of(this).get(RoutineViewModel::class.java)
    }

    private lateinit var routine: Routine
    private lateinit var exercises: Exercises
    private var routineNumber = 0

    companion object {
        private val TAG: String = RoutineActivity::class.java.simpleName
        private val EXTRA = "routine_data"

        private val VELOCITY_THRESHOLD: Long = 3000
        fun startIntent(routine: Int, context: Context): Intent {
            val intent = Intent(context, RoutineActivity::class.java)
            intent.putExtra(EXTRA, routine)

            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_routine)

        if (!intent.hasExtra(EXTRA)) {
            Log.e(TAG, "Activity not started with routine.")
            finish()
        }

        activityRoutine.setOnTouchListener(OnSwipeTouchListener(this, this))

        routineNumber = intent.getIntExtra(EXTRA, 0)
        routine = RoutineStorage.getInstance(this).routines.get(routineNumber)
        exercises = getRoutineExercises(routine)
        supportActionBar?.title = routine.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        if (viewModel.observableTime().value != null) {
            btnNext.text = getString(R.string.routine_btn_next_text)
            txtTimer.visibility = View.VISIBLE
            observerTime()
        }

        btnNext.setOnClickListener(View.OnClickListener {
            if (btnNext.text == getString(R.string.routine_btn_start_text)) {
                startRoutine()
            } else {
                nextExercise()
            }
        })

        showExercise(viewModel.getPosition())
        txtReps.text = exercises.reps

        // keep screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun startRoutine() {
        observerTime()
        viewModel.startRoutine()

        btnNext.text = getString(R.string.routine_btn_next_text)
        txtTimer.visibility = View.VISIBLE
    }

    fun observerTime() {
        viewModel.observableTime().observe(this, Observer {
            txtTimer.text = String.format("%d:%02d", it?.minutes, it?.seconds)
        })
    }

    fun nextExercise() {
        if (viewModel.getPosition() < exercises.names.length() - 1) {
            viewModel.incrementPosition()
            showExercise(viewModel.getPosition())
            if (exercises.names.length() - 1 == viewModel.getPosition()) {
                btnNext.text = getString(R.string.routine_btn_finish_text)
            }
        } else {
            val time: Time? = viewModel.observableTime().value
            if (time != null) {
                startActivity(ResultActivity.startIntent(time!!, routineNumber, this))
            }
        }
    }

    fun previousExercise() {
        if (viewModel.getPosition() > 0) {
            viewModel.incrementPosition()
            showExercise(viewModel.getPosition())
            btnNext.text = getString(R.string.routine_btn_next_text)
        }
    }

    fun showExercise(position: Int) {
        txtExercise.text = exercises.names.getText(position)
        gifExercise.setImageResource(exercises.gifs.getResourceId(position, 0))
    }

    fun getRoutineExercises(routine: Routine): Exercises {
        when (routine.title) {
            getString(R.string.routine_beginner_title) -> {
                return Exercises(
                    names = resources?.obtainTypedArray(R.array.beginner_routine)!!,
                    gifs = resources?.obtainTypedArray(R.array.beginner_routine_gifs)!!,
                    reps = getString(R.string.routine_beginner_reps)
                )
            }

            getString(R.string.routine_intermediate_title) -> {
                return Exercises(
                    names = resources?.obtainTypedArray(R.array.intermediate_routine)!!,
                    gifs = resources?.obtainTypedArray(R.array.intermediate_routine_gifs)!!,
                    reps = getString(R.string.routine_intermediate_reps)
                )
            }

            getString(R.string.routine_advanced_title) -> {
                return Exercises(
                    names = resources?.obtainTypedArray(R.array.advanced_routine)!!,
                    gifs = resources?.obtainTypedArray(R.array.advanced_routine_gifs)!!,
                    reps = getString(R.string.routine_advanced_reps)
                )
            }

            else -> {
                return Exercises(
                    names = resources?.obtainTypedArray(R.array.beginner_routine)!!,
                    gifs = resources?.obtainTypedArray(R.array.beginner_routine_gifs)!!,
                    reps = getString(R.string.routine_beginner_reps)
                )
            }
        }
    }
}