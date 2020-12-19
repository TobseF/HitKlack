package de.tfr.game

import com.soywiz.korau.sound.Sound
import com.soywiz.korau.sound.readSound
import com.soywiz.korge.view.Container
import com.soywiz.korio.file.std.resourcesVfs
import de.tfr.game.lib.engine.Loadable
import disableSound


class SoundMachine : Loadable {

    private var circleOk: Sound? = null
    private var lineMissed: Sound? = null
    private var lineOk: Sound? = null

    private suspend fun newSound(fileName: String) = resourcesVfs["sounds/$fileName"].readSound()

    override suspend fun create(container: Container) {
        circleOk = newSound("circle_ok.mp3")
        lineMissed = newSound("line_missed.mp3")
        lineOk = newSound("line_ok.mp3")
    }

    fun playCircleOK() {
        if (!disableSound) {
            circleOk?.play()
        }
    }

    fun playLineMissed() {
        if (!disableSound) {
            lineMissed?.play()
        }
    }

    fun playLineOK() {
        if (!disableSound) {
            lineOk?.play()
        }
    }

}