package de.tfr.game.lib.actor


open class Point2D(override var x: Double, override var y: Double) : Point {

    constructor(point: Point) : this(point.x, point.y)

}
