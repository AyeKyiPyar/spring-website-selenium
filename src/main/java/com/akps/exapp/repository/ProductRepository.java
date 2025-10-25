package com.akps.exapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akps.exapp.model.Product;

import jakarta.transaction.Transactional;


public interface ProductRepository extends JpaRepository<Product, Integer>
{
	//@Query(nativeQuery = true, value = "select * from Product where category_id = :id")
	@Query("select p from Product p where p.category.id = :id")
	List<Product> findByCategoryId(@Param("id") Integer id);
	
	@Modifying
	@Transactional
	@Query("update Product p set p.category.id = null where p.category.id = :id")
	void updateCategoryToNull(@Param("id") Integer id);
	
	
	/*@Modifying
    @Transactional
	@Query("delete from Product p where p.category.id = :id")
	void deleteByCategoryId(@Param("id") Integer id);
	*/
	
	

}
