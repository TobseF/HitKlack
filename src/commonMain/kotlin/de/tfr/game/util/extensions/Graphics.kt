package de.tfr.game.util.extensions

import com.soywiz.korge.view.Graphics
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.vector.Context2d
import com.soywiz.korma.geom.vector.VectorPath
import com.soywiz.korma.geom.vector.circle

fun Graphics.circle(x: Number, y: Number, radius: Number) {
    this.path { circle(x, y, radius) }
}

fun Graphics.path(path: (VectorPath) -> Unit = {}) {
    this.shape(VectorPath().apply(path))
}


fun Graphics.startFill(color: RGBA) {
    this.beginFill(Context2d.Color(color))
}

fun BitmapFont.startFill(color: RGBA) {
    //TODO: implement seeting color
}


fun Graphics.triangle(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double) {
    this.moveTo(x1, y1)
    this.lineTo(x2, y2)
    this.lineTo(x3, y3)
    this.lineTo(x1, y1)
}