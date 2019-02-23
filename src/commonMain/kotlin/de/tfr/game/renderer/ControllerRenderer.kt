package de.tfr.game.renderer

import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Point
import de.tfr.game.Controller
import de.tfr.game.Controller.Control
import de.tfr.game.lib.engine.Loadable


class ControllerRenderer : Loadable {

    private lateinit var buttons: SpriteSheet
    private lateinit var red: ButtonTexture
    private lateinit var blue: ButtonTexture
    private lateinit var yellow: ButtonTexture
    private lateinit var green: ButtonTexture
    private var width = 120

    private class ButtonTexture(val normal: TextureRegion, val pressed: TextureRegion)

    init {

    }

    override suspend fun create() {
        val texture = resourcesVfs["buttons.png"].readBitmap()

        buttons = SpriteSheet(texture, width, width, 2, 4)
        green = ButtonTexture(buttons[0], buttons[1])
        blue = ButtonTexture(buttons[2], buttons[3])
        yellow = ButtonTexture(buttons[4], buttons[5])
        red = ButtonTexture(buttons[6], buttons[7])
    }


    fun render(controller: Controller) {

        val radius = width / 2F
        fun draw(textureRegion: TextureRegion, touchArea: Controller.TouchArea) {
            val pos = touchArea.rect.center().sub(Point(radius, radius))
            //TODO:  graphics.draw(textureRegion, pos.x, pos.y)
        }

        fun button(button: ButtonTexture, control: Control): TextureRegion {
            return if (controller.isPressed(control)) button.pressed else button.normal
        }


        draw(button(red, Control.Left), controller.left)
        draw(button(blue, Control.Right), controller.right)
        draw(button(yellow, Control.Bottom), controller.bottom)
        draw(button(green, Control.Top), controller.top)

    }

    class SpriteSheet(val texture: Bitmap,
            val width: Int,
            val height: Int,
            val horizontalCount: Int,
            val verticalCount: Int) {
        private val regions: Array<TextureRegion> = Array(numTiles(), this::getTile)

        fun numTiles() = horizontalCount * verticalCount

        operator fun get(index: Int) = regions[index]

        fun getTile(index: Int): TextureRegion {
            val y = if (index > 0) index / horizontalCount else 0
            val x = index - (y * horizontalCount)

            return getTile(x, y)
        }


        private fun getTile(x: Int, y: Int) = TextureRegion(texture, x * width, y * height, width, height)
    }
}

class TextureRegion(texture: Bitmap, i: Int, i1: Int, width: Int, height: Int)
