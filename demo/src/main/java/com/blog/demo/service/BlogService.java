//package com.blog.demo.service;
//
//
//import com.blog.demo.dto.BlogDTO;
//import com.blog.demo.model.blog.Blog;
//import com.blog.demo.repository.BlogRepository;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//
//
//@Service
//@Slf4j
//@AllArgsConstructor
//@Transactional
// //@Transactional is applied to every public individual method. Private and Protected methods are Ignored by Spring.
//public class BlogService {
//    private final BlogRepository blogRepository;
//
//    public Blog createBlog(BlogDTO blogDTO) {
//
//        return  blogRepository.save(blog);
//    }
//    // it can form a cycle in the blog hierarchy
//    // a child of parent cannot be a child;
//    public Blog updateBlog(BlogDTO blogDTO) {
//
//        Blog blog = blogRepository.findById(blogDTO.getId()).orElseThrow(() -> new UsernameNotFoundException("Blog id is not valid"));
//        if(blogDTO.getParentId() != null){
//            Blog parentBlog = blogRepository.findById(blogDTO .getParentId()).orElseThrow(() -> new UsernameNotFoundException("Parent blog not found"));
//            //fetching all the children of blog id and checking if the parent id is not a child of its own children
//            Queue<Blog> queue = new LinkedList<>(blogRepository.findAllByParentId(blogDTO.getId()));
//            while(!queue.isEmpty()){
//                Blog temp = queue.poll();
//                if(temp.getId().equals(blogDTO.getParentId()))
//                    throw new RuntimeException("Parent blog cannot be a child of its own children");
//                queue.addAll(blogRepository.findAllByParentId(temp.getId()));
//            }
//            blog.setParentId(blogDTO.getParentId());
//        }
//        if(blogDTO.getName()!=null) blog.setName(blogDTO.getName());
//        return blogRepository.save(blog);
//
//    }
//    public void deleteBlog(Long blogId) {
//        blogRepository.deleteById(blogId);
//    }
//
//    public List<Blog> getBlog(Long blogId) {
//        List<Blog> blogList = new ArrayList<>();
//        if(blogId!=null)
//             blogList.add(blogRepository.findById(blogId).orElseThrow(() -> new UsernameNotFoundException("Blog id is not valid")));
//        else
//            blogList.addAll(blogRepository.findAll());
//
//        return blogList;
//    }
//}
