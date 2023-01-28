package mina1316.me.client.config.storage.annotation.validation

/**
 * Validate that this string matches the given regex.
 *
 * @author mina1316
 */
@Repeatable
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Regex(
    val pattern: String = "",
)
