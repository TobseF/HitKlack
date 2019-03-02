package de.tfr.game.renderer

import com.soywiz.korge.view.Graphics
import de.tfr.game.util.extensions.triangle

fun Graphics.triangleLeftUp(size: Double, x: Double, y: Double) = this.triangle(x, y, x, y + size, x + size, y)
fun Graphics.triangleLeftDown(size: Double, x: Double, y: Double) = this.triangle(x, y, x, y - size, x + size, y)
fun Graphics.triangleRightUp(size: Double, x: Double, y: Double) = this.triangle(x, y, x + size, y + size, x + size, y)

fun Graphics.triangleRightDown(size: Double, x: Double, y: Double) = this.triangle(x,
        y,
        x + size,
        y - size,
        x + size,
        y)

fun Graphics.triangleDownLeft(size: Double, x: Double, y: Double) = this.triangle(x, y, x, y + size, x - size, y + size)

fun Graphics.triangleDownRight(size: Double, x: Double, y: Double) = this.triangle(x,
        y,
        x,
        y + size,
        x + size,
        y + size)

fun Graphics.triangleUpLeft(size: Double, x: Double, y: Double) = this.triangle(x, y, x, y + size, x - size, y)
fun Graphics.triangleUpRight(size: Double, x: Double, y: Double) = this.triangle(x, y, x, y + size, x + size, y)