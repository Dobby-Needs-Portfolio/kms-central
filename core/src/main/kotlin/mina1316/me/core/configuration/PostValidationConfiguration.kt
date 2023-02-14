package mina1316.me.core.configuration

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
/**
 * Post validation configuration for core application.
 *
 * for example, check properties and if they are default values, stop application.
 * or, check if some services are available.
 */
class PostValidationConfiguration(
  private val environment: Environment,
  private val applicationContext: ApplicationContext,
) {
  @PostConstruct
  fun init() {
    environment.getProperty("spring.security.user.name")?.let {
      if (it == "user") {
        logger.error(defaultErrorMessage, IllegalStateException("spring.security.user.name is default value."))
        (applicationContext as ConfigurableApplicationContext).close()
      }
    }
    environment.getProperty("spring.security.user.password")?.let {
      if (it == "password") {
        logger.error(
          defaultErrorMessage,
          IllegalStateException("spring.security.user.password is default value.")
        )
        (applicationContext as ConfigurableApplicationContext).close()
      }
    }

  }

  companion object {
    private const val defaultErrorMessage = "An error occurred in validation: "
    private val logger = LoggerFactory.getLogger(PostValidationConfiguration::class.java)
  }
}