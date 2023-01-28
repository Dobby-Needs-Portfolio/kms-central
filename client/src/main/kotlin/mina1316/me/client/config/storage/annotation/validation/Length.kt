package mina1316.me.client.config.storage.annotation.validation

/**
 * This marks a field as a string that must be a certain length.
 * make -1 for no limit on length
 *
 * @author mina1316
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Length(
    val min: Int = -1,
    val max: Int = -1,
)
