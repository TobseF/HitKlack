package de.tfr.game

import de.tfr.game.lib.actor.Box
import de.tfr.game.util.StopWatch
import de.tfr.game.util.TimerFormatter


class Display(box: Box) : Box by box {

    var watch = StopWatch()
    var formatter = TimerFormatter()

    fun getText() = formatter.getFormattedTimeAsString(watch.getTime())
}
