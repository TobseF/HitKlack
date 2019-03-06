package de.tfr.game.model

import kotlin.random.Random


class Ring(val index: Int) : Iterable<Block> {

    private val sides = 4

    override fun iterator() = blocks.iterator()

    var blocks: Array<Block> = Array(sides) { i -> Block(this, Orientation.values()[i]) }

    operator fun get(orientation: Orientation) = blocks[orientation.ordinal]

    fun reset() = blocks.forEach { it.reset() }

    fun size() = blocks.filter { it.isTaken() }.count()

    fun isEmpty() = size() == 0

    fun isFull() = size() == sides

    fun freeSides(): List<Orientation> {
        return blocks.filter { it.isEmpty() }.map { it.orientation }.toList()
    }

    fun randomFreeSide(): Orientation? {
        val freeSides = freeSides()
        if (freeSides.isEmpty()) {
            return null
        }
        return freeSides[Random.nextInt(freeSides.size)]
    }

    override fun toString(): String {
        return "Ring:$index {${blocks.map {
            it.orientation.char() + (if (it.isEmpty()) "[_]" else "[X]") + if (it.active) "A" else ""
        }.joinToString(
                ",")}}" + if (isFull()) " - (full)" else ""
    }

}