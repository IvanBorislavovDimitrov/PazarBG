package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, String> {

    @Query(value = "select s from Subcategory s join s.category c where c.name like %?1%")
    List<Subcategory> findAllByCategoryNameLike(String categoryName);

    Subcategory findByName(String name);
}
