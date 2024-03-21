package com.blog.demo.controller;

import com.blog.demo.dto.CategoryDTO;
import com.blog.demo.exceptions.Result;
import com.blog.demo.exceptions.StatusCode;
import com.blog.demo.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    @GetMapping("")
    public Result getCategories(@RequestParam(value = "category_id",required = false) Long id) {
        return new Result(true, StatusCode.SUCCESS, "Fetched category hierarchy successfully",this.categoryService.getCategory(id));
    }
    @PostMapping("")
    public Result createCategory(@RequestBody CategoryDTO categoryDTO) {
        return new Result(true, StatusCode.SUCCESS, "Category created successfully", this.categoryService.createCategory(categoryDTO));
    }
//    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("")
    public Result deleteCategory(@RequestParam("category_id") Long id) {
        categoryService.deleteCategory(id);
        return new Result(true, StatusCode.SUCCESS, "Category deleted successfully");
    }
    @PutMapping("")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO) {
        return new Result(true, StatusCode.SUCCESS, "Category updated successfully", this.categoryService.updateCategory(categoryDTO));
    }
}
