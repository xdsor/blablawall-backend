package ru.xdprojects.wall.backend.posts.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.xdprojects.wall.backend.posts.api.dto.NewPostDto
import ru.xdprojects.wall.backend.posts.api.dto.NewReplyDto
import ru.xdprojects.wall.backend.posts.api.dto.PostDto
import ru.xdprojects.wall.backend.posts.api.dto.PostPreviewDto

interface PostsService {
    fun getPostPreviews(pageable: Pageable): Page<PostPreviewDto>
    fun getPostById(id: Long): PostDto
    fun createNewPost(newPostDto: NewPostDto, authorId: String): PostDto
    fun createNewReply(postId: Long, newReplyDto: NewReplyDto, authorId: String): PostDto
}