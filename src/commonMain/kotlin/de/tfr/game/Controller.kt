package de.tfr.game

import com.soywiz.korev.Key
import com.soywiz.korev.KeyEvent
import com.soywiz.korge.component.KeyComponent
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korge.view.Views
import de.tfr.game.Controller.Control.*
import de.tfr.game.lib.actor.Point
import de.tfr.game.lib.actor.Point2D
import de.tfr.game.lib.engine.Loadable
import de.tfr.game.libgx.emu.Input
import de.tfr.game.renderer.ButtonTiles
import de.tfr.game.renderer.ButtonTiles.Style
import de.tfr.game.ui.Button


class Controller(point: Point, private val gameRadius: Double, override val view: View) : KeyComponent,
        Point by point,
        Loadable {

    override suspend fun create(container: Container) {
        val tiles = ButtonTiles().apply { create(container) }
        val shift = gameRadius + distance
        val center = Point2D(this)
        top = container.addButton(Button(Top, center.shiftTop(shift), tiles.get(Style.Green), view))
        right = container.addButton(Button(Right, center.shiftRight(shift), tiles.get(Style.Blue), view))
        bottom = container.addButton(Button(Bottom, center.shiftBottom(shift), tiles.get(Style.Yellow), view))
        left = container.addButton(Button(Left, center.shiftLeft(shift), tiles.get(Style.Red), view))
    }

    private fun Container.addButton(button: Button): Button {
        button.create(this)
        button.clickListener = { notifyListener(button.control) }
        return button
    }

    lateinit var left: Button
    lateinit var right: Button
    lateinit var top: Button
    lateinit var bottom: Button

    private val distance = 90f
    private val vibrateTime = 26

    private val touchListeners: MutableCollection<ControlListener> = ArrayList()

    enum class Control { Left, Right, Top, Bottom, Esc, Action, Pause }

    interface ControlListener {
        fun controlEvent(control: Control)
    }


    override fun Views.onKeyEvent(event: KeyEvent) {
        if (event.type == KeyEvent.Type.DOWN) {
            event.key.toControl()?.let(::notifyListener)
        }
        doHapticFeedback()
    }

    private fun doHapticFeedback() = Input.vibrate(vibrateTime)

    fun addTouchListener(touchListener: ControlListener) = touchListeners.add(touchListener)

    private fun notifyListener(control: Control) = touchListeners.forEach { it.controlEvent(control) }

}

fun Key.toControl() = when (this) {
    Key.RIGHT -> Right
    Key.UP -> Top
    Key.DOWN -> Bottom
    Key.LEFT -> Left
    Key.SPACE -> Action
    Key.P -> Pause
    Key.ESCAPE -> Esc
    else -> null
}
