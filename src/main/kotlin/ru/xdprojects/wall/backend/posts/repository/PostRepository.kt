package ru.xdprojects.wall.backend.posts.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import ru.xdprojects.wall.backend.posts.domain.PostEntity

@Repository
interface PostRepository : PagingAndSortingRepository<PostEntity, Long>, CrudRepository<PostEntity, Long>