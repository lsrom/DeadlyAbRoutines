package cz.lsrom.deadlyabroutine.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import cz.lsrom.deadlyabroutine.model.Time

/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
class RoutineViewModel : ViewModel() {
    val time: MutableLiveData<Time> = MutableLiveData()

    private var startTime: Long = 0
    private var seconds: Int = 0
    private var timerHandler = Handler()
    private var position: Int = 0
    private var timerRunnable: Runnable = object : Runnable {

        override fun run() {
            val millis = System.currentTimeMillis() - startTime
            seconds = (millis / 1000).toInt()
            val t = Time(seconds / 60, seconds % 60)
            time.value = t

            timerHandler.postDelayed(this, 500)
        }
    }

    fun startRoutine() {
        startTime = System.currentTimeMillis()
        timerHandler.postDelayed(timerRunnable, 0)
    }

    fun observableTime(): LiveData<Time> {
        return time
    }

    fun getPosition(): Int {
        return position
    }

    fun incrementPosition() {
        position++
    }
}