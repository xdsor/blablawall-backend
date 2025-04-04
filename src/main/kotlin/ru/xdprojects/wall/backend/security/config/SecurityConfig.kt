package ru.xdprojects.wall.backend.security.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    @Value("\${app.cors.allowed-origin}")
    private val webOrigin: String
) {
    @Bean
    fun filterChain(
        http: HttpSecurity,
        authSuccessHandler: AuthenticationSuccessHandler,
        userActivatedAuthenticationManager: AuthorizationManager<RequestAuthorizationContext>
    ): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(HttpMethod.GET,"/api/users/self-info").authenticated()
                auth.requestMatchers("/login**").permitAll()
                auth.requestMatchers("/oauth2**").permitAll()
                    .anyRequest().access(userActivatedAuthenticationManager)
            }
            .oauth2Login { oauth2 ->
                oauth2.successHandler(authSuccessHandler)
            }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = mutableListOf<String?>(webOrigin)
        configuration.allowedMethods = mutableListOf<String>("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}