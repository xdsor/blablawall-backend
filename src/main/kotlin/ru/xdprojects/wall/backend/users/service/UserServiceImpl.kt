package ru.xdprojects.wall.backend.users.service

import org.springframework.stereotype.Service
import ru.xdprojects.wall.backend.security.models.DomainPrincipal
import ru.xdprojects.wall.backend.users.domain.UserEntity
import ru.xdprojects.wall.backend.users.repository.UserRepository
import java.time.LocalDateTime

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun registerNewUser(domainPrincipal: DomainPrincipal) {
        userRepository.save(domainPrincipal.toUserEntity())
    }
}

fun DomainPrincipal.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.userId,
        name = this.username,
        profileImageUrl = this.profilePictureUrl,
        activated = true,
        invitedBy = null,
        dateJoined = LocalDateTime.now(),
    )
}