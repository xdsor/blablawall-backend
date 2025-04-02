package ru.xdprojects.wall.backend.security.models.extractors

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken

interface OauthTokenInformationExtractor {
    fun getClientRegistrationId(): String
    fun extractEmail(auth: OAuth2AuthenticationToken): String
    fun extractUserName(auth: OAuth2AuthenticationToken): String
    fun extractAvatarUrl(auth: OAuth2AuthenticationToken): String?
}