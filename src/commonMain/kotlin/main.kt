import com.soywiz.klock.seconds
import com.soywiz.korge.Korge
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.interpolation.Easing
import de.tfr.game.HitKlack
import de.tfr.game.ui.DEVICE
import de.tfr.game.util.Resolution

val resolution = Resolution(width = 1080, height = 1440)

suspend fun main() = Korge(width = resolution.width, height = resolution.height, bgcolor = DEVICE) {
    val minDegrees = (-16).degrees
    val maxDegrees = (+16).degrees

    val hitKlack = HitKlack(this).apply {
        create(stage)
    }

    val image = image(resourcesVfs["korge.png"].readBitmap()) {
        rotation = maxDegrees
        anchor(.5, .5)
        scale(.2)
        position(resolution.width - 70, 70)
    }

    launchImmediately {
        while (true) {
            hitKlack.render(graphics = this.graphics { })

            image.tween(image::rotation[minDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
        }
    }
}