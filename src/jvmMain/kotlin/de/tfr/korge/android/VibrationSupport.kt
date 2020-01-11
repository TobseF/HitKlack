package de.tfr.korge.android

import com.soywiz.korge.plugin.KorgePluginExtension

class VibrationSupport : KorgePluginExtension() {

	override fun getAndroidManifestApplication(): String? =
		"""<uses-permission android:name="android.permission.VIBRATE" />"""

}