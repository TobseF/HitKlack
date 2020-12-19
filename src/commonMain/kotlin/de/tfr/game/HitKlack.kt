package de.tfr.game


import com.soywiz.klock.TimeSpan
import com.soywiz.korge.component.UpdateComponent
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import de.tfr.game.lib.actor.Box2D
import de.tfr.game.lib.engine.Loadable
import de.tfr.game.model.GameField
import de.tfr.game.renderer.DisplayRenderer
import de.tfr.game.renderer.GameFieldRenderer
import de.tfr.game.renderer.LogoRenderer
import resolution

class HitKlack(override val view: View) : UpdateComponent, Loadable {

    private lateinit var renderer: GameFieldRenderer
    private lateinit var controller: Controller
    private lateinit var display: Display
    private lateinit var displayRenderer: DisplayRenderer
    private lateinit var game: BoxGame
    private lateinit var logo: LogoRenderer

    private val gameField = GameField(10)

    override suspend fun create(container: Container) {
        val center = resolution.getCenter()
        renderer = GameFieldRenderer(center, gameField).apply { create(container) }
        game = BoxGame(gameField).apply { create(container) }

        val gameFieldSize = renderer.getFieldSize(gameField.size)
        controller = Controller(center, gameFieldSize, view)
        controller.create(container)
        container.addComponent(controller)
        controller.addTouchListener(game)
        display = Display(Box2D(center, 280.0, 90.0))
        displayRenderer = DisplayRenderer(display)
        displayRenderer.create(container)
        logo = LogoRenderer(center, gameFieldSize)
        logo.create(container)
    }

    override fun update(dt: TimeSpan) {
        val deltaTime = dt.seconds
        game.update(deltaTime)
        displayRenderer.render()
    }

}