package cz.lsrom.deadlyabroutine.model

import android.content.Context
import cz.lsrom.deadlyabroutine.R
import cz.lsrom.deadlyabroutine.SpManager

/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
class RoutineStorage {
    companion object {
        val BEGINNER: Int = 0
        val INTERMEDIATE: Int = 1
        val ADVANCED: Int = 2
        private var instance: RoutineStorage? = null

        fun getInstance(context: Context): RoutineStorage =
            instance ?: synchronized(this) {
                instance
                        ?: initiate(context).also { instance = it }
            }

        fun initiate(context: Context): RoutineStorage {
            val instance = RoutineStorage()
            instance.initiate(context)
            return instance
        }
    }

    val routines: MutableList<Routine> = ArrayList()

    private fun initiate(context: Context): RoutineStorage {
        val spManager: SpManager = SpManager.getInstance(context)
        routines.add(
            Routine(
                title = context.getString(R.string.routine_beginner_title),
                summary = context.getString(R.string.routine_beginner_summary),
                finished = spManager.getSetCount(BEGINNER),
                averageTime = spManager.getAverageTime(BEGINNER)
            )
        )
        routines.add(
            Routine(
                context.getString(R.string.routine_intermediate_title),
                context.getString(R.string.routine_intermediate_summary),
                finished = spManager.getSetCount(INTERMEDIATE),
                averageTime = spManager.getAverageTime(INTERMEDIATE)
            )
        )
        routines.add(
            Routine(
                context.getString(R.string.routine_advanced_title),
                context.getString(R.string.routine_advanced_summary),
                finished = spManager.getSetCount(ADVANCED),
                averageTime = spManager.getAverageTime(ADVANCED)
            )
        )

        return RoutineStorage()
    }
}