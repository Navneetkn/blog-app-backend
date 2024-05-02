package com.blog.blogapi.service;

import com.blog.blogapi.payloads.PostDto;
import com.blog.blogapi.payloads.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer postId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer id);
    void deletePost(Integer id);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    PostDto getPostById(Integer id);
    List<PostDto> getAllPostByCategory(Integer categoryId);
    List<PostDto> getAllPostByUser(Integer userId);
    List<PostDto> searchPosts(String keyword);
}
