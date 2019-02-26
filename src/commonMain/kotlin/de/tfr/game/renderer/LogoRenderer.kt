package de.tfr.game.renderer

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.image
import com.soywiz.korge.view.position
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import de.tfr.game.lib.actor.Point
import de.tfr.game.lib.engine.Loadable

class LogoRenderer(val point: Point, val gameFieldSize: Double) : Point by point, Loadable {

    override suspend fun create(container: Container) {
        container.image(resourcesVfs["images/hitclack_logo.png"].readBitmap()) {
            position(point.x - width / 2, point.y - gameFieldSize - 390)
        }
    }
}