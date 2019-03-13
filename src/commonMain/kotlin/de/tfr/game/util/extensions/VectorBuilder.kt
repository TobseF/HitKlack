package de.tfr.game.util.extensions

import com.soywiz.korma.geom.vector.VectorBuilder
import com.soywiz.korma.geom.vector.rect
import de.tfr.game.lib.actor.Point

fun VectorBuilder.square(point: Point, a: Double) {
    this.rect(point.x, point.y, a, a)
}