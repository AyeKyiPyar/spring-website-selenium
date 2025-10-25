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

import com.akps.exapp.model.Admin;
import com.akps.exapp.model.Category;
import com.akps.exapp.model.Orders;
import com.akps.exapp.model.Product;
import com.akps.exapp.service.AdminService;
import com.akps.exapp.service.CategoryService;
import com.akps.exapp.service.OrderService;
import com.akps.exapp.service.ProductService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class AdminController 
{
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	
	//******* admin ********
	
	@GetMapping("/admin")
	public String loginForm(Model model) 
	{
		model.addAttribute("admin", new Admin());
		
		return "admin/login";
	}
	
	@PostMapping("/admin/login/process")
	public String loginProcess(@ModelAttribute Admin admin) 
	{
		Admin loginAdmin = adminService.findByEmailAndPassword(admin.getEmail(), admin.getPassword());
		
		if (loginAdmin != null)
		{
			session.setAttribute("loginAdmin", loginAdmin);
			
			return "redirect:/dashboard";
		}
		else 
		{
			return "redirect:/admin";
		}
	}
	
	@GetMapping("/dashboard")
    public String dashboard() 
	{
        return "admin/dashboard";
    }
	
	@GetMapping("/admin/login/process")
	public String loginProcess() 
	{
		Admin loginAdmin = (Admin) session.getAttribute("loginAdmin");
		
		if (loginAdmin != null)
		{
			session.setAttribute("loginAdmin", loginAdmin);
			
			return "redirect:/dashboard";
		}
		else 
		{
			return "redirect:/admin";
		}
	}
	
	
	
	
	
	// ****** Users ********
	
	@GetMapping("/admin/customer")
	public String customer()
	{
		return "admin/customer";
	}
	
	// ****** Order ********
	
	@GetMapping("/admin/order")
	public String order(Model model)
	{
		List<Orders> orders = orderService.findAll();
		model.addAttribute("orders", orders);
		return "admin/order";
	}
}
