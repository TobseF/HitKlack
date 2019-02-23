package de.tfr.game.libgx.emu

import com.soywiz.korma.geom.IPoint
import com.soywiz.korma.geom.IRectangle
import com.soywiz.korma.geom.Point
import com.soywiz.korma.geom.Rectangle
import com.soywiz.korma.interpolation.Interpolable
import com.soywiz.korma.interpolation.MutableInterpolable

open class Rectangle(override var x: Double,
        override var y: Double,
        override var width: Double,
        override var height: Double) : MutableInterpolable<Rectangle>, Interpolable<Rectangle>, IRectangle {

    override fun interpolateWith(ratio: Double, other: Rectangle): Rectangle {
        return rectangle.interpolateWith(ratio, other)
    }

    fun center() = Point(x + (height / 2), y + (width / 2))

    val rectangle: Rectangle = com.soywiz.korma.geom.Rectangle(x, y, width, height)

    fun contains(x: Double, y: Double) = rectangle.contains(x, y)
    operator fun contains(that: Rectangle) = rectangle.contains(that)
    operator fun contains(that: IPoint) = rectangle.contains(that.x, that.y)


    override fun setToInterpolated(ratio: Double, l: Rectangle, r: Rectangle): Rectangle {
        return rectangle.setToInterpolated(ratio, l, r)
    }

}