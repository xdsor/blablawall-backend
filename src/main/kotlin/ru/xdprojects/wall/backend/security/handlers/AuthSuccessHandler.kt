package ru.xdprojects.wall.backend.security.handlers

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import ru.xdprojects.wall.backend.security.models.NewLogInEvent
import ru.xdprojects.wall.backend.security.models.OauthTokenToInternalAuthMapper

@Component
class AuthSuccessHandler(
    @Value("\${app.cors.allowed-origin}")
    private val webOrigin: String,
    private val oauthTokenToInternalAuthMapper: OauthTokenToInternalAuthMapper
) : AuthenticationSuccessHandler, ApplicationEventPublisherAware {
    private lateinit var publisher: ApplicationEventPublisher

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        if (authentication !is OAuth2AuthenticationToken) {
            throw RuntimeException("Could not determine authentication token type")
        }
        val internalAuth = oauthTokenToInternalAuthMapper.mapToInternalAuth(authentication)
        SecurityContextHolder.getContext().authentication = internalAuth
        publisher.publishEvent(NewLogInEvent(this, internalAuth))
        response.sendRedirect(webOrigin)
    }

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        this.publisher = applicationEventPublisher
    }
}