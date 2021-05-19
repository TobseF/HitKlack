package de.tfr.game

import com.soywiz.korau.sound.Sound
import com.soywiz.korau.sound.readSound
import com.soywiz.korge.view.Stage
import com.soywiz.korio.async.launch
import com.soywiz.korio.file.std.resourcesVfs
import disableSound


class SoundMachine(private val stage: Stage? = null) {

    private var circleOk: Sound? = null
    private var lineMissed: Sound? = null
    private var lineOk: Sound? = null

    suspend fun init() = apply { loadSounds() }

    private suspend fun readSound(fileName: String) = resourcesVfs["sounds/$fileName"].readSound()

    private suspend fun loadSounds() {
        circleOk = readSound("circle_ok.mp3")
        lineMissed = readSound("line_missed.mp3")
        lineOk = readSound("line_ok.mp3")
    }

    fun playCircleOK() = circleOk.play()

    fun playLineMissed() = lineMissed.play()

    fun playLineOK() = lineOk.play()

    private fun Sound?.play() {
        if (!disableSound) {
            stage?.launch {
                this?.play()
            }
        }
    }


}