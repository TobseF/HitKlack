package de.tfr.game.model


class Block(val row: Int, val orientation: Orientation) {
    var stone: Stone? = null

    fun isEmpty(): Boolean {
        return stone == null
    }

    fun isTaken(): Boolean {
        return !isEmpty()
    }

    override fun toString() = "Block [$row ${orientation.char()} $stone]"

    fun reset() {
        stone = null
    }
}