package de.tfr.game.util


class TimerFormatter {

    fun getFormattedTimeAsString(time: ms): String {
        val totalSeconds = time / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds - (minutes * 60)
        fun twoDigits(number: ms) = if (number < 10) "0" + number else number.toString()
        return twoDigits(minutes) + ":" + twoDigits(seconds)
    }


}