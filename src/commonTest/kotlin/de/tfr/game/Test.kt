package de.tfr.game

import com.soywiz.korge.tests.ViewsForTesting
import de.tfr.game.model.GameField
import de.tfr.game.model.Orientation
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class BoxGameTest : ViewsForTesting() {

    @Test
    fun testFirstRowFull() {
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
    fun testMissedRing() {
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


    @Test
    fun testMissedRingOnRing2() {
        val gameField = GameField(10)
        val game = BoxGame(gameField, Orientation.Up)

        fun moveAndSet(orientation: Orientation) {
            game.nextDirection = orientation
            game.move()
            game.setBlock()
        }

        moveAndSet(Orientation.Left)
        moveAndSet(Orientation.Right)
        moveAndSet(Orientation.Down)
        game.move()
        game.move()
        println(game)
        game.nextDirection = Orientation.Left
        game.setBlock()
        println(game)
        assertTrue("First ring should be empty") { gameField[9].isEmpty() }
        assertTrue("Second ring should be empty") { gameField[8].isEmpty() }
        assertTrue("Only block should be active") { gameField[9].blocks.count { it.active } == 1 }
    }


    @Test
    fun testRemovePreviousBlock() = viewsTest {
        val gameField = GameField(10)
        val game = BoxGame(gameField, Orientation.Up)

        fun moveAndSet(orientation: Orientation) {
            game.nextDirection = orientation
            game.setBlock()
            assertFalse("First ring should set with one stone") { gameField[9].isEmpty() }
            game.move()
            game.setBlock()
            assertTrue("First ring should be empty") { gameField[9].isEmpty() }
            assertTrue("Second ring should be empty") { gameField[8].isEmpty() }
        }

        moveAndSet(Orientation.Left)
        moveAndSet(Orientation.Down)
        moveAndSet(Orientation.Up)
        moveAndSet(Orientation.Right)
    }
}