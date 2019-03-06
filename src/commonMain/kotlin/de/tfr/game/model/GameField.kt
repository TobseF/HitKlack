package de.tfr.game.model


class GameField(var size: Int) : Iterable<Ring> {

    override fun iterator() = rings.iterator()

    private val rings: Array<Ring> = Array(size, ::Ring)

    operator fun get(index: Int) = rings[index]

    fun reset() = rings.forEach { it.reset() }


    override fun toString() = rings.joinToString("\n")

    fun setActive() = forEach { ring -> ring.forEach { block -> block.active = true } }

}