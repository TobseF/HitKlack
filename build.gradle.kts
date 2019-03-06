import com.soywiz.korge.gradle.GameCategory
import com.soywiz.korge.gradle.Orientation
import com.soywiz.korge.gradle.korauVersion
import com.soywiz.korge.gradle.korge

buildscript {
    repositories {
        mavenLocal()
        maven { url = uri("https://dl.bintray.com/soywiz/soywiz") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenCentral()
    }
    dependencies {
        classpath("com.soywiz:korge-gradle-plugin:1.1.3")
    }
}

apply(plugin = "korge")

korge {
    id = "de.tfr.game.hitclack"
    authorName = "TobseF"
    name = "HitKlack"
    description = "Retro console game remake of Mephisto Hit Klack"
    gameCategory = GameCategory.ARCADE
    orientation = Orientation.PORTRAIT
    exeBaseName = "HitKlack"

    dependencyMulti("com.soywiz:korau-mp3:$korauVersion")
}
