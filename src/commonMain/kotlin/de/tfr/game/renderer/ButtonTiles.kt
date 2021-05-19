package de.tfr.game.renderer

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.image
import com.soywiz.korge.view.tiles.TileSet
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.BitmapSlice
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs


class ButtonTiles {

    enum class Style { Green, Blue, Yellow, Red }

    lateinit var buttons: TileSet

    suspend fun init(container: Container) = apply {
        val texture = container.image(resourcesVfs["images/buttons.png"].readBitmap()) {
            visible = false
        }
        buttons = TileSet(texture.bitmap as BitmapSlice<Bitmap>, 120, 120)
    }

    fun get(style: Style) = when (style) {
        Style.Green -> ButtonImage(buttons[0]!!, buttons[1]!!)
        Style.Blue -> ButtonImage(buttons[2]!!, buttons[3]!!)
        Style.Yellow -> ButtonImage(buttons[4]!!, buttons[5]!!)
        Style.Red -> ButtonImage(buttons[6]!!, buttons[7]!!)
    }

    class ButtonImage(val normal: BmpSlice, val pressed: BmpSlice)

}