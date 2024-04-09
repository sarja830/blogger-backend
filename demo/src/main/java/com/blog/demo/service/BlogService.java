package com.blog.demo.service;


import com.blog.demo.dto.AuthorDTO;
import com.blog.demo.dto.BlogDTO;
import com.blog.demo.model.Category;
import com.blog.demo.model.blog.Blog;
import com.blog.demo.model.blog.BlogContent;
import com.blog.demo.model.user.User;
import com.blog.demo.repository.BlogContentRepository;
import com.blog.demo.repository.BlogRepository;
import com.blog.demo.repository.CategoryRepository;

import com.blog.demo.repository.UserRepository;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
//@Transactional is applied to every public individual method. Private and Protected methods are Ignored by Spring.
public class BlogService {
    private final BlogRepository blogRepository;
    private final BlogContentRepository blogContentRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    public Blog createBlog(BlogDTO blogDTO,  Jwt jwt) {
        User author =  getUserFromJwt(jwt);

        if(blogDTO.getCategoryId()==null)
            throw new RuntimeException("Category id is mandatory to create a blog");
        Category category = categoryRepository.findById(blogDTO.getCategoryId()).orElseThrow(() -> new UsernameNotFoundException("Category id is not valid"));

        BlogContent blogContentBuild = BlogContent.builder()
                .content(blogDTO.getContent())
                .tags(blogDTO.getTags())
                .created(new Date())
                .lastUpdated(new Date() )
                .images(blogDTO.getImages())
                .build();

        BlogContent blogContent = blogContentRepository.save(blogContentBuild);
        try{

            Blog blog = Blog.builder()
                    .title(blogDTO.getTitle())
                    .banner(blogDTO.getBanner())
                    .description(blogDTO.getDescription())
                    .contentId(blogContent.getId())
                    .draft(blogDTO.getDraft())
                    .viewCount(0)
                    .voteCount(0)
                    .commentCount(0)
                    .author(author)
                    .category(category)
                    .created(new Date())
                    .lastUpdated(new Date())
                    .build();

            return blogRepository.save(blog);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            blogContentRepository.deleteById(blogContent.getId());
            throw new UsernameNotFoundException("Failed to create blog");
        }
    }
    public Blog updateBlog(BlogDTO blogDTO,  Jwt jwt) {
        Category category = null;
        if(blogDTO.getCategoryId()!=null)
            category = categoryRepository.findById(blogDTO.getCategoryId()).orElseThrow(() -> new UsernameNotFoundException("Category id is not valid"));
        if(blogDTO.getId()==null)
            throw new UsernameNotFoundException("Blog id is mandatory to update a blog");
        User author =  getUserFromJwt(jwt);
        Blog blog = blogRepository.findById(blogDTO.getId()).orElseThrow(() -> new UsernameNotFoundException("Blog id is not valid"));

        BlogContent blogContent = blogContentRepository.findById(blog.getContentId()).orElseThrow(() -> new UsernameNotFoundException("Blog content id is not valid"));
        if(!blog.getAuthor().getId().equals(author.getId()) )
            throw new UsernameNotFoundException("You are not authorized to update this blog");
        if(blogDTO.getContent()!=null)
            blogContent.setContent(blogDTO.getContent());

        if(blogDTO.getTags()!=null)
            blogContent.setTags(blogDTO.getTags());
        if(blogDTO.getImages()!=null)
            blogContent.setImages(blogDTO.getImages());
        blogContent.setLastUpdated(new Date());
        blogContentRepository.save(blogContent);

        if(blogDTO.getTitle()!=null)
            blog.setTitle(blogDTO.getTitle());
        if(blogDTO.getDescription()!=null)
            blog.setDescription(blogDTO.getDescription());
        if(blogDTO.getBanner()!=null)
            blog.setBanner(blogDTO.getBanner());
        if(blogDTO.getDraft()!=null)
            blog.setDraft(blogDTO.getDraft());
        if(category!=null)
            blog.setCategory(category);
        blog.setLastUpdated(new Date());
        return blogRepository.save(blog);
    }
    //only owner and admin can delete the blog
    public void deleteBlog(Long blogId,  Jwt jwt) {
//        check the user with blog before deleting
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new UsernameNotFoundException("Blog id is not valid"));
        if(getUserFromJwt(jwt).equals(blog.getAuthor()))
            throw new UsernameNotFoundException("You are not authorized to delete this blog");
        blogRepository.deleteById(blogId);
    }


    public List<BlogDTO> getBlogs(Boolean draft,Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Blog> blogs = blogRepository.findByDraft(draft, pageable);
        return blogDTOList(blogs);
    }
    public Long countBlogs(Boolean draft){
        Long count = blogRepository.countByDraft(draft);
        return count;
    }
    public List<BlogDTO> getBlogsByAuthor(Boolean draft,List<Long> authorIds, Integer page, Integer pageSize ){
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Blog> blogs = blogRepository.findByDraftAndAuthorIdIn(draft, authorIds ,pageable);
        return blogDTOList(blogs);
    }
    public Long countBlogsByAuthor(Boolean draft,List<Long> authorIds){
        Long count = blogRepository.countByDraftAndAuthorIdIn(draft, authorIds );
        return count;
    }
    public List<BlogDTO> getBlogsByCategory( List<Integer> categoryIds, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Blog> blogs = blogRepository.findByCategoryIdInAndDraft(categoryIds, false, pageable);
        return blogDTOList(blogs);
    }
    public Long countBlogsByCategory( List<Integer> categoryIds) {
        Long count  = blogRepository.countByCategoryIdInAndDraft(categoryIds, false);
        return count;
    }
    public List<BlogDTO> getBlogsByDraftAuthorCategory(Boolean draft,List<Long> authorId, List<Integer> categoryIds, Integer page, Integer pageSize ){
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Blog> blogs = blogRepository. findByDraftAndAuthorIdInAndCategoryIdIn(draft, authorId,categoryIds,pageable);
        return blogDTOList(blogs);
    }
    public Long countBlogsByDraftAuthorCategory(Boolean draft, List<Long> authorId, List<Integer> categoryIds){
        Long count  = blogRepository.countByDraftAndAuthorIdInAndCategoryIdIn(draft, authorId,categoryIds);
        return count;
    }

