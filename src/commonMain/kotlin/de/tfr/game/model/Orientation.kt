package de.tfr.game.model

import kotlin.random.Random


enum class Orientation {
    Left, Right, Up, Down;

    fun char() = this.name.first()

    companion object {

        fun random() = values()[Random.nextInt(values().size)]
    }
}