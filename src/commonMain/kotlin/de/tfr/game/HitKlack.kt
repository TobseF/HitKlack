package de.tfr.game


import com.soywiz.klock.PerformanceCounter
import com.soywiz.klogger.Logger
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Graphics
import com.soywiz.korge.view.View
import de.tfr.game.lib.actor.Box2D
import de.tfr.game.libgx.emu.ApplicationAdapter
import de.tfr.game.model.GameField
import de.tfr.game.renderer.DisplayRenderer
import de.tfr.game.renderer.GameFieldRenderer
import de.tfr.game.renderer.LogoRenderer
import resolution

private val log = Logger("HitKlack")

class HitKlack(val view: View) : ApplicationAdapter() {

    private lateinit var renderer: GameFieldRenderer
    private lateinit var controller: Controller
    private lateinit var display: Display
    private lateinit var displayRenderer: DisplayRenderer
    private lateinit var game: BoxGame
    private lateinit var logo: LogoRenderer

    private val gameField = GameField(10)

    private var time: Double

    init {
        time = PerformanceCounter.microseconds
    }

    override suspend fun create(container: Container) {
        game = BoxGame(gameField)

        val center = resolution.getCenter()
        renderer = GameFieldRenderer(center)
        val gameFieldSize = renderer.getFieldSize(gameField)
        controller = Controller(center, gameFieldSize, view)
        controller.create(container)
        container.addComponent(controller)
        controller.addTouchListener(game)
        display = Display(Box2D(center, 280f, 90f))
        displayRenderer = DisplayRenderer(display)
        displayRenderer.create(container)
        logo = LogoRenderer(center, gameFieldSize)
        logo.create(container)
    }

    override suspend fun render(graphics: Graphics) {
        val deltaTime = getDeltaTime()
        log.debug { "time$deltaTime" }
        game.update(deltaTime)
        renderField(graphics)
        displayRenderer.render(graphics)
    }

    private fun getDeltaTime(): Double {
        val deltaTime = PerformanceCounter.microseconds - time
        time = PerformanceCounter.microseconds
        return (deltaTime / (1000 * 1000))
    }

    private fun renderField(graphics: Graphics) {
        this.renderer.apply {
            render(game.field, graphics)
            game.getStones().forEach(renderer::renderStone)
        }
    }

    override fun resize(width: Int, height: Int) {
    }
}