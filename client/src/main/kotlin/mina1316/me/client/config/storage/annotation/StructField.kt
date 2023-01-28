package mina1316.me.client.config.storage.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class StructField(
    val name: String = "",
)