//    public List<BlogDTO> getBlogsByAuthor(Integer page, Integer pageSize, Jwt jwt){
//        User author =  getUserFromJwt(jwt);
//        Pageable pageable = PageRequest.of(page,pageSize);
//        Page<Blog> blogs = blogRepository.findByAuthor(author.getId(),pageable);
//        return blogDTOList(blogs);
//    }

    public BlogDTO getBlogByIdPublic(Long blogId) {
        Blog blog = blogRepository.findByIdAndDraft(blogId,false).orElseThrow(() -> new UsernameNotFoundException("Blog id is not valid"));
        BlogContent blogContent = blogContentRepository.findById(blog.getContentId()).orElseThrow(() -> new UsernameNotFoundException("Blog content id is not valid"));
        return BlogDTO.builder()
                .title(blog.getTitle())
                .banner(blog.getBanner())
                .id(blog.getId())
                .description(blog.getDescription())
                .author(AuthorDTO.builder()
                        .username(blog.getAuthor().getUsername())
                        .email(blog.getAuthor().getEmail())
                        .name(blog.getAuthor().getName())
                        .build())
                .created(blog.getCreated())
                .lastUpdated(blog.getLastUpdated())
                .viewCount(blog.getViewCount())
                .voteCount(blog.getVoteCount())
                .commentCount(blog.getCommentCount())
                .content(blogContent.getContent())
                .tags(blogContent.getTags())
                .images(blogContent.getImages())
                .draft(blog.isDraft())
                .category(blog.getCategory())
                .categoryId(blog.getCategory().getId())
                .build();
    }
    public BlogDTO getBlogByIdByAuthor(Long blogId,Long userId) {
        Blog blog = blogRepository.findByIdAndAuthorId(blogId,userId).orElseThrow(() -> new UsernameNotFoundException("Blog id is not valid"));
        BlogContent blogContent = blogContentRepository.findById(blog.getContentId()).orElseThrow(() -> new UsernameNotFoundException("Blog content id is not valid"));
        return BlogDTO.builder()
                .title(blog.getTitle())
                .banner(blog.getBanner())
                .id(blog.getId())
                .description(blog.getDescription())
                .author(AuthorDTO.builder()
                        .username(blog.getAuthor().getUsername())
                        .email(blog.getAuthor().getEmail())
                        .name(blog.getAuthor().getName())
                        .build())
                .created(blog.getCreated())
                .lastUpdated(blog.getLastUpdated())
                .viewCount(blog.getViewCount())
                .voteCount(blog.getVoteCount())
                .commentCount(blog.getCommentCount())
                .content(blogContent.getContent())
                .tags(blogContent.getTags())
                .images(blogContent.getImages())
                .draft(blog.isDraft())
                .category(blog.getCategory())
                .categoryId(blog.getCategory().getId())
                .build();
    }

    public List<BlogDTO>  getBlogForEdit(Integer page, Integer pageSize){
//sorting logic here
        Pageable pageable = PageRequest.of(page,pageSize);
//        Page<Blog> blog = blogRepository.findAll(pageable);
        Page<Blog> blog = blogRepository.findByDraft(false, pageable);

        List<BlogDTO> blogDTOList = new LinkedList<>();
        for(Blog b: blog)
        {
            BlogContent blogContent = blogContentRepository.findById(b.getContentId()).orElseThrow(() -> new UsernameNotFoundException("Blog content id is not valid"));
            blogDTOList.add(BlogDTO.builder()
                    .title(b.getTitle())
                    .banner(b.getBanner())
                    .author(AuthorDTO.builder()
                            .username(b.getAuthor().getUsername())
                            .email(b.getAuthor().getEmail())
                            .name(b.getAuthor().getName())
                            .build())
//                    .categoryId(b.getCategory().getId())
                    .id(b.getId())
                    .description(b.getDescription())
                    .ContentId(b.getContentId())
                    .content(blogContent.getContent())
                    .tags(blogContent.getTags())
                    .images(blogContent.getImages())
                    .draft(b.isDraft())
                    .category(b.getCategory())
                    .created(b.getCreated())
                    .lastUpdated(b.getLastUpdated())
                    .voteCount(b.getVoteCount())
                    .viewCount(b.getViewCount())
                    .build());
        }

        return blogDTOList;
    }
    public User getUserFromJwt(Jwt jwt) {
        if(jwt==null)
            throw new UsernameNotFoundException("User not found. Invalid token");
        String username = (String) jwt.getClaims().get("sub");
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    List<BlogDTO> blogDTOList( Page<Blog> blogs )
    {
        List<BlogDTO> blogDTOList = new LinkedList<>();
        for(Blog b: blogs)
        {
            BlogContent blogContent = blogContentRepository.findById(b.getContentId()).orElseThrow(() -> new UsernameNotFoundException("Blog content id is not valid"));
            blogDTOList.add(BlogDTO.builder()
                    .title(b.getTitle())
                    .banner(b.getBanner())
                    .author(AuthorDTO.builder()
                            .username(b.getAuthor().getUsername())
                            .email(b.getAuthor().getEmail())
                            .name(b.getAuthor().getName())
                            .build())
//                    .categoryId(b.getCategory().getId())
                    .id(b.getId())
                    .description(b.getDescription())
                    .ContentId(b.getContentId())
                    .content(blogContent.getContent())
                    .tags(blogContent.getTags())
                    .images(blogContent.getImages())
                    .draft(b.isDraft())
                    .category(b.getCategory())
                    .created(b.getCreated())
                    .lastUpdated(b.getLastUpdated())
                    .voteCount(b.getVoteCount())
                    .viewCount(b.getViewCount())
                    .commentCount(b.getCommentCount())
                    .build());
        }
        return blogDTOList;
    }
}

