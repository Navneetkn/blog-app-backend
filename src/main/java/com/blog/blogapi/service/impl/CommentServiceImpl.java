package com.blog.blogapi.service.impl;

import com.blog.blogapi.entities.Comment;
import com.blog.blogapi.entities.Post;
import com.blog.blogapi.entities.Users;
import com.blog.blogapi.exceptions.ResourceNotFoundException;
import com.blog.blogapi.payloads.CommentDto;
import com.blog.blogapi.repository.CommentRepo;
import com.blog.blogapi.repository.PostRepo;
import com.blog.blogapi.repository.UserRepo;
import com.blog.blogapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        Users user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment updatedComment = commentRepo.save(comment);

        return modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));
        commentRepo.delete(comment);

    }
}
