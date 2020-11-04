package de.tfr.game.util.extensions

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.addTo
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.Font
import com.soywiz.korim.font.SystemFontRegistry

inline fun Container.text(text: String,
        textSize: Double = 16.0,
        font: Font = SystemFontRegistry.DEFAULT_FONT,
        color: RGBA = Colors.WHITE,
        callback: Text.() -> Unit = {}) = Text(
        text, textSize = textSize, font = font, color = color).addTo(this).apply(callback)