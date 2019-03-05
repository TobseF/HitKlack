package de.tfr.game.lib.actor


open class Point2D(override var x: Double, override var y: Double) : Point {

    constructor(point: Point) : this(point.x, point.y)

    fun shift(x: Double, y: Double): Point2D {
        return Point2D(this.x + x, this.y + y)
    }

    fun shiftTop(a: Double) = shift(0.0, -a)
    fun shiftBottom(a: Double) = shift(0.0, a)
    fun shiftLeft(a: Double) = shift(-a, 0.0)
    fun shiftRight(a: Double) = shift(a, 0.0)

}
