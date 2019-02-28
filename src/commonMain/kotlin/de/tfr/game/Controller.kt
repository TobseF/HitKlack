package de.tfr.game

import com.soywiz.kmem.isAlmostZero
import com.soywiz.korev.Key
import com.soywiz.korev.KeyEvent
import com.soywiz.korev.TouchEvent
import com.soywiz.korge.component.KeyComponent
import com.soywiz.korge.component.TouchComponent
import com.soywiz.korge.view.View
import com.soywiz.korge.view.Views
import com.soywiz.korma.geom.length
import de.tfr.game.Controller.Control.*
import de.tfr.game.lib.actor.Point
import de.tfr.game.libgx.emu.Input
import de.tfr.game.libgx.emu.Rectangle
import de.tfr.game.libgx.emu.Viewport


class Controller(point: Point, gameRadius: Double, val viewport: Viewport, override val view: View) : TouchComponent,
        KeyComponent,
        Point by point {

    val left: TouchArea
    val right: TouchArea
    val top: TouchArea
    val bottom: TouchArea

    var lastTouch: List<com.soywiz.korma.geom.Point> = emptyList()

    private val distance = 90f
    private val radius = 62.0
    private val vibrateTime = 26

    private val touchListeners: MutableCollection<ControlListener> = ArrayList()

    enum class Control { Left, Right, Top, Bottom, Esc, Action, Pause }
    private class Button(centerX: Double, centerY: Double, radius: Double) : Rectangle(centerX - radius,
            centerY - radius,
            radius * 2,
            radius * 2)

    class TouchArea(val control: Control, val rect: Rectangle)

    interface ControlListener {
        fun controlEvent(control: Control)
    }

    fun isPressed(control: Control): Boolean {
        val touchPointers = getTouchPointers()
        fun touches(touchArea: TouchArea) = touchPointers.any(touchArea.rect::contains)
        return when (control) {
            Left -> touches(left)
            Right -> touches(right)
            Top -> touches(top)
            Bottom -> touches(bottom)
            else -> false
        }
    }

    private fun getTouchPointers() = lastTouch.map { viewport.unproject(it) }.filter { !it.length.isAlmostZero() }

    init {
        left = TouchArea(Left, Button(x - gameRadius - distance, y, radius))
        right = TouchArea(Right, Button(x + gameRadius + distance, y, radius))
        top = TouchArea(Top, Button(x, y + gameRadius + distance, radius))
        bottom = TouchArea(Bottom, Button(x, y - gameRadius - distance, radius))
    }

    val touchAreas: List<TouchArea> by lazy {
        arrayListOf(left, right, top, bottom)
    }

    override fun onTouchEvent(views: Views, e: TouchEvent) {
        lastTouch = e.touches.map { it.current }
        lastTouch.forEach { point ->
            val worldCords = viewport.unproject(point)

            touchAreas.filter { it.rect.contains(worldCords) }.forEach {
                doHapticFeedback()
                notifyListener(it.control)
            }
        }
    }

    override fun onKeyEvent(views: Views, event: KeyEvent) {
        fun toControl(keycode: Key) = when (keycode) {
            Key.RIGHT -> Right
            Key.UP -> Top
            Key.DOWN -> Bottom
            Key.LEFT -> Left
            Key.SPACE -> Action
            Key.P -> Pause
            Key.ESCAPE -> Esc
            else -> null
        }
        toControl(event.key)?.let(this::notifyListener)
        doHapticFeedback()
    }


    private fun doHapticFeedback() = Input.vibrate(vibrateTime)

    fun addTouchListener(touchListener: ControlListener) = touchListeners.add(touchListener)

    private fun notifyListener(control: Control) = touchListeners.forEach { it.controlEvent(control) }

}