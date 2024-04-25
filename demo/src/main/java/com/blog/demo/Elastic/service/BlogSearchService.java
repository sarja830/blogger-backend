package com.blog.demo.Elastic.service;

import com.blog.demo.Elastic.dto.BlogSearchDTO;
import com.blog.demo.Elastic.model.BlogSearch;
import com.blog.demo.Elastic.repository.BlogSearchRepository;
import com.blog.demo.dto.AuthorDTO;
import com.blog.demo.dto.BlogDTO;
import com.blog.demo.model.Category;
import com.blog.demo.model.blog.Blog;
import com.blog.demo.model.blog.BlogContent;
import com.blog.demo.repository.BlogContentRepository;
import com.blog.demo.repository.BlogRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;


import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BlogSearchService {
    private BlogSearchRepository blogSearchRepository;
    private BlogRepository blogRepository;
    private BlogContentRepository blogContentRepository;


    List<BlogDTO>  TransformBlogSearchDTOToBlogDTO(Page<BlogSearch> blogSearchDTOList )
    {
        List<BlogDTO> blogDTOList = new LinkedList<>();
        for(BlogSearch blogSearchDTO: blogSearchDTOList) {
            AuthorDTO authorDTO = AuthorDTO.builder()
                    .id(blogSearchDTO.getAuthorId())
                    .name(blogSearchDTO.getName())
                    .profileImage(blogSearchDTO.getProfileImage())
                    .username(blogSearchDTO.getUsername())
                    .build();
            Category category = Category.builder()
                    .id(blogSearchDTO.getCategoryId())
                    .name(blogSearchDTO.getCategoryName())
                    .build();
            BlogDTO blogDTO = BlogDTO.builder()
                    .id(blogSearchDTO.getId())
                    .banner(blogSearchDTO.getBanner())
                    .title(blogSearchDTO.getTitle())
                    .content(blogSearchDTO.getContent())
//                    .voteCount(blogSearchDTO.getVoteCount())
//                    .commentCount(blogSearchDTO.getCommentCount())
//                    .viewCount(blogSearchDTO.getViewCount())
                    .description(blogSearchDTO.getDescription())
                    .draft(blogSearchDTO.getDraft())
                    .author(authorDTO)
                    .category(category)
                    .created(blogSearchDTO.getCreated())
                    .tags(blogSearchDTO.getTags())
                    .build();
            blogDTOList.add(blogDTO);
        }
        return blogDTOList;
    }

    public List<BlogDTO> search(String query, Integer page, Integer pageSize) {
        Sort sort = Sort.by("created").descending();
        Pageable pageable = PageRequest.of(page,pageSize,sort);
        Page<BlogSearch> blogSearchDTOList = blogSearchRepository.findByTitleOrContentOrDescriptionUsingCustomQuery(query, pageable);
        return TransformBlogSearchDTOToBlogDTO(blogSearchDTOList);
    }
    public Long searchResultCount(String query) {

        Long count = blogSearchRepository.CountByTitleOrContentOrDescription(query);
        return count;
    }
    public List<BlogDTO> searchByUser(String query, Integer page, Integer pageSize) {
        Sort sort = Sort.by("created").descending();
        Pageable pageable = PageRequest.of(page,pageSize,sort);
        Page<BlogSearch> blogSearchDTOList = blogSearchRepository.findUser( query, pageable);
        return TransformBlogSearchDTOToBlogDTO(blogSearchDTOList);
    }
    public Long  countByUser(String query) {
     Long count = blogSearchRepository.CountUser( query);
     return count;
    }
    public void sync(Long id) {
        log.info("Syncing blog to Elastic search with id: "+ id);
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new ErrorResponseException(HttpStatus.BAD_REQUEST,new Throwable("Blog id is not valid")));
        BlogContent blogContent = blogContentRepository.findById(blog.getContentId()).orElseThrow(() -> new ErrorResponseException(HttpStatus.NOT_FOUND,new Throwable("Blog content id is not valid")));
        BlogSearch blogSearch = BlogSearch.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blogContent.getContent())
                .description(blog.getDescription())
                .draft(blog.isDraft())
                .authorId(blog.getAuthor().getId())
                .profileImage(blog.getAuthor().getProfileImage())
                .name(blog.getAuthor().getName())
                .username(blog.getAuthor().getUsername())
                .created(blog.getCreated())
                .banner(blog.getBanner())
                .tags(blogContent.getTags())
                .categoryId(blog.getCategory().getId())
                .categoryName(blog.getCategory().getName())
                .build();

        blogSearchRepository.save(blogSearch);
    }
}
