package de.tfr.game.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.view.Graphics
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.setText
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korio.file.std.resourcesVfs
import de.tfr.game.Display
import de.tfr.game.lib.engine.Loadable
import de.tfr.game.ui.GRAY_DARK
import de.tfr.game.ui.GREEN_LIGHT
import de.tfr.game.ui.GREEN_LIGHT2
import de.tfr.game.util.extensions.startFill
import de.tfr.game.util.extensions.text


private val log = Logger("DisplayRenderer")

class DisplayRenderer(val display: Display) : Loadable {
    lateinit var font: BitmapFont


    override suspend fun create() {
        font = resourcesVfs["fonts/segment7.fnt"].readBitmapFont()
    }

    var text: Text? = null


    fun Graphics.text(text: String, x: Number, y: Number, color: RGBA = Colors.WHITE): Text {
        return this.text(text, x, y, font, 100.0, color)
    }

    var init = false
    fun render(renderer: Graphics) {
        if (!init) {
            init = true

            renderer.startFill(GREEN_LIGHT)
            // renderer.rect(display.x - display.width / 2, display.y - 510, display.width, display.height)
            renderer.endFill()

            renderer.text("88:88", font = font)

            renderer.text("88:88", display.x - display.width / 2, display.y - 510, GREEN_LIGHT2)

            text = renderer.text(display.getText(), display.x - display.width / 2, display.y - 510, GRAY_DARK)

        } else {
            val newTime = display.getText()
            log.info { newTime }
            text?.setText(newTime)
        }
    }

}
