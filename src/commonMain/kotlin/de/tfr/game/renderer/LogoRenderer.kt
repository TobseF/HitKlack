package de.tfr.game.renderer

import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import de.tfr.game.lib.actor.Point
import de.tfr.game.lib.engine.Loadable

/**
 * Created by tobse on 24.12.2016.
 */
class LogoRenderer(point: Point, val gameFieldSize: Double) : Point by point, Loadable {
    lateinit var logo: Bitmap

    override suspend fun create() {
        logo = resourcesVfs["images/hitclack_logo.png"].readBitmap()
    }

    fun render() {
        //TODO: logo.draw( x - 200, y + gameFieldSize + 250f)
    }
}