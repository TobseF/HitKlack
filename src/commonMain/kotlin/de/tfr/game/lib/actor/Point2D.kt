package de.tfr.game.lib.actor


open class Point2D(override var x: Double, override var y: Double) : Point {

    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())
}
