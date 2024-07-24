package com.sitera.pos.category.service;

import com.sitera.pos.category.model.CategoryReq;
import com.sitera.pos.category.model.CategoryRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryRes> get();
    Optional<CategoryRes> getById(String id);
    Optional<CategoryRes> save(CategoryReq request, MultipartFile file) throws Exception;
    Optional<CategoryRes> update(String id, CategoryReq request, MultipartFile file) throws Exception;
    Optional<CategoryRes> delete(String id);
}
