package de.tfr.game.audio

import de.tfr.game.lib.engine.Loadable


interface ISoundMachine : Loadable {


    fun playCircleOK()

    fun playLineMissed()

    fun playLineOK()
}