package mina1316.me.client

import mina1316.me.client.config.storage.StorageManager
import mina1316.me.client.storage.Configuration
import javax.swing.JFrame

class Application() : JFrame() {
    init {
        title = "Hello, world!"
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(300, 300)
        isVisible = true
    }
}

fun main(args: Array<String>) {
//    val logger = SimpleLogger(
//        Application::class.simpleName,
//        Level.DEBUG,
//        false,
//        true,
//        true,
//        true,
//        "yyyy-MM-dd HH:mm:ss.SSS",
//        null,
//        null,
//        System.out
//    )
//
//
//    logger.info("Starting application")

    System.setProperty("client.storage.name", "client.mvstore")

    StorageManager(Configuration::class)
    Application()
    println("Hello, world!")
}