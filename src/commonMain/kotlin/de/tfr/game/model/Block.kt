package de.tfr.game.model

import com.soywiz.korge.view.Graphics

class Block(val ring: Ring, val orientation: Orientation) {
    val row = ring.index
    var image: Graphics? = null

    enum class State { Full, Empty }

    var active = false
        set(value) {
            field = value
            if (value) {
                image?.visible = true
                image?.alpha = 0.95
            } else {
                image?.alpha = 0.7
                image?.visible = state == State.Full
            }
        }

    var state = State.Empty
        set(value) {
            field = value
            when (value) {
                State.Full -> image?.visible = true
                State.Empty -> image?.visible = false
            }
        }

    fun setFull() {
        state = State.Full
    }

    fun isEmpty() = state == State.Empty

    fun isTaken() = state == State.Full

    override fun toString() = "Block [$ring ${orientation.char()} $state]"

    fun reset() {
        active = false
        state = State.Empty
    }
}