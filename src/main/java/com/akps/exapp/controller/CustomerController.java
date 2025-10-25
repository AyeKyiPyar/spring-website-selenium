package com.akps.exapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akps.exapp.model.Cart;
import com.akps.exapp.model.Category;
import com.akps.exapp.model.Customer;
import com.akps.exapp.model.OrderDetail;
import com.akps.exapp.model.Orders;
import com.akps.exapp.model.Product;
import com.akps.exapp.repository.OrderRepository;
import com.akps.exapp.service.CategoryService;
import com.akps.exapp.service.CustomerService;
import com.akps.exapp.service.OrderDetailService;
import com.akps.exapp.service.OrderService;
import com.akps.exapp.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController 
{	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	// create list
	List<Cart> carts = new ArrayList<Cart>();
			
	
	
	// ******** Customer *******
	@GetMapping("/customer/index")
	public String index(Model model)
	{
		List<Product> products = productService.findAll();
		
		model.addAttribute("products", products);
		List<Category> categories = categoryService.findAll();
		
		model.addAttribute("categories",categories);			
		return "customer/index";
	}
	
	@GetMapping("/customer/login/form")
	public String loginForm(Model model)
	{
		model.addAttribute("customer", new Customer());
		
		return "customer/login";
	}
	
	@GetMapping("/customer/login/process")
	public String loginProcess(@ModelAttribute Customer customer)
	{
		System.out.println(customer.getEmail() + " Email and " + customer.getPassword());	
		
		Customer loginCustomer = customerService.findByEmailAndPassword(customer.getEmail(), customer.getPassword());
		
		if (loginCustomer != null)
		{
			session.setAttribute("loginCustomer", loginCustomer);
			
			return "redirect:/customer/index";
		}
		else
		{
			return "redirect:/customer/login/form";
		}
	}
	
	@GetMapping("/customer/signout")
	public String signOut()
	{
		// Check if the session exists
		if (session != null) 
		{
		    // Remove specific attribute from the session
		    session.removeAttribute("loginCustomer");
		    session.removeAttribute("carts");
		}
		return "redirect:/customer/index";
	}
	
	@GetMapping("/customer/register/form")
	public String registerForm(Model model)
	{
		model.addAttribute("customer", new Customer());
		
		return "customer/register";
	}
	
	@GetMapping("/customer/register/process")
	public String registerProcess(@ModelAttribute Customer customer)
	{
		customer.setLastAccessDate(new Date());
		
		Customer registerCustomer = customerService.save(customer);
		
		if (registerCustomer != null)
		{
			return "redirect:/customer/login/form";
		}
		else
		{
			return "redirect:/customer/register/form";
		}
		
	}
	
	@GetMapping("/customer/category/view/{id}")
	public String viewByCategory(@PathVariable Integer id, Model model)
	{
		List<Product> products = productService.findByCategoryId(id);		
		List<Category> categories = categoryService.findAll();
		
		model.addAttribute("products", products);
		model.addAttribute("categories",categories);
		
		return "customer/index";
		
	}
	// ******** Cart ******* //
	@GetMapping("/customer/cart/view")
	public String viewCart(Model model)
	{
		
		
		Double total = 0.0;
		
		if (session.getAttribute("carts") != null)
		{
			// retrieve list from session
			carts = (List<Cart>) session.getAttribute("carts");
		}
		
		if (carts != null)
		{
			// loop list
			for(Cart cart: carts)
			{
				total = total + cart.getPrice()* cart.getQuantity();
			}
		}
		
		// add model
		model.addAttribute("total", total);
		
		return "customer/view-cart";
	}
	
	@GetMapping("/customer/cart/add/{id}")
	public String addCart(@PathVariable Integer id)
	{
		// create list
		List<Cart> carts = new ArrayList<Cart>();
		
		Boolean flag = false;
		
		// retrieve product from db with id
		Product product = productService.findById(id);
		
		// new session
		if (session.getAttribute("carts") == null)
		{
			System.out.println("New Session");
			
			//create cart
			Cart cart = new Cart();
			
			// insert cart from product
			cart.setId(product.getId());
			cart.setName(product.getName());
			cart.setDescription(product.getDescription());
			cart.setPrice(product.getPrice());
			cart.setCreatedDate(product.getCreatedDate());
			cart.setImage(product.getImage());
			cart.setCategoryName(product.getCategory().getName());
			cart.setQuantity(1);
			
			
			
			// insert cart to list
			carts.add(cart);
			
			// insert list to session
			session.setAttribute("carts", carts);
		
		}
		else// old session
		{
			System.out.println("Old Session");
			
			// retrieve list from session
			carts = (List<Cart>) session.getAttribute("carts");
			
			// old cart
			for (Cart cart : carts)
			{
				if (cart.getId() == id)
				{
					// update quantity
					cart.setQuantity(cart.getQuantity() + 1);
					
					// flag
					flag = true;
					
					// break
					break;
				}
			}
			
			// new cart
			if (!flag)
			{
				//create cart
				Cart cart = new Cart();
				
				// insert cart from product
				cart.setId(product.getId());
				cart.setName(product.getName());
				cart.setDescription(product.getDescription());
				cart.setPrice(product.getPrice());
				cart.setCreatedDate(product.getCreatedDate());
				cart.setImage(product.getImage());
				cart.setCategoryName(product.getCategory().getName());
				cart.setQuantity(1);
						
				
				// insert cart to list
				carts.add(cart);
				// insert list to session
				session.setAttribute("carts", carts);
			}
		}
		
		
		return "redirect:/customer/index";
	}
	
	@GetMapping("/customer/cart/update/{id}")
	public String updateCart(@PathVariable Integer id,@RequestParam String action)
	{
		
		if (session.getAttribute("carts") != null)
		{
			carts = (List<Cart>) session.getAttribute("carts");
		}
		if (carts != null)
		{
			for(Cart cart : carts)
			{
				if (cart.getId() == id)
				{
					// subtract
					if (action.equalsIgnoreCase("subtract"))
					{
						cart.setQuantity(cart.getQuantity() - 1);
						
					}
					else if (action.equalsIgnoreCase("add"))// add
					{
						cart.setQuantity(cart.getQuantity() + 1);
					}
					break;
				}// end if
				
			}
		}
		
		session.setAttribute("carts", carts);
		return "redirect:/customer/cart/view";
	}
	
	//@GetMapping("/customer/cart/update")
	public String updateCart(@RequestParam Integer id, @RequestParam Integer updateQuantity)
	{
		System.out.println(updateQuantity + " update quantity");
		
		if (session.getAttribute("carts") != null)
		{
			carts = (List<Cart>) session.getAttribute("carts");
		}
		
		for(Cart cart : carts)
		{
			if (cart.getId() == id)
			{
				cart.setQuantity(updateQuantity);
				break;
			}
			
		}
		return "redirect:/customer/cart/view";
	}
	
	@GetMapping("/customer/cart/delete/{id}")
	public String deleteCart(@PathVariable Integer id)
	{
		// retrieve list from session
		List<Cart> carts = (List<Cart>) session.getAttribute("carts");
		
		// create iterator
		Iterator<Cart> it = carts.iterator();
		
		
		// to delete
		while (it.hasNext())
		{
			if (it.next().getId() == id)
			{
				it.remove();
				break;
			}
		}
		
		// add to session
		session.setAttribute("carts",carts);
		
		return "redirect:/customer/cart/view";
	}
	
	@GetMapping("/customer/invoice")
	public String invoice(Model model)
	{
		Double total = 0.0;
		Customer customer = (Customer) session.getAttribute("loginCustomer");
		
		// login user
		if (customer != null)
		{
			// 1. retrieve list from session
			carts = (List<Cart>) session.getAttribute("carts");
			
			// 2. loop --> total
			if (carts != null) 
			{
				for (Cart cart : carts)
				{
					total += (cart.getPrice() * cart.getQuantity());
				}
				
			}
			
			// 3. order object create
			Orders order = new Orders();
			
			// 4. set data to order
			order.setOrderDate(new Date());
			order.setStatus("Processing");
			order.setBillingAddress(customer.getAddress());
			order.setTotalPrice(total);
			order.setCustomer(customer);
			
			// 5. save order to database
			Orders saveOrder = orderService.save(order);
			
			// 6. loop --> orderdetail save with recent order id
			List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
			
			if (carts != null)
			{
				for (Cart cart : carts)
				{
					// create orderdetail object
					OrderDetail orderDetail = new OrderDetail();
					
					// set data to orderdetail
					orderDetail.setOrder(saveOrder);
					
					Product product = new Product();
					product.setId(cart.getId());
					product.setName(cart.getName());
					product.setPrice(cart.getPrice());
					
					orderDetail.setProduct(product);
					orderDetail.setQuantity(cart.getQuantity());
					orderDetail.setSubPrice(cart.getPrice() * cart.getQuantity());
					
					// save to database
					orderDetailService.save(orderDetail);
					
					// add to list
					orderDetails.add(orderDetail);
				}
			}
			saveOrder.setOrderDetails(orderDetails);
			
			model.addAttribute("saveOrder", orderService.findById(saveOrder.getId()));
			
			return "customer/invoice-test";
		}
		else// not login user
		{
			return "redirect:/customer/login/form";
		}
		
	}
	
	/*Double totalPrice = 0.0;
	
	List<OrderDetail> details = new ArrayList<OrderDetail>();
			
	// login user
	if (session.getAttribute("loginCustomer") != null)
	{
		// insert into order table
		
		// retrieve list from session
		carts = (List<Cart>) session.getAttribute("carts");
		
		if (carts != null)
		{
			for (Cart cart : carts)
			{
				totalPrice += (cart.getPrice() * cart.getQuantity());
			}
		}
		
		
		Orders order = new Orders();
		order.setOrderDate(new Date());
		order.setStatus("Processing");
		
		Customer customer = (Customer)session.getAttribute("loginCustomer");
		order.setCustomer(customer);
		order.setBillingAddress(customer.getAddress());
		
		order.setTotalPrice(totalPrice);
		Orders saveOrder = orderService.save(order);
		
		for (Cart cart : carts)
		{
			OrderDetail detail = new OrderDetail();
			Product product = new Product(); 
			product.setId(cart.getId());
			detail.setProduct(product);
			detail.setQuantity(cart.getQuantity());
			detail.setSubPrice(cart.getPrice() * cart.getQuantity());
			detail.setOrder(saveOrder);
				
			orderDetailService.save(detail);
		}
		
		session.removeAttribute("carts");
		
		model.addAttribute("order", order);
		
	}		
	else// not login
	{
		return "redirect:/customer/login/form";
	}
	
	
	
	return "customer/invoice-test";*/
	// ******** Customer *******

}
