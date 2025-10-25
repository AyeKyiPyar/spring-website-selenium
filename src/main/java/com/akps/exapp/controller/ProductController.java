package com.akps.exapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akps.exapp.model.Category;
import com.akps.exapp.model.Product;
import com.akps.exapp.service.CategoryService;
import com.akps.exapp.service.ProductService;

@Controller
public class ProductController 
{
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	// ******** product *********
		@GetMapping("/admin/product")
		public String product(Model model)
		{
			List<Product> products = productService.findAll();
			model.addAttribute("products", products);
			
			return "admin/product";
			
		}
		
		@GetMapping("/admin/product/add/form")
		public String addProductForm(Model model)
		{
			List<Category> categories = categoryService.findAll();
			
			model.addAttribute("categories",categories);
			model.addAttribute("product", new Product());
			
			return "admin/product-add";
		}
		
		
		
		public static String uploadDirectory= "/uploads";//System.getProperty("user.dir") + "/src/main/resources/static/Upload";
		
		@PostMapping("/admin/product/add/process")
		public String addProductProcess(@ModelAttribute Product product, @RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException
		{
			// upload image file to project
			String fileName = file.getOriginalFilename();
			
			Path filePath = Paths.get(uploadDirectory, fileName);
			Files.write(filePath, file.getBytes());
			
			product.setImage(fileName);
			product.setCreatedDate(new Date());
			
			Product addProduct = productService.save(product);
			
			if (addProduct != null)
			{
				redirectAttributes.addFlashAttribute("successMessage", "Product added successfully");
				return "redirect:/admin/product";
			}
			else
			{
				return "redirect:/admin/product/add/form";
			}
			
		}
		
		@GetMapping("/admin/product/edit/{id}")
		public String updateProduct(@PathVariable Integer id, Model model)
		{
			Product product = productService.findById(id);
			model.addAttribute("product", product);
			
			List<Category> categories = categoryService.findAll();
			
			model.addAttribute("categories",categories);
			
			return "admin/product-edit";
		}
		
		@PostMapping("/admin/product/edit/process")
		public String updateProductProcess(@ModelAttribute Product product,  @RequestParam MultipartFile file) throws IOException
		{
			
			
			// upload image file to project
			String fileName = file.getOriginalFilename();
					
			Path filePath = Paths.get(uploadDirectory, fileName);
			Files.write(filePath, file.getBytes());
					
			product.setImage(fileName);
			
			Product updatedProduct = productService.updateProduct(product);
			
			return "redirect:/admin/product";
		}
		
		@GetMapping("/admin/product/delete/{id}")
		public String deleteProduct(@PathVariable Integer id)
		{
			boolean isDeleted = productService.deleteById(id);
			
			return "redirect:/admin/product";
		}
}
