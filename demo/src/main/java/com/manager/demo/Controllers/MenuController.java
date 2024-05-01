package com.manager.demo.Controllers;

import OrderManagement.Cart;
import OrderManagement.CartItem;
import com.manager.demo.Models.CustomerInfo;
import com.manager.demo.Models.FoodItem;
import com.manager.demo.Repos.FoodItemRepo;
import com.manager.demo.Services.FoodItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {
    private FoodItemService foodItemService;
    @Autowired
    private HttpSession session;

    public MenuController(FoodItemService foodItemService)
    {
        this.foodItemService = foodItemService;
    }


    @GetMapping("/menu")
    public String getCustomerMenu(@RequestParam(name="search", required = false) String searchQuery, Model model) {

        List<FoodItem> items;
        List<String> categories;
        CustomerInfo c = (CustomerInfo) session.getAttribute("user");
        if(c == null) {
            return "redirect:/login";
        }

        if (searchQuery != null && !searchQuery.isEmpty()) {
            items = foodItemService.searchFoodItems(searchQuery);
            categories = foodItemService.getCategoryOfSearchedItems(searchQuery);
        } else {
            items = foodItemService.getAllFoodItems();
            categories = foodItemService.getAllCategories();
        }

        model.addAttribute("items", items);
        model.addAttribute("categories", categories);

        return "Customer/menu";
    }

    @PostMapping("/addToCart/{itemId}")
    public String addItemToCart(@PathVariable("itemId") Long itemId, Model model) {
        FoodItem item = foodItemService.getItemById(itemId).get();
        Cart cart = (Cart) session.getAttribute("cart");
        cart.addItem(item, 1);
        System.out.println(cart.getTotalPrice());
        return getCustomerMenu(null, model);
    }

    @GetMapping("/cart")
    public String displayCart(Model model) {
        if(session.getAttribute("user") == null) {
            return  "redirect:/login";
        }
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        return "Customer/Cart";
    }

    @PostMapping("/increaseItem/{itemId}")
    public String increaseItemQunatity(@PathVariable("itemId") Long itemId) {
        FoodItem item = foodItemService.getItemById(itemId).get();
        Cart cart = (Cart) session.getAttribute("cart");
        cart.addItem(item, 1);
        return "redirect:/cart";
    }

    @PostMapping("/decreaseItem/{itemId}")
    public String decreaseItemQunatity(@PathVariable("itemId") Long itemId) {
        FoodItem item = foodItemService.getItemById(itemId).get();
        Cart cart = (Cart) session.getAttribute("cart");
        cart.removeItem(item);
        return "redirect:/cart";
    }


}
