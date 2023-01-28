package mina1316.me.client.config.storage.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Storage(
    val name: String = "",
)
