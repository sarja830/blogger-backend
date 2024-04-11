package com.blog.demo.service;



import com.blog.demo.dto.CategoryDTO;
import com.blog.demo.model.Category;
import com.blog.demo.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
//@Transactional is applied to every public individual method. Private and Protected methods are Ignored by Spring.
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDTO categoryDTO) {
        Category parentCategory = null;
        if(categoryDTO.getParentId() != null)
            parentCategory = categoryRepository.findById(categoryDTO.getParentId()).orElseThrow(() -> new UsernameNotFoundException("Parent category not found"));
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setParentId(categoryDTO.getParentId());
        return  categoryRepository.save(category);
    }
    // it can form a cycle in the category hierarchy
    // a child of parent cannot be a child;
    public Set<Long> getChildCategories(List<Long> CategoryIds) {
        Set<Long> childCategories = new HashSet<>();
        List<Category> categoryList = categoryRepository.findAll();
        HashMap<Long,LinkedList<Long>> parentMap = new HashMap<>();
        for(Category category: categoryList){
            LinkedList<Long> children = parentMap.getOrDefault(category.getParentId(), new LinkedList<>());
            children.add(category.getId());
            parentMap.put(category.getParentId(),children);
        }
        Queue<Long> queue = new LinkedList<>();
        queue.addAll(CategoryIds);
        while(!queue.isEmpty()){
            Long temp = queue.poll();
            childCategories.add(temp);
            if(parentMap.containsKey(temp)){
                for(Long child: parentMap.get(temp)){
                    queue.add(child);
                }
            }
        }
        return childCategories;
    }
    public Category updateCategory(CategoryDTO categoryDTO) {

        Category category = categoryRepository.findById(categoryDTO.getId()).orElseThrow(() -> new UsernameNotFoundException("Category id is not valid"));
        if(categoryDTO.getParentId() != null) {
            if (getChildCategories(Arrays.asList(categoryDTO.getId())).contains(categoryDTO.getParentId()))
                throw new RuntimeException("Parent category cannot be a child of its own children");
            category.setParentId(categoryDTO.getParentId());
        }
        if(categoryDTO.getName()!=null) category.setName(categoryDTO.getName());
        return categoryRepository.save(category);

    }
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public List<Category> getCategory(Long categoryId) {
        List<Category> categoryList = new ArrayList<>();
        if(categoryId!=null)
            categoryList.add(categoryRepository.findById(categoryId).orElseThrow(() -> new UsernameNotFoundException("Category id is not valid")));
        else
            categoryList.addAll(categoryRepository.findAll());

        return categoryList;
    }
}
