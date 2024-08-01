package com.sitera.pos.category.controller;

import com.sitera.pos.category.model.CategoryReq;
import com.sitera.pos.category.model.CategoryRes;
import com.sitera.pos.category.model.CategoryStatus;
import com.sitera.pos.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.get());
        return "pages/category/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryReq());
        model.addAttribute("statuses", CategoryStatus.values());
        return "create-category";
    }

    @PostMapping("/save")
    public String createCategory(
            @ModelAttribute CategoryReq categoryReq,
            @RequestParam("image") MultipartFile image,
            Model model) {
        try {
            Optional<CategoryRes> savedCategory = categoryService.save(categoryReq, image);
            return savedCategory.map(categoryRes -> "redirect:/categories").orElse("create-category");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create category: " + e.getMessage());
            model.addAttribute("statuses", CategoryStatus.values());
            return "create-category";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<CategoryRes> category = categoryService.getById(id);
        model.addAttribute("category", category.orElse(new CategoryRes()));
        model.addAttribute("statuses", CategoryStatus.values());
        return "edit-category";
    }

    @PostMapping("/{id}/edit")
    public String editCategory(
            @PathVariable String id,
            @ModelAttribute CategoryReq categoryReq,
            @RequestParam("image") MultipartFile image,
            Model model) {
        try {
            Optional<CategoryRes> updatedCategory = categoryService.update(id, categoryReq, image);
            return updatedCategory.map(categoryRes -> "redirect:/categories").orElse("edit-category");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update category: " + e.getMessage());
            model.addAttribute("statuses", CategoryStatus.values());
            return "edit-category";
        }
    }

    @GetMapping("/{id}")
    public String viewCategory(@PathVariable String id, Model model) {
        Optional<CategoryRes> category = categoryService.getById(id);
        model.addAttribute("category", category.orElse(new CategoryRes()));
        return "view-category";
    }

    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable String id) {
        Optional<CategoryRes> deletedCategory = categoryService.delete(id);
        return "redirect:/categories";
    }
}
