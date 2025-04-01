package ru.xdprojects.wall.backend.users.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.xdprojects.wall.backend.users.domain.UserEntity

@Repository
interface UserRepository : JpaRepository<UserEntity, String>