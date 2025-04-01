package ru.xdprojects.wall.backend.posts.api

import org.springframework.data.domain.PageRequest
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.xdprojects.wall.backend.posts.api.dto.*
import ru.xdprojects.wall.backend.posts.service.PostsService

const val MAX_PAGE_SIZE = 15

@RequestMapping("/api/posts")
@RestController
@CrossOrigin(origins = ["*"])
class PostsController(
    private val postsService: PostsService
) {
    @GetMapping("", produces = ["application/json"])
    fun getPostPreviews(
        @RequestParam(defaultValue = 0.toString())
        page: Int,
        @RequestParam(defaultValue = 5.toString()) size: Int,
        pagedResourcesAssembler: PagedResourcesAssembler<PostPreviewDto>
    ): PagedModel<EntityModel<PostPreviewDto>> {
        return postsService.getPostPreviews(
            PageRequest.of(verifyPageNumber(page), verifyPageSize(size))
        ).let {
            pagedResourcesAssembler.toModel(it)
        }
    }

    @GetMapping("/{id}", produces = ["application/json"])
    fun getPostById(@PathVariable id: Long): PostDto {
        return postsService.getPostById(id)
    }

    @PostMapping("", consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewPost(@RequestBody post: NewPostDto): PostDto {
        return postsService.createNewPost(post, "random")
    }

    @PostMapping("/{id}/reply", consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewReply(@PathVariable id: Long, @RequestBody reply: NewReplyDto): PostDto {
        return postsService.createNewReply(
            postId = id,
            newReplyDto = reply,
            authorId = "random"
        )
    }

    private fun verifyPageNumber(pageNumber: Int): Int {
        if (pageNumber < 1) {
            return 0
        }
        return pageNumber
    }

    private fun verifyPageSize(pageSize: Int): Int {
        if (pageSize < 1) {
            return 0
        } else if (pageSize > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE
        }
        return pageSize
    }

}