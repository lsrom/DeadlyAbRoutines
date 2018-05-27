package cz.lsrom.deadlyabroutine

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
class SpManager private constructor() {
    companion object {
        private val TAG: String = SpManager::class.java.simpleName
        private val SP_NAME = "storage"
        private val SET_COUNT = "beg_set_count"
        private val TOTAL_TIME = "beg_avg_time"
        private val DARK_THEME = "dark_theme"

        fun getInstance(context: Context): SpManager {
            val mngr: SpManager = SpManager()
            mngr.sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            mngr.editor = mngr.sp.edit()
            return mngr
        }
    }

    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun addSet(time: Int, routine: Int) {
        var totalTime: Int = sp.getInt(TOTAL_TIME + routine, 0)
        var count = sp.getInt(SET_COUNT + routine, 0)

        count++
        totalTime += time
        editor.putInt(TOTAL_TIME + routine, totalTime)
        editor.putInt(SET_COUNT + routine, count)

        editor.apply()
    }

    fun getSetCount(routine: Int): Int {
        return sp.getInt(SET_COUNT + routine, 0)
    }

    // todo make universal for all routines?
    fun getAverageTime(routine: Int): Int {
        var totalTime: Int = sp.getInt(TOTAL_TIME + routine, 0)
        var count = sp.getInt(SET_COUNT + routine, 0)

        if (count == 0) {
            return 0
        }

        return totalTime / count
    }

    fun setDarkTheme(dark: Boolean) {
        Log.d(TAG, "Dark theme: " + dark)
        editor.putBoolean(DARK_THEME, dark)
        editor.apply()
    }

    fun isDarkTheme(): Boolean {
        return sp.getBoolean(DARK_THEME, false)
    }
}