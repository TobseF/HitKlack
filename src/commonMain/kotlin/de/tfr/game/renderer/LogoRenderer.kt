package de.tfr.game.renderer

import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.degrees
import de.tfr.game.lib.actor.Point
import de.tfr.game.lib.engine.Loadable
import resolution

class LogoRenderer(val point: Point, val gameFieldSize: Double) : Point by point, Loadable {

    override suspend fun create(container: Container) {
        container.image(resourcesVfs["images/hitclack_logo.png"].readBitmap()) {
            position(point.x - width / 2, point.y - gameFieldSize - 390)
        }
        container.image(resourcesVfs["images/korge_logo.png"].readBitmap()) {
            rotation = (+16).degrees
            anchor(.5, .5)
            scale(.2)
            position(resolution.width - 70, 70)
        }
    }
}