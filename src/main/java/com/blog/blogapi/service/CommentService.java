package com.blog.blogapi.service;

import com.blog.blogapi.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
    void deleteComment(Integer commentId);
}
