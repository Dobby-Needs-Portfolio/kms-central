package mina1316.me.client.storage

import mina1316.me.client.config.storage.annotation.Storage
import mina1316.me.client.config.storage.annotation.ValueField

@Storage("config")
class Configuration(
    @property:ValueField
    var connectIp: String = "",
    @property:ValueField("service_name")
    var serviceName: String = "",
    @property:ValueField
    var serviceConfiguration: String = "",
)
