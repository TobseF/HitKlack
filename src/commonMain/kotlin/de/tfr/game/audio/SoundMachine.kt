package de.tfr.game.audio

import com.soywiz.korau.sound.Sound
import com.soywiz.korau.sound.readSound
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Views
import com.soywiz.korio.async.launch
import com.soywiz.korio.file.std.resourcesVfs
import de.tfr.game.lib.engine.Loadable
import disableSound


class SoundMachine(val view: Views) : Loadable, ISoundMachine {

    private var circleOk: Sound? = null
    private var lineMissed: Sound? = null
    private var lineOk: Sound? = null

    private suspend fun newSound(fileName: String) = resourcesVfs["sounds/$fileName"].readSound()

    override suspend fun create(container: Container) {
        circleOk = newSound("circle_ok.mp3")
        lineMissed = newSound("line_missed.mp3")
        lineOk = newSound("line_ok.mp3")
    }

    override fun playCircleOK() {
        if (!disableSound) {
            view.launch {
                circleOk?.play()
            }
        }
    }

    override fun playLineMissed() {
        if (!disableSound) {
            view.launch {
                lineMissed?.play()
            }
        }
    }

    override fun playLineOK() {
        if (!disableSound) {
            view.launch {
                lineOk?.play()
            }
        }
    }

}