buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath("org.mockito:mockito-core:3.12.4")
        classpath ("org.mockito:mockito-junit-jupiter:3.12.4")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
