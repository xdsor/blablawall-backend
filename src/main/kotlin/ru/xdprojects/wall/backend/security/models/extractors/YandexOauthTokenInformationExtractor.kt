package ru.xdprojects.wall.backend.security.models.extractors

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Component

@Component
class YandexOauthTokenInformationExtractor : OauthTokenInformationExtractor {
    override fun getClientRegistrationId(): String = "yandex"

    override fun extractEmail(auth: OAuth2AuthenticationToken): String {
        return auth.principal.getAttribute<String?>("default_email") ?: throw RuntimeException("No email attribute found")
    }

    override fun extractUserName(auth: OAuth2AuthenticationToken): String {
        return auth.principal.getAttribute<String?>("login") ?: throw RuntimeException("No login attribute found")
    }

    override fun extractAvatarUrl(auth: OAuth2AuthenticationToken): String? {
        return with(auth.principal.getAttribute<String?>("default_avatar_id")) {
            this?.let { "https://avatars.yandex.net/get-yapic/$it/islands-200" }
        }
    }

}