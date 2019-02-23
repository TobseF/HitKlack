package de.tfr.game.util

import com.soywiz.korim.color.RGBA


fun rgbColor(r: Int, g: Int, b: Int): RGBA {
    return RGBA.float(r / 255f, g / 255f, b / 255f, 1f)
}
