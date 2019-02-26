package de.tfr.game.util

import de.tfr.game.lib.actor.Point2D

data class Resolution(var width: Int, var height: Int) {
    fun getCenter() = Point2D(width / 2.0, height / 2.0)
}