package de.tfr.game.renderer

import com.soywiz.klogger.Logger
import com.soywiz.korge.view.Graphics
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.setText
import com.soywiz.korge.view.text
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korim.font.readBitmapFont
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Matrix
import de.tfr.game.Display
import de.tfr.game.lib.engine.Loadable
import de.tfr.game.ui.GRAY_DARK
import de.tfr.game.ui.GREEN_LIGHT
import de.tfr.game.ui.GREEN_LIGHT2
import de.tfr.game.util.extensions.startFill


private val log = Logger("DisplayRenderer")

class DisplayRenderer(val display: Display) : Loadable {
    lateinit var font: BitmapFont


    override suspend fun create() {
        font = resourcesVfs["fonts/segment7.fnt"].readBitmapFont()
    }

    var text: Text? = null

    fun Graphics.text(text: String, x: Number, y: Number): Text {
        return text(text, 120.0, font = font) {
            setTransform(Matrix.Transform(x, y))
        }
    }

    var init = false
    fun render(renderer: Graphics) {
        if (!init) {
            init = true
            renderer.startFill(GREEN_LIGHT)

            //renderer.rect(display.x - display.width / 2, display.y - 510, display.width, display.height)
            renderer.endFill()

            renderer.text("88:88", font = font)
            renderer.endFill()
            font.startFill(GRAY_DARK)

            val width = display.width// font.base * 5
            val height = display.height//font.lineHeight

            //renderer.text("88:88", display.x - display.width / 2, display.y - 510)
            renderer.endFill()
            font.startFill(GREEN_LIGHT2)

            text = renderer.text(display.getText(), display.x - display.width / 2, display.y - 510)
            renderer.endFill()
        } else {
            val newTime = display.getText()
            log.info { newTime }
            text?.setText(newTime)
        }
    }


}
