package de.tfr.game

import com.soywiz.korge.tests.ViewsForTesting
import de.tfr.game.model.GameField
import kotlin.test.Test
import kotlin.test.assertTrue


class BoxGameTest : ViewsForTesting() {

    @Test
    fun testFirstRowFull() = viewsTest {
        val gameField = GameField(10)
        val game = BoxGame(gameField)
        game.setStone()
        game.setStone()
        game.setStone()
        game.setStone()
        println(game)
        assertTrue("First ring should be full") { gameField[9].isFull() }
    }
}