package de.tfr.game.libgx.emu

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Graphics

interface ApplicationListener {
    /** Called when the Application is first created.  */
    suspend fun create(container: Container)

    /** Called when the Application is resized. This can happen at any point during a non-paused state but will never happen
     * before a call to [.create].
     *
     * @param width the new width in pixels
     * @param height the new height in pixels
     */
    fun resize(width: Int, height: Int)

    /** Called when the Application should render itself.  */
    suspend fun render(renderer: Graphics)

    /** Called when the Application is paused, usually when it's not active or visible on screen. An Application is also
     * paused before it is destroyed.  */
    fun pause()

    /** Called when the Application is resumed from a paused state, usually when it regains focus.  */
    fun resume()

    /** Called when the Application is destroyed. Preceded by a call to [.pause].  */
    fun dispose()
}
