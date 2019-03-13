package de.tfr.game.lib.actor


interface Point {
    var x: Double
    var y: Double

    fun shift(shiftXY: Double): Point2D {
        return shift(shiftXY, shiftXY)
    }

    fun shift(x: Double, y: Double): Point2D {
        return Point2D(this.x + x, this.y + y)
    }

    fun shiftTop(a: Double) = shift(0.0, -a)
    fun shiftBottom(a: Double) = shift(0.0, a)
    fun shiftLeft(a: Double) = shift(-a, 0.0)
    fun shiftRight(a: Double) = shift(a, 0.0)
}