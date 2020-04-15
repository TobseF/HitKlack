package de.tfr.game.util.extensions

import com.soywiz.korge.view.Fonts
import com.soywiz.korge.view.Graphics
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.position
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.vector.Context2d
import com.soywiz.korim.vector.paint.ColorPaint
import com.soywiz.korma.geom.vector.VectorPath
import com.soywiz.korma.geom.vector.circle

fun Graphics.circle(x: Number, y: Number, radius: Number) {
    this.path { circle(x, y, radius) }
}

fun Graphics.path(path: (VectorPath) -> Unit = {}) {
    this.shape(VectorPath().apply(path))
}

fun Graphics.startFill(color: RGBA) {
    this.beginFill(ColorPaint(color))
}

fun Graphics.drawFill(color: RGBA, renderAction: (Graphics) -> Any) {
    this.startFill(color)
    renderAction.invoke(this)
    this.endFill()
}

fun Graphics.text(text: String,
        x: Number,
        y: Number,
        font: BitmapFont = Fonts.defaultFont,
        textSize: Double,
        color: RGBA = Colors.WHITE): Text {
    return text(text, textSize, font = font, color = color) {
        position(x, y)
    }
}

fun Graphics.triangle(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double) {
    this.moveTo(x1, y1)
    this.lineTo(x2, y2)
    this.lineTo(x3, y3)
    this.lineTo(x1, y1)
}