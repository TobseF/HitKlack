package de.tfr.game

import com.soywiz.korau.format.AudioFormats
import com.soywiz.korau.format.registerStandard
import com.soywiz.korau.sound.AudioData
import com.soywiz.korau.sound.playAndWait
import com.soywiz.korau.sound.readAudioData
import com.soywiz.korio.file.std.resourcesVfs


class SoundMachine {

    private lateinit var circle_ok: AudioData
    private lateinit var line_missed: AudioData
    private lateinit var line_ok: AudioData

    val formats = AudioFormats().registerStandard()

    suspend fun init() {
        circle_ok = newSound("circle_ok.ogg")
        line_missed = newSound("line_missed.ogg")
        line_ok = newSound("line_ok.ogg")
    }

    private suspend fun newSound(fileName: String) = resourcesVfs["sounds/" + fileName].readAudioData(formats)

    fun playCircleOK() {
        //TODO: call playAndWaitCircleOK()
    }

    fun playLineMissed() {
        //TODO: call playAndWaitLineMissed()
    }

    fun playLineOK() {
        //TODO: call playAndWaitLineOK()
    }


    suspend fun playAndWaitCircleOK() = circle_ok.playAndWait()
    suspend fun playAndWaitLineMissed() = line_missed.playAndWait()
    suspend fun playAndWaitLineOK() = line_ok.playAndWait()
}