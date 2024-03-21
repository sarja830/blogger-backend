package com.blog.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "category name cannot be empty")
    private String name;
//    @OneToOne
//    @JoinColumn(name = "parent_id")
//    private Comment parentCommentId;

//    @ManyToOne
//    @JoinColumn(name = "parent_id")

    private Long parentId;

//    @OneToMany(mappedBy = "parentCategory",
//            cascade = { CascadeType.REMOVE, CascadeType.PERSIST} )
//    private Set<Category> children = new HashSet<>();

}
