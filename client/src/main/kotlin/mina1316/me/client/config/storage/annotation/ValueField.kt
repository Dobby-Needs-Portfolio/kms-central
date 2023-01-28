package mina1316.me.client.config.storage.annotation

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ValueField(
    val name: String = "",
)
