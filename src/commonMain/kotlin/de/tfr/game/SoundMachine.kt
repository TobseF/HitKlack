package de.tfr.game

import com.soywiz.korau.sound.*
import com.soywiz.korio.file.std.resourcesVfs


class SoundMachine {

    private lateinit var circle_ok: NativeSound
    private lateinit var line_missed: NativeSound
    private lateinit var line_ok: NativeSound

    suspend fun init() {
        circle_ok = newSound("circle_ok.ogg")
        line_missed = newSound("line_missed.ogg")
        line_ok = newSound("line_ok.ogg")
    }

    private suspend fun newSound(fileName: String) = resourcesVfs["sounds/" + fileName].readNativeSound()

    fun playCircleOK() {
        //TODO: call playAndWaitCircleOK()
        println("playCircleOK")
        circle_ok.play()
    }

    fun playLineMissed() {
        //TODO: call playAndWaitLineMissed()
        println("playLineMissed")
        line_missed.play()
    }

    fun playLineOK() {
        //TODO: call playAndWaitLineOK()
        println("playLineOK")
        line_ok.play()
    }


    suspend fun playAndWaitCircleOK() = circle_ok.playAndWait()
    suspend fun playAndWaitLineMissed() = line_missed.playAndWait()
    suspend fun playAndWaitLineOK() = line_ok.playAndWait()
}