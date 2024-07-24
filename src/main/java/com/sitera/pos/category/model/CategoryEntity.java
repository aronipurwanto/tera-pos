package com.sitera.pos.category.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_category")
public class CategoryEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "slug", nullable = false, length = 20)
    private String slug;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "parent_id", length = 36)
    private String parentId;

    @Column(name = "description", length = 225)
    private String description;

    @Column(name = "status", nullable = false, length = 10)
    private CategoryStatus status;
}
