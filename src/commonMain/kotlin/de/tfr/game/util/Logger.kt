package de.tfr.game.util

import com.soywiz.klogger.Logger
import kotlin.reflect.KClass

/**
 * @see com.soywiz.klogger.Logger
 */
object Logger {

    operator fun invoke(`class`: KClass<out Any>) = Logger(`class`.simpleName!!)

}