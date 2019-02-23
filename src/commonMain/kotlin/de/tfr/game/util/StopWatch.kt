package de.tfr.game.util

import com.soywiz.klock.PerformanceCounter


class StopWatch(var start: ms = PerformanceCounter.milliseconds.toLong()) {


    fun getTime(): ms = PerformanceCounter.milliseconds.toLong() - start
}
