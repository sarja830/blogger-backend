package com.blog.demo.service;

import com.blog.demo.dto.BlogRequestDTO;
import com.blog.demo.model.blog.Blog;
import com.blog.demo.model.blog.BlogContent;
import com.blog.demo.repository.BlogContentRepository;
import com.blog.demo.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {

    private final BlogContentRepository blogContentRepository;
    private final BlogRepository blogRepository;
    public void save(BlogRequestDTO blogRequestDTO) {
        Blog blog = new Blog();
        BlogContent blogContent = new BlogContent();
        blogContent.builder()
                .content(blogRequestDTO.getContent())
                .build();
        log.info("Product {} is created ",blogContent.getId());
        blogContentRepository.save(blogContent);
        blog.builder()
                .title(blogRequestDTO.getTitle())
                .banner(blogRequestDTO.getBanner())
                .content(blogRequestDTO.getContent())
//                .tags(blogRequestDTO.getTags())
                .draft(blogRequestDTO.getDraft())
                .id(blogRequestDTO.getId())
                .contentId(blogContent.getId())
                .build();


//        #TODO  handle duplicates in the future
//        Blog blog = blogRepository.findByName(postRequest.getBlogName())
//                .orElseThrow(() -> new BlogNotFoundException(postRequest.getBlogName()));
//        blogRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

}
