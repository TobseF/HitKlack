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
        game.setBlock()
        game.setBlock()
        game.setBlock()
        game.setBlock()
        println(game)
        assertTrue("First ring should be full") { gameField[9].isFull() }
    }

    @Test
    fun testMissedRing() = viewsTest {
        val gameField = GameField(10)
        val game = BoxGame(gameField)
        game.setBlock()
        game.setBlock()
        game.setBlock()
        println(game)
        game.move()
        println(game)
        game.setBlock()
        println(game)
        assertTrue("First ring should be empty") { gameField[9].isEmpty() }
        assertTrue("Second ring should be empty") { gameField[8].isEmpty() }
        assertTrue("Only block should be active") { gameField[9].blocks.count { it.active } == 1 }
    }
}