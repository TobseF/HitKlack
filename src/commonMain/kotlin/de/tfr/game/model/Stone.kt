package de.tfr.game.model


class Stone(var block: Block) {

    enum class State { Active, Set }

    var state = State.Active

    fun freeze() {
        state = State.Set
    }

    override fun toString(): String {
        return "Stone[$state]"
    }
}