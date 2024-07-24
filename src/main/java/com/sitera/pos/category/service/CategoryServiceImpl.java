package com.sitera.pos.category.service;

import com.sitera.pos.category.model.CategoryEntity;
import com.sitera.pos.category.model.CategoryReq;
import com.sitera.pos.category.model.CategoryRes;
import com.sitera.pos.category.repository.CategoryRepo;
import com.sitera.pos.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    @Value("${file.upload-dir}")
    private String uploadDir;

    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<CategoryRes> get() {
        List<CategoryEntity> categories = categoryRepo.findAll();
        return categories.stream()
                .map(this::convertToCategoryRes)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryRes> getById(String id) {
        Optional<CategoryEntity> categoryOptional = categoryRepo.findById(id);
        return categoryOptional.map(this::convertToCategoryRes);
    }

    @Override
    public Optional<CategoryRes> save(CategoryReq request, MultipartFile file) throws IOException {
        CategoryEntity category = convertToCategoryEntity(request);

        // Generate slug if not provided
        if (category.getSlug() == null || category.getSlug().isEmpty()) {
            category.setSlug(CommonUtil.toSlug(category.getName()));
        }

        // Save the file
        if (file != null && !file.isEmpty()) {
            String fileName = CommonUtil.getAlphaNumericString(6) + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath);
            category.setImagePath(filePath.toString());
        }

        CategoryEntity result = categoryRepo.save(category);
        return Optional.of(convertToCategoryRes(result));
    }

    @Override
    public Optional<CategoryRes> update(String id, CategoryReq request, MultipartFile file) throws IOException {
        Optional<CategoryEntity> categoryOptional = categoryRepo.findById(id);
        if (!categoryOptional.isPresent()) {
            return Optional.empty();
        }

        CategoryEntity category = categoryOptional.get();
        BeanUtils.copyProperties(request, category);

        // Generate slug if not provided
        if (category.getSlug() == null || category.getSlug().isEmpty()) {
            category.setSlug(CommonUtil.toSlug(category.getName()));
        }

        // Save the file
        if (file != null && !file.isEmpty()) {
            String fileName = CommonUtil.getAlphaNumericString(6) + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath);
            category.setImagePath(filePath.toString());
        }

        CategoryEntity result = categoryRepo.save(category);
        return Optional.of(convertToCategoryRes(result));
    }

    @Override
    public Optional<CategoryRes> delete(String id) {
        Optional<CategoryEntity> categoryOptional = categoryRepo.findById(id);
        if (!categoryOptional.isPresent()) {
            return Optional.empty();
        }

        CategoryEntity category = categoryOptional.get();
        categoryRepo.delete(category);

        return Optional.of(convertToCategoryRes(category));
    }

    private CategoryEntity convertToCategoryEntity(CategoryReq request) {
        CategoryEntity category = new CategoryEntity();
        BeanUtils.copyProperties(request, category);
        return category;
    }

    private CategoryRes convertToCategoryRes(CategoryEntity category) {
        CategoryRes categoryRes = new CategoryRes();
        BeanUtils.copyProperties(category, categoryRes);
        return categoryRes;
    }
}
