plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    jcenter()
}
dependencies{
    implementation("com.android.tools.build:gradle:3.5.1")
    implementation(gradleApi())
    implementation(localGroovy())

}