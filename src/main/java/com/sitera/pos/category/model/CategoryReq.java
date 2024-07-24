package com.sitera.pos.category.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReq {
    private String id;
    private String name;
    private String slug;
    private String imagePath;
    private String parentId;
    private String description;
    private CategoryStatus status;
}
