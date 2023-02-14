package mina1316.me.core.configuration

import de.codecentric.boot.admin.server.config.AdminServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.util.*

@Configuration
/**
 * Security configuration for core application.
 *
 * handle request for Eureka MSA dashboard, Spring Boot Admin dashboard, and other services.
 */
class SecurityConfiguration(
    private val adminServer: AdminServerProperties
) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain = httpSecurity.also { http ->
        val successHandler = SavedRequestAwareAuthenticationSuccessHandler()
        val adminContextPath = this.adminServer.contextPath
        successHandler.setTargetUrlParameter("redirectTo")
        successHandler.setDefaultTargetUrl("$adminContextPath/")

        http.authorizeRequests()
            .antMatchers("$adminContextPath/assets/**").permitAll()
            .antMatchers("$adminContextPath/eureka/**").permitAll()
            .antMatchers("$adminContextPath/login").permitAll()
            .anyRequest().authenticated()
        http.formLogin()
            .loginPage("$adminContextPath/login")
            .successHandler(successHandler)
        http.logout()
            .logoutUrl("$adminContextPath/logout")
        http.httpBasic()
        http.csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringRequestMatchers(
                AntPathRequestMatcher("$adminContextPath/instances", "POST"),
                AntPathRequestMatcher("$adminContextPath/instances/*", "DELETE"),
                AntPathRequestMatcher("$adminContextPath/actuator/**")
            )
        http.rememberMe()
            .key(UUID.randomUUID().toString())
            .tokenValiditySeconds(1209600)

    }.build()
}