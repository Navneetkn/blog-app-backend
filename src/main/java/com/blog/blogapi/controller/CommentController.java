package com.blog.blogapi.controller;

import com.blog.blogapi.payloads.ApiResponse;
import com.blog.blogapi.payloads.CommentDto;
import com.blog.blogapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable(name = "postId") Integer id,
            @RequestParam(name = "userId") Integer userId
    ){
        CommentDto createdComment = commentService.createComment(commentDto, id, userId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer id){
        commentService.deleteComment(id);
        return new ResponseEntity<>(new ApiResponse("Comment deleted Successfully", HttpStatus.OK), HttpStatus.OK);
    }
}
