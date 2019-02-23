package de.tfr.game.util


class Timer(var actionTime: Double, val timerAction: () -> Unit) : Time {

    override var time = 0.0
    private var pause = false

    fun update(deltaTime: Double) {
        time += deltaTime
        if (!pause && time >= actionTime) {
            time = 0.0
            timerAction.invoke()
        }
    }

    fun togglePause() {
        pause = !pause
    }

    fun reset() {
        time = 0.0
    }

}
