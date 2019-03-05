package de.tfr.game.ui

import com.soywiz.korev.KeyEvent
import com.soywiz.korge.input.onDown
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.input.onKeyUp
import com.soywiz.korge.input.onUp
import com.soywiz.korge.view.*
import de.tfr.game.Controller
import de.tfr.game.lib.actor.Point
import de.tfr.game.lib.actor.Point2D
import de.tfr.game.renderer.ButtonTiles
import de.tfr.game.toControl

class Button(val control: Controller.Control,
        center: Point2D,
        private val style: ButtonTiles.ButtonImage,
        val view: View) : Point by center {

    var clickListener: (() -> Any)? = null

    private lateinit var image: Image

    fun create(container: Container): Button {
        val posX = x
        val posY = y
        image = container.image(style.normal) {
            position(posX, posY)
            anchor(.5, .5)
        }

        image.onKeyDown { ifControlPressed(it) { setDown() } }
        image.onKeyUp { ifControlPressed(it) { setUp() } }
        image.onUp { setUp() }
        image.onDown { setDown() }
        return this
    }

    private fun setDown() {
        clickListener?.invoke()
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