/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.controller;

import com.mindfire.DTO.ArtistDTO;
import com.mindfire.DTO.ArtistListDTO;
import com.mindfire.DTO.CategoryDTO;
import com.mindfire.DTO.OrderDTO;
import com.mindfire.DTO.OrderListDTO;
import com.mindfire.DTO.ProductDTO;
import com.mindfire.DTO.ProductListDTO;
import com.mindfire.DTO.UserDTO;
import com.mindfire.model.Artist;
import com.mindfire.model.Category;
import com.mindfire.model.Order;
import com.mindfire.model.Product;
import com.mindfire.model.User;
import com.mindfire.service.ArtistService;
import com.mindfire.service.CategoryService;
import com.mindfire.service.OrderService;
import com.mindfire.service.ProductService;
import com.mindfire.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author anants
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;
    
    @Autowired
    ProductService productService;
    
    @Autowired
    ArtistService artistService;
    
    @Autowired
    OrderService orderService;
    
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/")
    public ModelAndView showform() {
        return new ModelAndView("index");
    }
    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
//        UserDTO result = new UserDTO();
//        result.setCode("200");
//        result.setMessage("Successfully Loged Out");
//        result.setStatus("successful");
         return new ModelAndView("redirect:/");
    }
    // Show By controller
    @RequestMapping("/byArtist")
    public ModelAndView showByArtist() {
        return new ModelAndView("showByArtist");
    }
    @RequestMapping("/bySize")
    public ModelAndView showBySize() {
        return new ModelAndView("showBySize");
    }
    @RequestMapping("/byType")
    public ModelAndView showByType() {
        return new ModelAndView("showByType");
    }
    
    //My Account Controller
    @RequestMapping("/order")
    public ModelAndView showOrder() {
        return new ModelAndView("order");
    }
    @RequestMapping("/cart")
    public ModelAndView showCart() {
        return new ModelAndView("cart");
    }
    @RequestMapping("/myProfile")
    public ModelAndView showProfile() {
        return new ModelAndView("artistProfile");
    }
    /*----------------------------------------- Usser Controllers Started ------------------------------------------------------ */

    @RequestMapping(value = "/login")
    public UserDTO getStatus(String email, String password, HttpServletRequest request) {
        User us = userService.getUser(email);
        UserDTO result = new UserDTO();
        if (us != null) {
            if (BCrypt.checkpw(password, us.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", us);
                //setting data into DTO file
                result.setCode("200");
                result.setMessage("Valid User");
                result.setStatus("successful");
                result.setUser(us);
            } else {
                result.setCode("404");
                result.setStatus("unsuccessfull");
                result.setMessage("Invalid Credentials");
            }
        } else {
            result.setCode("400");
            result.setStatus("unsuccessfull");
            result.setMessage("User Does Not Exist Try Sigining Up");
        }
        return result;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public UserDTO save(@ModelAttribute("user") User user, HttpServletRequest request) {
        //encrypting password using bcrypt
        String pass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(pass);
        int status = userService.saveUser(user);
        UserDTO result = new UserDTO();
        if (status >= 1) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            result.setCode("200");
            result.setMessage("Valid User");
            result.setStatus("successful");
            result.setUser(user);
        } else {
            result.setCode("400");
            result.setStatus("unsuccessfull");
            result.setMessage("There was a problem Try Again Later");
        }
        return result;
    }

    /*----------------------------------------- User Controllers Ended ------------------------------------------------------ */

 /*----------------------------------------- Product Controllers Started ------------------------------------------------------ */
    // save product
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ProductDTO saveProduct(@ModelAttribute("product") Product product) {
        int status = productService.saveProduct(product);
        ProductDTO result = new ProductDTO();
        if (status == 1) {
            result.setCode("200");
            result.setStatus("successfull");
            result.setProduct(product);
            result.setMessage("");
        } else {
            result.setCode("400");
            result.setStatus("unsuccessfull");
            //result.setProduct(product);
            result.setMessage("Query not run");
        }
        return result;
    }

    // Get product
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ProductListDTO getProduct() {
        List<Product> plist = (List<Product>) productService.getProduct();
        ProductListDTO result = new ProductListDTO();
        result.setCode("200");
        result.setStatus("successfull");
        result.setPlist(plist);
        result.setMessage("product reciecved");
        return result;
    }

    //Get Product By Artist
    @RequestMapping(value = "/productByArtist", method = RequestMethod.POST)
    public ProductListDTO getProductByArtist(String a_id) {
        int artist_id = Integer.parseInt(a_id);
        List<Product> plist = (List<Product>) productService.getProductByArtist(artist_id);
        ProductListDTO result = new ProductListDTO();
        result.setCode("200");
        result.setStatus("successfull");
        result.setPlist(plist);
        result.setMessage("product reciecved");
        return result;
    }

    // delete product
    @RequestMapping(value = "/product", method = RequestMethod.DELETE)
    public ProductDTO deleteProduct(String p_id) {
        int product_id = Integer.parseInt(p_id);
        int status = productService.deleteProduct(product_id);
        ProductDTO result = new ProductDTO();
        if (status == 1) {
            result.setCode("200");
            result.setStatus("successfull");
            //result.setProduct(product);
            result.setMessage("");
        } else {
            result.setCode("400");
            result.setStatus("unsuccessfull");
            //result.setProduct(product);
            result.setMessage("Query not run");
        }
        return result;

    }

    @RequestMapping(value = "/product", method = RequestMethod.PUT)
    public ProductDTO updateProduct(Product product) {
        int status = productService.updateProduct(product);
        ProductDTO result = new ProductDTO();
        if (status == 1) {
            result.setCode("200");
            result.setStatus("successfull");
            //result.setProduct(product);
            result.setMessage("");
        } else {
            result.setCode("400");
            result.setStatus("unsuccessfull");
            //result.setProduct(product);
            result.setMessage("Query not run");
        }
        return result;
    }

    /*----------------------------------------- Product Controllers Ended ------------------------------------------------------ */
    
 /*----------------------------------------- Artist Controllers Started ------------------------------------------------------ */
    // Add Artist
    @RequestMapping(value = "/artist", method = RequestMethod.POST)
    public ArtistDTO saveArtist(@ModelAttribute("artist") Artist artist) {
        int status = artistService.saveArtist(artist);
        ArtistDTO result = new ArtistDTO();
        if (status == 1) {
            result.setCode("200");
            result.setStatus("successfull");
            result.setArtist(artist);
            result.setMessage("Artist added Successfully");
        } else {
            result.setCode("400");
            result.setStatus("unsuccessfull");
            //result.setProduct(product);
            result.setMessage("Query not run");
        }
        return result;
    }

    //Update Artist
    @RequestMapping(value = "/artist", method = RequestMethod.PUT)
    public ArtistDTO updateArtist(@ModelAttribute("artist") Artist artist) {
        int status = artistService.updateArtist(artist);
        ArtistDTO result = new ArtistDTO();
        if (status == 1) {
            result.setCode("200");
            result.setStatus("successfull");
            //result.setProduct(product);
            result.setMessage("");
        } else {
            result.setCode("400");
            result.setStatus("unsuccessfull");
            //result.setProduct(product);
            result.setMessage("Query not run");
        }
        return result;
    }

    //Get Artist
    @RequestMapping(value = "/artist", method = RequestMethod.GET)
    public ArtistListDTO getArtist() {
        List<Artist> alist = artistService.getArtist();
        ArtistListDTO result = new ArtistListDTO();
        result.setCode("200");
        result.setStatus("successfull");
        result.setAlist(alist);
        result.setMessage(" all artists");
        return result;
    }
    
    @RequestMapping(value = "/artist/{aid}", method = RequestMethod.GET)
    public ModelAndView getArtistById(@PathVariable int aid) {   
        Artist ar= artistService.getArtistById(aid);
        ModelAndView model= new ModelAndView("artistProfile");
        model.addObject("artist",ar);
        return model;
    }
    
    
    

    /*----------------------------------------- Artist Controllers Ended ------------------------------------------------------ */
    
    
    
    /*----------------------------------------- Order Controllers Started ------------------------------------------------------ */
    
    
    // Add Order
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public OrderDTO saveOrder(@ModelAttribute("order") Order order) {
        int status = orderService.saveOrder(order);
        OrderDTO result = new OrderDTO();
        if (status == 1) {
            result.setCode("200");
            result.setStatus("successfull");
            //result.setArtist(artist);
            result.setMessage("Order added Successfully");
        } else {
            result.setCode("400");
            result.setStatus("unsuccessfull");
            //result.setProduct(product);
            result.setMessage("Query not run");
        }
        return result;
    }
    
    //Get Order
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public OrderListDTO getOrder(String u_id) {
        int user_id= Integer.parseInt(u_id);
        List<Order> olist = orderService.getOrder(user_id);
        OrderListDTO result = new OrderListDTO();
        result.setCode("200");
        result.setStatus("successfull");
        result.setOlist(olist);
        result.setMessage(" all artists");
        return result;
    }
    
    
    
    
     /*----------------------------------------- Order Controllers Ended ------------------------------------------------------ */
    
    
    /*----------------------------------------- Category Controllers Started ------------------------------------------------------ */
    
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public CategoryDTO getCategory() {
        List<Category> ca = categoryService.getCategory();
        CategoryDTO result = new CategoryDTO();
        result.setCode("200");
        result.setStatus("successfull");
        result.setCategory(ca);
        result.setMessage(" all category");
        return result;
    }
    
    
    /*----------------------------------------- Category Controllers Ended ------------------------------------------------------ */
    
    
    
//    @RequestMapping(value = "/edit", method = RequestMethod.GET)
//    public RestWrapperDTO getEmployeeInJSON() {
//        RestWrapperDTO wrapperDTO = new RestWrapperDTO();
//        wrapperDTO.setSuccess("Successful");
//        return wrapperDTO;
//    }
//    
//   @RequestMapping(value = "/search")
//    public SearchCriteria getSearch() {
//        SearchCriteria result = new SearchCriteria();
//        result.setEmail("anant@gmail.com");
//        result.setUsername("anant");
//        return result;
//    }
//    
}
