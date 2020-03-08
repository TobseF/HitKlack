import com.soywiz.korge.gradle.GameCategory
import com.soywiz.korge.gradle.Orientation
import com.soywiz.korge.gradle.korge

buildscript {
    val korgePluginVersion = "1.10.0.0"

    repositories {
        mavenLocal()
        maven { url = uri("https://dl.bintray.com/korlibs/korlibs") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenCentral()
    }
    dependencies {
        classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
    }
}

apply(plugin = "korge")

korge {
    id = "de.tfr.game.hitclack"

    name = "Hit Klack"
    authorName = "TobseF"
    description = "Retro console game remake of Mephisto Hit Klack"
    gameCategory = GameCategory.ARCADE

    exeBaseName = "HitKlack"
    icon = File("build-res/icon.png")

    orientation = Orientation.PORTRAIT
}
