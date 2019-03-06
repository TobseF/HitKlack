import com.soywiz.korge.Korge
import de.tfr.game.HitKlack
import de.tfr.game.ui.DEVICE
import de.tfr.game.util.Resolution

val resolution = Resolution(width = 800, height = 1440)

val debug = false

suspend fun main() = Korge(title = "HitClack", width = resolution.width, height = resolution.height, bgcolor = DEVICE) {

    val hitKlack = HitKlack(this).apply {
        create(stage)
    }
    addComponent(hitKlack)

}