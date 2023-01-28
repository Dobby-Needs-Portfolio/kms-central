package mina1316.me.client.config.storage

import mina1316.me.client.config.proxy.createProxy
import mina1316.me.client.config.storage.annotation.Storage
import mina1316.me.client.config.storage.annotation.ValueField
import mina1316.me.client.storage.Configuration
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.h2.mvstore.MVMap
import org.h2.mvstore.MVStore
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

/**
 * This class is used to scan the storage classes and persist them on H2 MVStore.
 *
 * @author mina1316
 * @since 0.1
 */
class StorageManager<T : Any>(
    private val storageClass: KClass<T>,
) {
    val name: String
    val storage: T

    init {
        // Register the storage class to global storage map
        val mvMap = registerStorage(storageClass)
        name = getName(storageClass) ?: throw IllegalArgumentException("The storage class must have a name")

        // Create new storageClass instance that represents database fields to memory
        val storageInstance = storageClass.createInstance()

        // Using Kotlin Proxy to register delegates for each field in the storage class
        createProxy(storageInstance::class.java) { proxy, method, args ->
            logger.debug("Proxying ${storageInstance::class.simpleName}.${method.name} with args ${args?.contentToString()}")
            // If the method is a setter, then set the value in the database
            if (method.name.startsWith("set")) {
                // Get the value that is being set
                val value = args[0]

                // Get the property
                val property = storageInstance::class.memberProperties.first { property ->
                    property.name == method.name.substring(3).replaceFirstChar { it.lowercase(Locale.getDefault()) }
                }

                // Get valueField name from its annotation @ValueField.name
                val valueFieldName = property.findAnnotation<ValueField>()?.name ?: property.name

                // Persist the value to the database
                mvMap[valueFieldName] = value
                store.commit()
            }

            return@createProxy method.invoke(storageInstance, *args)
        }

        storage = storageInstance

    }


    companion object {
        private val logger: Logger = LogManager.getLogger(StorageManager::class.java)
        private val store: MVStore
        private val maps: HashMap<String, MVMap<String, Any>> = HashMap()


        init {
            logger.info("Storage Initialization started: ${System.getProperty("client.storage.name")}")
            val filename = System.getProperty("client.storage.name")
                ?: throw IllegalArgumentException("Storage filename is not specified. please define client.storage.name")

            store = MVStore.Builder()
                .fileName(filename)
                .open()
        }

        private fun getName(storageClass: KClass<out Any>): String? {
            return storageClass.annotations.find { it is Storage }?.let { (it as Storage).name }
        }

        private fun getMap(name: String): MVMap<String, Any> {
            return maps[name] ?: throw IllegalAccessException("Map $name is not registered")
        }

        private fun registerStorage(storageClass: KClass<out Any>): MVMap<String, Any> {
            // Get Storage name(map name) from annotation @Storage
            logger.debug("Start Registering Storage: ${storageClass.simpleName}")
            val storageName = getName(storageClass) ?: throw IllegalArgumentException("Storage name is not defined")

            // Check is map exists, and if not, create it
            if (!store.hasMap(storageName)) logger.info("Creating map $storageName")
            val storageMap = store.openMap<String, Any?>(storageName)

            // Get all fields from storage class and persist them to map if they are not exists
            val fields = storageClass.memberProperties
                .filter { field -> field.annotations.find { it is ValueField } != null }
            fields.forEach { field ->
                val fieldName = field.annotations
                    .find { it is ValueField }?.let { (it as ValueField).name }
                    ?.ifBlank { field.name }
                    ?: throw IllegalArgumentException("Field name is not defined")
                val fieldValue = ""
                if (!storageMap.containsKey(fieldName)) {
                    logger.info("    - Persist $storageName.$fieldName")
                    storageMap[fieldName] = fieldValue
                }
            }

            // register map to memory
            maps[storageName] = storageMap
            logger.debug("registered storage $storageName")

            store.commit().run {
                logger.info("$storageName persisted.")
            }
            return storageMap
        }
    }
}

fun main() {
    System.setProperty("client.storage.name", "client.mvstore")
    val manager = StorageManager(Configuration::class)

    val storage = manager.storage

    storage.connectIp = "192.168.1.0"

    println(storage.connectIp)

}