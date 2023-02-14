package mina1316.me.client

import javax.swing.JFrame

class Application() : JFrame() {
    init {
        title = "Hello, world!"
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(300, 300)
        isVisible = true
    }
}
