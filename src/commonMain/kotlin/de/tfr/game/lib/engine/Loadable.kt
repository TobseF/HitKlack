package de.tfr.game.lib.engine

import com.soywiz.korge.view.Container

interface Loadable {
    suspend fun create(container: Container)
}