package de.tfr.game.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.position
import com.soywiz.korge.view.solidRect
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


class DisplayRenderer(private val display: Display) : Loadable {

    private val log = Logger<DisplayRenderer>()
    lateinit var font: BitmapFont
    var text: Text? = null

    override suspend fun create(container: Container) {
        font = resourcesVfs["fonts/segment7.fnt"].readBitmapFont()

        container.solidRect(display.width, display.height, GREEN_LIGHT) {
            position(display.borderLeftX(), display.y + 500)
        }

        container.timeText("00:22", GREEN_LIGHT2)

        text = container.timeText(display.getText(), GRAY_DARK)
    }

    private fun Container.timeText(text: String, color: RGBA = Colors.WHITE): Text {
        return this.text(text, display.borderLeftX() + 5, display.y + 506, font, 92.0, color)
    }

    fun render() {
        val newTime = display.getText()
        log.trace { newTime }
        text?.text = newTime
    }

}
