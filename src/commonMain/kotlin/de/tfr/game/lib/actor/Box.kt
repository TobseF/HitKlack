package de.tfr.game.lib.actor


interface Box : Point {
    var width: Double
    var height: Double

    fun center() = Point2D(widthHalf(), heightHalf())

    fun widthHalf() = width / 2
    fun heightHalf() = height / 2

    fun borderLeftX() = x - widthHalf()
    fun borderRightX() = x + widthHalf()
}