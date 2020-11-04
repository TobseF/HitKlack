package de.tfr.game.ui

import com.soywiz.korev.KeyEvent
import com.soywiz.korge.input.keys
import com.soywiz.korge.input.onDown
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
        image.onDown { }
        image.onUp { setUp() }
        image.onDown { setDown() }
        image.keys {
            down {
                ifControlPressed(it) { setDown() }
            }
            up {
                ifControlPressed(it) { setUp() }
            }
        }
        return this
    }

    private fun setDown() {
        clickListener?.invoke()
        image.bitmap = style.pressed
    }

    private fun setUp() {
        image.bitmap = style.normal
    }

    private fun ifControlPressed(e: KeyEvent, action: () -> Any) {
        if (e.key.toControl() == control) {
            action.invoke()
        }
    }

}