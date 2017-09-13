/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.controller;

import com.mindfire.DTO.ArtistDTO;
import com.mindfire.DTO.ArtistListDTO;
import com.mindfire.DTO.ProductDTO;
import com.mindfire.DTO.ProductListDTO;
import com.mindfire.DTO.UserDTO;
import com.mindfire.model.Artist;
import com.mindfire.model.Product;
import com.mindfire.model.User;
import com.mindfire.service.ArtistService;
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

    @RequestMapping("/")
    public ModelAndView showform() {
        return new ModelAndView("index");
    }

    /*----------------------------------------- Usser Controllers Started ------------------------------------------------------ */

    @RequestMapping(value = "/login")
    public UserDTO getStatus(String email, String password, HttpServletRequest request) {
        User us = userService.getUser(email);
        UserDTO result = new UserDTO();
        HttpSession session = request.getSession();
        session.setAttribute("user", us);
        result.setCode("200");
        result.setMessage("Valid User");
        result.setStatus("successful");
        result.setUser(us);
        return result;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public UserDTO save(@ModelAttribute("user") User user) {
        //encrypting password using bcrypt
        String pass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        System.out.println("pass   " + pass);
        user.setPassword(pass);
        userService.saveUser(user);
        UserDTO result = new UserDTO();
        result.setCode("200");
        result.setMessage("Valid User");
        result.setStatus("successful");
        result.setUser(user);
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
    @RequestMapping(value = "/productByArtist", method = RequestMethod.GET)
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
    public ArtistDTO saveProduct(@ModelAttribute("artist") Artist artist) {
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
    public ArtistDTO updateProduct(@ModelAttribute("artist") Artist artist) {
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
    public ArtistListDTO updateProduct() {
        List<Artist> alist = artistService.getArtist();
        ArtistListDTO result = new ArtistListDTO();
        result.setCode("200");
        result.setStatus("successfull");
        result.setAlist(alist);
        result.setMessage(" all artists");
        return result;
    }

    /*----------------------------------------- Artist Controllers Ended ------------------------------------------------------ */
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
