package de.tfr.game.util.extensions

import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.BitmapFont

inline fun Container.text(text: String,
        textSize: Double = 16.0,
        font: BitmapFont = Fonts.defaultFont,
        color: RGBA = Colors.WHITE,
        callback: @ViewsDslMarker Text.() -> Unit = {}) = Text(text,
        textSize = textSize,
        font = font,
        color = color).addTo(this).apply(callback)