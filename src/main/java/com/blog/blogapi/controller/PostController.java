package com.blog.blogapi.controller;

import com.blog.blogapi.config.AppConstants;
import com.blog.blogapi.payloads.ApiResponse;
import com.blog.blogapi.payloads.PostDto;
import com.blog.blogapi.payloads.PostResponse;
import com.blog.blogapi.service.FileService;
import com.blog.blogapi.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/users/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable(name = "userId") Integer userId,
            @PathVariable(name = "categoryId") Integer categoryId
    ){
        PostDto created = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable(name = "userId") Integer userId){
        List<PostDto> postDtos = postService.getAllPostByUser(userId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable(name = "categoryId") Integer categoryId){
        List<PostDto> postDtos = postService.getAllPostByCategory(categoryId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_TITLE, required = false) String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection
    ){
        PostResponse postServiceAllPost = postService.getAllPost(pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(postServiceAllPost, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Integer id){
        PostDto postDto = postService.getPostById(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping("post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable(name = "postId") Integer id){
        postService.deletePost(id);

        return new ResponseEntity<>(
                new ApiResponse("Record Deleted Successfully", HttpStatus.OK),
                HttpStatus.OK
        );
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDtoBody, @PathVariable(name = "postId") Integer id){
        PostDto postDto = postService.updatePost(postDtoBody, id);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping("/post/search")
    public  ResponseEntity<List<PostDto>> searchPosts(@RequestParam(name = "keyword", required = false) String keyword){
        List<PostDto> postDtos = postService.searchPosts(keyword);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable(name = "postId") Integer id
            ) throws IOException
    {
        PostDto postDto = postService.getPostById(id);
        String fileName = fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        postService.updatePost(postDto, id);
        PostDto postDtoResult = postService.updatePost(postDto, id);
        return new ResponseEntity<PostDto>(postDtoResult, HttpStatus.CREATED);
    }

//    @GetMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
//    public void downloadImage(
//            @PathVariable("imageName") String imageName,
//            HttpServletResponse response
//    ) throws IOException
//    {
//        InputStream inputStream = fileService.getResource(path, imageName);
//        InputStreamEntity
//
//    }
}