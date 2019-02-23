package de.tfr.game


import com.soywiz.klock.PerformanceCounter
import com.soywiz.klogger.Logger
import com.soywiz.korge.view.Graphics
import com.soywiz.korge.view.View
import com.soywiz.korim.color.RGBA
import de.tfr.game.lib.actor.Box2D
import de.tfr.game.lib.actor.Point2D
import de.tfr.game.libgx.emu.ApplicationAdapter
import de.tfr.game.libgx.emu.Viewport
import de.tfr.game.model.GameField
import de.tfr.game.renderer.ControllerRenderer
import de.tfr.game.renderer.DisplayRenderer
import de.tfr.game.renderer.GameFieldRenderer
import de.tfr.game.renderer.LogoRenderer
import de.tfr.game.ui.DEVICE

private val log = Logger("HitKlack")

class HitKlack(val view: View) : ApplicationAdapter() {

    data class Resolution(var width: Float, var height: Float) {
        fun getCenter() = Point2D(width / 2.0, height / 2.0)
    }

    private lateinit var renderer: GameFieldRenderer
    private lateinit var controller: Controller
    private lateinit var display: Display
    private lateinit var displayRenderer: DisplayRenderer
    private lateinit var controllerRenderer: ControllerRenderer
    private lateinit var game: BoxGame
    private lateinit var logo: LogoRenderer

    private val gameField = GameField(10)
    private val resolution = Resolution(800f, 1400f)
    val viewport = Viewport

    private var time: Double

    init {
        time = PerformanceCounter.microseconds
    }

    override suspend fun create() {
        game = BoxGame(gameField)

        val center = resolution.getCenter()
        renderer = GameFieldRenderer(center)
        val gameFieldSize = renderer.getFieldSize(gameField)

        controller = Controller(center, gameFieldSize, viewport, view)
        controller.addTouchListener(game)
        display = Display(Box2D(center, 280f, 90f))
        displayRenderer = DisplayRenderer(display)
        displayRenderer.create()
        controllerRenderer = ControllerRenderer()
        controllerRenderer.create()
        logo = LogoRenderer(center, gameFieldSize)
        logo.create()
    }

    override suspend fun render(graphics: Graphics) {
        val deltaTime = getDeltaTime()
        log.debug { "time$deltaTime" }
        clear()
        controllerRenderer.render(controller)
        renderField(graphics)
        game.update(deltaTime)
        displayRenderer.render(graphics)
        logo.render()
    }

    private fun getDeltaTime(): Double {
        val deltaTime = PerformanceCounter.microseconds - time
        time = PerformanceCounter.microseconds
        return deltaTime / (1000 * 1000)
    }

    private fun renderField(graphics: Graphics) {
        this.renderer.apply {
            start()
            render(game.field, graphics)
            game.getStones().forEach(renderer::renderStone)
            end()
        }
    }

    private fun clear() = clear(DEVICE)

    private fun clear(color: RGBA) {
        //   renderGameBackground()
    }

    private fun renderGameBackground() {
        // shapeRenderer.rect(0f, 0f, resolution.width, resolution.height)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }
}