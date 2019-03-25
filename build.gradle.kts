plugins {
    `java-library`
}

group = "org.cat73"
version = "1.0.0-SNAPSHOT"

// Java 版本
configure<JavaPluginConvention> {
    val javaVersion = JavaVersion.VERSION_1_8

    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

// 仓库配置
repositories {
    mavenLocal()
    jcenter()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

// 源文件编码
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// 依赖
dependencies {
    implementation("org.spigotmc:spigot:1.11.2-R0.1-SNAPSHOT")
}
