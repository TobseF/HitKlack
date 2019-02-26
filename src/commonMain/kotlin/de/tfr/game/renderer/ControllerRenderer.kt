package de.tfr.game.renderer

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.image
import com.soywiz.korge.view.position
import com.soywiz.korge.view.tiles.TileSet
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.BitmapSlice
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Point
import de.tfr.game.Controller
import de.tfr.game.Controller.Control
import de.tfr.game.lib.engine.Loadable


class ControllerRenderer(val controller: Controller) : Loadable {

    private lateinit var buttons: TileSet
    private lateinit var red: Button
    private lateinit var blue: Button
    private lateinit var yellow: Button
    private lateinit var green: Button
    private var width = 120

    private class Button(val touchArea: Controller.TouchArea, val normal: BmpSlice?, val pressed: BmpSlice?) {

        fun create(container: Container): Button {
            container.image(normal!!) {
                position(touchArea.rect.x, touchArea.rect.y)
            }
            return this
        }

    }

    override suspend fun create(container: Container) {
        val texture = container.image(resourcesVfs["buttons.png"].readBitmap()) {
            visible = false
        }

        buttons = TileSet.invoke(texture.texture as BitmapSlice<Bitmap>, 120, 120)

        green = Button(controller.top, buttons[0], buttons[1]).create(container)
        blue = Button(controller.right, buttons[2], buttons[3]).create(container)
        yellow = Button(controller.bottom, buttons[4], buttons[5]).create(container)
        red = Button(controller.left, buttons[6], buttons[7]).create(container)
    }


    fun render(controller: Controller) {

        val radius = width / 2F
        fun draw(textureRegion: BmpSlice?, touchArea: Controller.TouchArea) {
            val pos = touchArea.rect.center().sub(Point(radius, radius))
            //TODO:  graphics.draw(textureRegion, pos.x, pos.y)
        }

        fun button(button: Button, control: Control): BmpSlice? {
            return if (controller.isPressed(control)) button.pressed else button.normal
        }


        draw(button(red, Control.Left), controller.left)
        draw(button(blue, Control.Right), controller.right)
        draw(button(yellow, Control.Bottom), controller.bottom)
        draw(button(green, Control.Top), controller.top)

    }

}
