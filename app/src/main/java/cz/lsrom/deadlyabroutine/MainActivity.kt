package cz.lsrom.deadlyabroutine

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import cz.lsrom.deadlyabroutine.model.Routine
import cz.lsrom.deadlyabroutine.model.RoutineAdapter
import cz.lsrom.deadlyabroutine.model.RoutineStorage
import cz.lsrom.deadlyabroutine.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
class MainActivity : BaseActivity(), IRoutineSelector {
    companion object {
        fun startIntent(context: Context): Intent {
            return Intent(
                context,
                MainActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun routineSelected(routine: Int) {
        startActivity(RoutineActivity.startIntent(routine, this))
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val routineStorage: RoutineStorage = RoutineStorage.getInstance(this)

        var adapter = RoutineAdapter(routineStorage.routines, this, this)
        routinesList.layoutManager = LinearLayoutManager(this)
        routinesList.adapter = adapter
    }
}