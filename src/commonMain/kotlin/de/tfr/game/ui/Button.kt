package de.tfr.game.ui

import com.soywiz.korev.KeyEvent
import com.soywiz.korge.input.onDown
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.input.onKeyUp
import com.soywiz.korge.input.onUp
import com.soywiz.korge.view.*
import de.tfr.game.Controller
import de.tfr.game.libgx.emu.Rectangle
import de.tfr.game.renderer.ButtonTiles
import de.tfr.game.toControl

class Button(val control: Controller.Control,
        centerX: Double,
        centerY: Double,
        private val style: ButtonTiles.ButtonImage,
        val view: View,
        radius: Double = 62.0) : Rectangle(centerX - radius, centerY - radius, radius * 2, radius * 2) {


    private lateinit var image: Image

    fun create(container: Container): Button {
        val posX = x
        val posY = y
        image = container.image(style.normal) {
            position(posX, posY)
        }

        image.onKeyDown { ifControlPressed(it) { setDown() } }
        image.onKeyUp { ifControlPressed(it) { setUp() } }
        image.onUp { setUp() }
        image.onDown { setDown() }
        return this
    }

    private fun setDown() {
        image.texture = style.pressed
    }

    private fun setUp() {
        image.texture = style.normal
    }

    private fun ifControlPressed(e: KeyEvent, action: () -> Any) {
        if (e.key.toControl() == control) {
            action.invoke()
        }
    }

}