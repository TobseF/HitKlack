import com.soywiz.korge.gradle.*

buildscript {
	repositories {
		mavenLocal()
		maven { url = uri("https://dl.bintray.com/soywiz/soywiz") }
		maven { url = uri("https://plugins.gradle.org/m2/") }
		mavenCentral()
	}
	dependencies {
		classpath("com.soywiz:korge-gradle-plugin:1.1.1")
	}
}

apply(plugin = "korge")

korge {
	id = "com.sample.demo"
	dependencyMulti("com.soywiz:korau-mp3:$korauVersion")
	dependencyMulti("com.soywiz:korau-ogg-vorbis:$korauVersion")
}
