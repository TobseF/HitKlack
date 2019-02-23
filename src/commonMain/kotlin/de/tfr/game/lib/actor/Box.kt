package de.tfr.game.lib.actor


interface Box : Point {
    var width: Float
    var height: Float

    fun center() = Point2D(width / 2.0, height / 2.0)

}