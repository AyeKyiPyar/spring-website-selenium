package com.akps.exapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akps.exapp.model.Category;
import com.akps.exapp.service.CategoryService;

@Controller
public class CategoryController 
{
	@Autowired
	private CategoryService categoryService;
	
	// ******** category *********
		@GetMapping("/admin/category")
		public String category(Model model)
		{
			List<Category> categories = categoryService.findAll();
			model.addAttribute("categories",categories);
			
			return "admin/category";
		}
		
		@GetMapping("/admin/category/add/form")
		public String addCategory(Model model)
		{
			model.addAttribute("category", new Category());
			
			return "admin/category-add";
		}
		
		@GetMapping("/admin/category/add/process")
		public String addCategoryProcess(@ModelAttribute Category category, RedirectAttributes redirectAttributes)
		{
			Category addCategory = categoryService.save(category);
			
			if (addCategory != null)
			{
				redirectAttributes.addFlashAttribute("successMessage", "Category added");;

				return "redirect:/admin/category";	
			}
			else
			{
				return "redirect:/admin/category/add/form";
			}
		}
		
		@GetMapping("/admin/category/edit/{id}")
		public String updateCategory(@PathVariable Integer id, Model model)
		{
			Category category = categoryService.findById(id);
			
			model.addAttribute("category", category);
			return "admin/category-edit";
		}
		
		@GetMapping("/admin/category/edit/process")
		public String updateCategoryProcess(@ModelAttribute Category category)
		{
			Category updatedCategory = categoryService.updateCategory(category);
			return "redirect:/admin/category";
		}
		
		@GetMapping("/admin/category/delete/{id}")
		public String deleteCategory(@PathVariable Integer id)
		{
			boolean isDeleted = categoryService.deleteById(id);
			return "redirect:/admin/category";
		}

}
