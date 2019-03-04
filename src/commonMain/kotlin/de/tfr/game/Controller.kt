package de.tfr.game

import com.soywiz.kmem.isAlmostZero
import com.soywiz.korev.Key
import com.soywiz.korev.KeyEvent
import com.soywiz.korev.TouchEvent
import com.soywiz.korge.component.KeyComponent
import com.soywiz.korge.component.TouchComponent
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korge.view.Views
import com.soywiz.korma.geom.length
import de.tfr.game.Controller.Control.*
import de.tfr.game.lib.actor.Point
import de.tfr.game.lib.engine.Loadable
import de.tfr.game.libgx.emu.Input
import de.tfr.game.libgx.emu.Viewport
import de.tfr.game.renderer.ButtonTiles
import de.tfr.game.renderer.ButtonTiles.Style
import de.tfr.game.ui.Button


class Controller(point: Point,
        val gameRadius: Double,
        val viewport: Viewport,
        override val view: View) : TouchComponent, KeyComponent, Point by point, Loadable {

    override suspend fun create(container: Container) {
        val tiles = ButtonTiles().apply { create(container) }

        top = Button(Top, x, y - gameRadius - distance, tiles.get(Style.Green), view).create(container)
        right = Button(Right, x + gameRadius + distance, y, tiles.get(Style.Blue), view).create(container)
        bottom = Button(Bottom, x, y + gameRadius + distance, tiles.get(Style.Yellow), view).create(container)
        left = Button(Left, x - gameRadius - distance, y, tiles.get(Style.Red), view).create(container)
    }

    lateinit var left: Button
    lateinit var right: Button
    lateinit var top: Button
    lateinit var bottom: Button

    var lastTouch: List<com.soywiz.korma.geom.Point> = emptyList()

    private val distance = 90f
    private val vibrateTime = 26

    private val touchListeners: MutableCollection<ControlListener> = ArrayList()

    enum class Control { Left, Right, Top, Bottom, Esc, Action, Pause }

    interface ControlListener {
        fun controlEvent(control: Control)
    }

    fun isPressed(control: Control): Boolean {
        val touchPointers = getTouchPointers()
        fun touches(touchArea: Button) = touchPointers.any(touchArea::contains)

        return when (control) {
            Left -> touches(left)
            Right -> touches(right)
            Top -> touches(top)
            Bottom -> touches(bottom)
            else -> false
        }
    }

    private fun getTouchPointers() = lastTouch.map { viewport.unproject(it) }.filter { !it.length.isAlmostZero() }

    val touchAreas: List<Button> by lazy {
        arrayListOf(left, right, top, bottom)
    }

    override fun onTouchEvent(views: Views, e: TouchEvent) {
        lastTouch = e.touches.map { it.current }
        lastTouch.forEach { point ->
            val worldCords = viewport.unproject(point)

            touchAreas.filter { it.contains(worldCords) }.forEach {
                doHapticFeedback()
                notifyListener(it.control)
            }
        }
    }

    override fun onKeyEvent(views: Views, event: KeyEvent) {
        event.key.toControl()?.let(this::notifyListener)
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
