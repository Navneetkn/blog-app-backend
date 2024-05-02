package com.blog.blogapi.service.impl;

import com.blog.blogapi.entities.Category;
import com.blog.blogapi.entities.Post;
import com.blog.blogapi.entities.Users;
import com.blog.blogapi.exceptions.ResourceNotFoundException;
import com.blog.blogapi.payloads.PostResponse;
import com.blog.blogapi.repository.CategoryRepo;
import com.blog.blogapi.payloads.PostDto;
import com.blog.blogapi.repository.PostRepo;
import com.blog.blogapi.repository.UserRepo;
import com.blog.blogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        Users users = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(users);
        post.setCategory(category);
        post.setContent(post.getContent());

        Post newPost = postRepo.save(post);
        return modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = postRepo.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));
        postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        Sort sortFunction = Sort.by(sortBy);
        if(sortDirection.equalsIgnoreCase("desc"))
            sortFunction = Sort.by(sortBy).descending();

        // Pagination
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortFunction);
        Page<Post> postPage = postRepo.findAll(pageable);
        List<Post> posts = postPage.getContent();

        List<PostDto> postDtoList = posts
                .stream()
                .map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", "Post Id", id));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        List<Post> postsByCategory = postRepo.findByCategory(category);
        List<PostDto> postDtoList = postsByCategory
                .stream()
                .map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users", "User Id", userId));
        List<Post> posts = postRepo.findByUser(user);
        List<PostDto> postDtoList = posts
                .stream()
                .map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtoList =  posts
                .stream()
                .map((post) -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtoList;
    }
}
