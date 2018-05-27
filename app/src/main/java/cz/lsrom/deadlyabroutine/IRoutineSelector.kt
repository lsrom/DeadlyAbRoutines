package cz.lsrom.deadlyabroutine

import cz.lsrom.deadlyabroutine.model.Routine

/**
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
interface IRoutineSelector {
    fun routineSelected(routine: Int)
}