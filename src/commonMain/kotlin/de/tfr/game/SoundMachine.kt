package de.tfr.game

import com.soywiz.korau.sound.NativeSound
import com.soywiz.korau.sound.readNativeSound
import com.soywiz.korau.sound.readSound
import com.soywiz.korge.view.Container
import com.soywiz.korio.file.std.resourcesVfs
import de.tfr.game.lib.engine.Loadable
import debug


class SoundMachine : Loadable {


    private var circle_ok: NativeSound? = null
    private var line_missed: NativeSound? = null
    private var line_ok: NativeSound? = null

    private suspend fun newSound(fileName: String) = resourcesVfs["sounds/$fileName"].readSound()

    override suspend fun create(container: Container) {
        circle_ok = newSound("circle_ok.mp3")
        line_missed = newSound("line_missed.mp3")
        line_ok = newSound("line_ok.mp3")
    }

    fun playCircleOK() {
        if (!debug) {
            circle_ok?.play()
        }
    }

    fun playLineMissed() {
        if (!debug) {
            line_missed?.play()
        }
    }

    fun playLineOK() {
        if (!debug) {
            line_ok?.play()
        }
    }

}