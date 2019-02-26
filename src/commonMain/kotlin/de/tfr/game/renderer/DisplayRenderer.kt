package de.tfr.game.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.view.*
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
import de.tfr.game.util.extensions.text


private val log = Logger("DisplayRenderer")

class DisplayRenderer(val display: Display) : Loadable {

    lateinit var font: BitmapFont
    var text: Text? = null

    override suspend fun create(container: Container) {
        font = resourcesVfs["fonts/segment7.fnt"].readBitmapFont()
    }

    fun Graphics.timeText(text: String, color: RGBA = Colors.WHITE): Text {
        return this.text(text, display.x - display.width / 2 + 5, display.y + 506, font, 92.0, color)
    }

    var init = false
    fun render(renderer: Graphics) {
        if (!init) {
            init = true

            renderer.solidRect(display.width, display.height, GREEN_LIGHT) {
                position(display.x - display.width / 2, display.y + 500)
            }

            renderer.text("88:88", font = font)

            renderer.timeText("88:88", GREEN_LIGHT2)

            text = renderer.timeText(display.getText(), GRAY_DARK)

        } else {
            val newTime = display.getText()
            log.info { newTime }
            text?.setText(newTime)
        }
    }

}
