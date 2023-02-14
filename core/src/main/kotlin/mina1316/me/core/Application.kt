package mina1316.me.core

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer


@SpringBootApplication
@EnableAdminServer
@EnableEurekaServer
class CoreApplication()

fun main() {
    SpringApplication.run(CoreApplication::class.java)
}