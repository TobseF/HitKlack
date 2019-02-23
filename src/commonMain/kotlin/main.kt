import com.soywiz.klock.seconds
import com.soywiz.korge.Korge
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.interpolation.Easing
import de.tfr.game.HitKlack

suspend fun main() = Korge(width = 800, height = 800, bgcolor = Colors.LIGHTGRAY) {
    val minDegrees = (-16).degrees
    val maxDegrees = (+16).degrees

    val texture = resourcesVfs["buttons.png"].readBitmap()

    val hitKlack = HitKlack(this).apply {
        create()
    }

    val image = image(resourcesVfs["korge.png"].readBitmap()) {
        rotation = maxDegrees
        anchor(.5, .5)
        scale(.2)
        position(720, 70)
    }

    launchImmediately {
        while (true) {
            hitKlack.render(graphics = this.graphics { })

            image.tween(image::rotation[minDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
            //image.tween(image::rotation[maxDegrees], time = 100.milliseconds, easing = Easing.EASE_IN_OUT)
        }
    }
}