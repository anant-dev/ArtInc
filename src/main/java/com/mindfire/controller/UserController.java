/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.controller;

import com.mindfire.DTO.ArtistDTO;
import com.mindfire.DTO.ArtistListDTO;
import com.mindfire.DTO.CategoryDTO;
import com.mindfire.DTO.DataDTO;
import com.mindfire.DTO.MyCartDTO;
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
import com.mindfire.util.MailUtil;
import com.mindfire.util.Sort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView showIndex(@ModelAttribute("message") String msg) {
        ModelAndView model = new ModelAndView("index");
        if (!msg.equals("")) {
            model.addObject("message", msg);
        }
        return model;
    }

    @RequestMapping("/home")
    public ModelAndView showHome() {
        return new ModelAndView("redirect:/");
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
    public ModelAndView showProfile(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        ModelAndView model;
        if (user == null || user.getEmail().equals("")) {
            model = new ModelAndView("error");
            model.addObject("error", "No Session");
            model.addObject("message", "Session Expired do login Again");

        } else {
            Artist ar = artistService.getArtistByUserId(user.getUser_id());
            System.out.println("details   " + ar + "---" + user.getUser_id());
            if (!ar.getArtist_name().equals("")) {
                model = new ModelAndView("artistProfile");
                model.addObject("artist", ar);
            } else {
                model = new ModelAndView("index");
            }
        }
        return model;

    }

    // redirecting to show product where product loaded using ajax by size
    @RequestMapping(value = "/sizeArt", method = RequestMethod.GET)
    public ModelAndView showSize(HttpServletRequest request) {
        int sid = Integer.parseInt(request.getParameter("sid"));
        ModelAndView model = new ModelAndView("showProduct");
        model.addObject("size", sid);
        model.addObject("type", "productBySize");
        return model;
    }

    @RequestMapping(value = "/mycart", method = RequestMethod.GET)
    public ModelAndView getMyCart(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        ModelAndView model;
        if (user == null || user.getEmail().equals("")) {
            model = new ModelAndView("error");
            model.addObject("error", "No Session");
            model.addObject("message", "Session Expired do login Again");

        } else {
            model = new ModelAndView("cart");
        }
        return model;
    }

    @RequestMapping(value = "/myorder", method = RequestMethod.GET)
    public ModelAndView getMyOrders(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        ModelAndView model;
        if (user == null || user.getEmail().equals("")) {
            model = new ModelAndView("error");
            model.addObject("error", "No Session");
            model.addObject("message", "Session Expired do login Again");

        } else {
            model = new ModelAndView("order");
        }
        return model;
    }

    /*----------------------------------------- User Controllers Started ------------------------------------------------------ */
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
                result.setMessage("Login Succssful !");
                result.setUser(us);
            } else {
                result.setCode("404");
                result.setStatus("unsuccessful");
                result.setMessage("Invalid Credentials !!");
            }
        } else {
            result.setCode("400");
            result.setStatus("unsuccessful");
            result.setMessage("User Does Not Exist Try Sigining Up ");
        }
        return result;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public UserDTO save(@ModelAttribute("user") User user, HttpServletRequest request) {
        //encrypting password using bcrypt
        String pass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(pass);
        User ur = userService.getUser(user.getEmail());
        int status = 0;
        UserDTO result = new UserDTO();
        if (ur != null) {
            status = userService.saveUser(user);
            if (status != 0) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                result.setCode("200");
                result.setMessage("User Profile Created ....!");
                result.setStatus("successful");
                result.setUser(user);
            } else {
                result.setCode("400");
                result.setStatus("unsuccessful");
                result.setMessage("There was a problem Try Again Later");
            }
        } else {
            result.setCode("400");
            result.setStatus("unsuccessful");
            result.setMessage("This email id already exists");
        }
        return result;
    }

    @RequestMapping(value = "/signupArtist", method = RequestMethod.POST)
    public ModelAndView saveArtist(String description, String place, @RequestParam("profilePic") MultipartFile files, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String path = "E:/uploads/profile_pic";
        String prefix = UUID.randomUUID().toString();
        String fileName = files.getOriginalFilename();
        ModelAndView model = new ModelAndView("redirect:/");
        Artist artist = new Artist();
        int status = 0;
        try {
            byte[] bytes = files.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path + File.separator + prefix + fileName)));
            stream.write(bytes);
            stream.flush();
            stream.close();
            String picToSave = "uploads/profile_pic/" + prefix + fileName;
            artist.setArtist_name(user.getName());
            artist.setDescription(description);
            artist.setPlace(place);
            artist.setProfile_pic(picToSave);
            artist.setNumb_img("0");
            artist.setUsr_id(user.getUser_id());
            status = artistService.saveArtist(artist);
            if (status != 0) {
                return model;
            } else {
                model.addObject("message", "Somrthing went wrong");
            }

        } catch (FileNotFoundException ex) {
            model.addObject("message", "File not found");
        } catch (IOException ex) {
            model.addObject("message", "IO Exception");
        }
        return model;
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
    public ProductListDTO getProductByArtist(String id) {
        int artist_id = Integer.parseInt(id);
        List<Product> plist = (List<Product>) productService.getProductByArtist(artist_id);
        ProductListDTO result = new ProductListDTO();
        result.setCode("200");
        result.setStatus("successfull");
        result.setPlist(plist);
        result.setMessage("product reciecved");
        return result;
    }

    //Get Product By Artist
    @RequestMapping(value = "/productBySize", method = RequestMethod.GET)
    public ProductListDTO getProductBySize(String id) {
        int size = Integer.parseInt(id);
        ProductListDTO result = new ProductListDTO();

        if (size > 0 && size < 5) {
            List<Product> plist = (List<Product>) productService.getProductBySize(size);
            if (!plist.isEmpty()) {
                result.setCode("200");
                result.setStatus("successfull");
                result.setPlist(plist);
                result.setMessage("product reciecved");
            } else {
                result.setCode("404");
                result.setStatus("successfull");
                result.setMessage("No Product Exist");
            }
        } else {
            result.setCode("400");
            result.setStatus("unsuccessful");
            result.setMessage("Bad Request : No product of this type exists");
        }
        return result;
    }

    //Get Product By Artist
    @RequestMapping(value = "/productByCategory", method = RequestMethod.GET)
    public ProductListDTO getProductByCategory(String id) {
        int category = Integer.parseInt(id);
        List<Product> plist = (List<Product>) productService.getProductByCategory(category);
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

    //product details based on product ID
    @RequestMapping(value = "/productDetails", method = RequestMethod.GET)
    public ModelAndView productDetails(HttpServletRequest request, RedirectAttributes redirect) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Product product = productService.getProductById(pid);
        ModelAndView model;
        if (product != null) {
            Category category = categoryService.getCategoryById(product.getCategory());
            int size = product.getP_size();
            String s;
            if (size == 1) {
                s = "Small";
            } else if (size == 2) {
                s = "Medium";
            } else if (size == 3) {
                s = "Large";
            } else {
                s = "Extra Large";
            }
            model = new ModelAndView("productInfo");
            model.addObject("product", product);
            if (category != null) {
                model.addObject("category", category.getName());
            } else {
                model.addObject("category", "Not Available");
            }
            model.addObject("size", s);
        } else {

            model = new ModelAndView("redirect:/");
            redirect.addFlashAttribute("message", "Product Not Found");
        }
        return model;
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

    @RequestMapping(value = "/artistArt", method = RequestMethod.GET)
    public ModelAndView getArtistById(HttpServletRequest request) {
        int aid = Integer.parseInt(request.getParameter("aid"));
        Artist ar = artistService.getArtistById(aid);
        System.out.println(" value " + request.getParameter("aid"));
        ModelAndView model = new ModelAndView("showProduct");
        model.addObject("artist", ar);
        model.addObject("type", "productByArtist");
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
    @RequestMapping(value = "/myorderitems", method = RequestMethod.GET)
    public MyCartDTO getOrder(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        MyCartDTO result = new MyCartDTO();
        if (user == null || user.getEmail().equals("")) {
            result.setCode("400");
            result.setStatus("unsuccessfull");
            result.setMessage("Bad Request : NO SESSION Avaliable");
        } else {
            List<Order> olist = orderService.getOrder(user.getUser_id(), 1);
            List<Product> plist = new ArrayList<>();
            if (olist.isEmpty()) {
                result.setCode("200");
                result.setStatus("unsuccessfull");
                result.setMessage("There are No orders");
            } else {
                for (Order od : olist) {
                    plist.add(productService.getProductById(od.getProduct_id()));
                }

                result.setCode("200");
                result.setStatus("successfull");
                result.setMessage("orders and products recieved");
                result.setOlist(olist);
                result.setPlist(plist);
            }
        }
        return result;
    }

    @RequestMapping(value = "/mycartitems", method = RequestMethod.GET)
    public MyCartDTO getMyCartItems(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        MyCartDTO result = new MyCartDTO();
        if (user == null || user.getEmail().equals("")) {
            result.setCode("400");
            result.setStatus("unsuccessfull");
            result.setMessage("Bad Request : NO SESSION Avaliable");
        } else {
            List<Order> olist = orderService.getOrder(user.getUser_id(), 0);
            List<Product> plist = new ArrayList<>();
            float price = 0;
            int item = 0;
            if (olist.isEmpty()) {
                result.setCode("200");
                result.setStatus("unsuccessfull");
                result.setMessage("There are No orders");
            } else {
                for (Order od : olist) {
                    plist.add(productService.getProductById(od.getProduct_id()));
                }
                for (Product pr : plist) {
                    item++;
                    price += Float.parseFloat(pr.getPrice());
                }
                result.setCode("200");
                result.setStatus("successfull");
                result.setMessage("orders and products recieved");
                result.setOlist(olist);
                result.setPlist(plist);
                result.setPrice(price);
                result.setItems(item);
            }
        }
        return result;
    }

    @RequestMapping(value = "/addToCart", method = RequestMethod.GET)
    public DataDTO addToCart(String product_id, HttpServletRequest request) {
        int p_id = Integer.parseInt(product_id);
        int status = 0;
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        DataDTO result = new DataDTO();
        if (user != null) {
            Order order = new Order();
            order.setProduct_id(p_id);
            order.setUser_id(user.getUser_id());
            order.setStatus(0);
            status = orderService.saveOrder(order);
            if (status == 0) {
                result.setCode("400");
                result.setStatus("unsuccessfull");
                result.setMessage("Something went wrong Try Again Later");
            } else {
                result.setCode("200");
                result.setStatus("successfull");
                result.setMessage("Item Added to Cart");
            }
        } else {
            result.setCode("200");
            result.setStatus("unsuccessful");
            result.setMessage("Please Login to Add to Cart");
        }

        return result;
    }

    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
    public ModelAndView deleteOrder(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        ModelAndView model;
        int order_id = Integer.parseInt(request.getParameter("oid"));
        if (user == null || user.getEmail().equals("")) {
            model = new ModelAndView("redirect:/");
        } else {
            int status = orderService.deleteOrder(order_id);
            model = new ModelAndView("redirect:/mycart");
        }
        return model;
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

    @RequestMapping(value = "/categoryArt", method = RequestMethod.GET)
    public ModelAndView getCategoryById(HttpServletRequest request) {
        int caid = Integer.parseInt(request.getParameter("caid"));
        Category ca = categoryService.getCategoryById(caid);
        ModelAndView model = new ModelAndView("showProduct");
        model.addObject("category", ca);
        model.addObject("type", "productByCategory");
        return model;
    }

    /*----------------------------------------- Category Controllers Ended ------------------------------------------------------ */
 /*----------------------------------------- Artist Edit Controllers starts ------------------------------------------------------ */
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    public DataDTO picSave(@RequestParam("uploadFile") MultipartFile files, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String path = "E:/uploads/profile_pic";
        String prefix = UUID.randomUUID().toString();
        String fileName = files.getOriginalFilename();
        int status = 0;
        DataDTO result = new DataDTO();
        try {
            byte[] bytes = files.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path + File.separator + prefix + fileName)));
            stream.write(bytes);
            stream.flush();
            stream.close();
            String picToSave = "uploads/profile_pic/" + prefix + fileName;
            status = artistService.updateProfilePic(picToSave, user.getUser_id());
            if (status == 0) {
                result.setCode("500");
                result.setStatus("unsuccessfull");
                result.setMessage("Something went wrong please try later");
            } else {
                result.setCode("200");
                result.setStatus("successfull");
                result.setMessage("Profile picture changed");
                result.setData(picToSave);
            }
        } catch (FileNotFoundException ex) {
            result.setCode("500");
            result.setStatus("unsuccessfull");
            result.setMessage("Choose a valid file");
        } catch (IOException ex) {
            result.setCode("400");
            result.setStatus("unsuccessfull");
            result.setMessage("BAD REQUEST");
        }
        return result;
    }

    @RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
    public ModelAndView saveProduct(@ModelAttribute Product product, @RequestParam("productPicture") CommonsMultipartFile file, HttpSession session,
            String artist_name, String artist_id) {
        int a_id = Integer.parseInt(artist_id);
        String path = "E:/uploads/products";
        String prefix = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        ModelAndView model = new ModelAndView("redirect:/myProfile");
        System.out.println("upload data" + product.getArtist_name() + product.getDescription() + product.getNumb_sold() + product.getCategory() + product.getP_size());

        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path + File.separator + prefix + fileName)));
            stream.write(bytes);
            stream.flush();
            stream.close();
            File thumbpath = new File("E:/uploads/thumbnail");
            System.out.println("above thumbnail");
            Thumbnails.of(path + File.separator + prefix + fileName)
                    .size(200, 200)
                    .toFiles(thumbpath, Rename.PREFIX_DOT_THUMBNAIL);
            String sourceImg = "uploads/products/" + prefix + fileName;
            String thumbnailImg = "uploads/thumbnail/thumbnail." + prefix + fileName;
            System.out.println("below thumbnail" + thumbnailImg);
            product.setLocation(sourceImg);
            product.setArtist_id(a_id);
            product.setArtist_name(artist_name);
            product.setThumbnail(thumbnailImg);
            productService.saveProduct(product);
            //User user = userService.editPic((int) session.getAttribute("user_id"), picToSave);

        } catch (Exception ex) {
            System.out.println("exception " + ex);
            model.addObject("message", "Something Went wrong Try Again Later");
        }
        return model;
    }

    @RequestMapping(value = "/sortProduct", method = RequestMethod.GET)
    public ProductListDTO sortProduct(int sortid) {
        List<Product> plist = (List<Product>) productService.getProduct();
        ProductListDTO result = new ProductListDTO();
        if (!plist.isEmpty()) {
            result.setCode("200");
            result.setStatus("successfull");
            if (sortid == 1) {
                Collections.sort(plist, Sort.ByPopularity);
                result.setMessage("Product Sorted By Popularity");
            } else if (sortid == 2) {
                Collections.sort(plist, Sort.ByNameAZ);
                result.setMessage("Product Sorted By Name A-Z");
            } else if (sortid == 3) {
                Collections.sort(plist, Sort.ByNameZA);
                result.setMessage("Product Sorted By Name Z-A");
            } else if (sortid == 4) {
                Collections.sort(plist, Sort.ByPriceLH);
                result.setMessage("Product Sorted By Price Low to High");
            } else if (sortid == 5) {
                Collections.sort(plist, Sort.ByPriceHL);
                result.setMessage("Product Sorted By Price High to Low");
            } else {
                result.setMessage("Unable to Sort Product Please try later .. ");
            }
            result.setPlist(plist);
        } else {
            result.setCode("200");
            result.setStatus("unsuccessfull");
            result.setMessage("Products not Recieved");
        }
        return result;
    }

    /*----------------------------------------- Artist Edit Controllers ends ------------------------------------------------------ */
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public ModelAndView sendMail(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        final String username = "artincofficial";
        final String password = "mindfire";
        if (user == null || user.getEmail().equals("")) {
            return new ModelAndView("redirect:/");
        } else {
            List<Order> olist = orderService.getOrder(user.getUser_id(), 0);
            List<String> attachments = new ArrayList<>();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String d = dateFormat.format(date);

            for (Order od : olist) {
                Product product = productService.getProductById(od.getProduct_id());
                int count = Integer.parseInt(product.getNumb_sold());
                count++;
                attachments.add(product.getLocation());
                orderService.updateOrder(od.getOrder_id(), 1, d);
                productService.increaseCount(product.getProduct_id(), Integer.toString(count));
            }

            String host = "smtp.gmail.com";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.user", "anants@mindfiresolutions.com");
            props.put("mail.password", password);
            // set the session object
            Session s = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            MailUtil.sendAttachmentEmail(s, user.getEmail(), "Your Orders From Art Inc",
                    "Please find the Products you ordered in the attachment below. Thanks For Ordering from Art Inc, Hope to see you soon Again !!", attachments);

            return new ModelAndView("redirect:/myorder");

        }

        //update order table and increase count of sold painting
    }

    @RequestMapping(value = "/getOtp", method = RequestMethod.GET)
    public DataDTO forgotMailPassword(String email, HttpServletRequest request) {

        DataDTO result = new DataDTO();
        User user = userService.getUser(email);
        final String username = "artincofficial";
        final String password = "mindfire";
        if (user == null) {
            result.setCode("200");
            result.setStatus("unsuccessfull");
            result.setMessage("User does not exist");
        } else {
            int otp = (int) (100000 + new Random().nextDouble() * 900000);
            String host = "smtp.gmail.com";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.user", "anants@mindfiresolutions.com");
            props.put("mail.password", password);
            // set the session object
            Session s = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            MailUtil.sendAttachmentEmail(s, email, "Reset Password Initiated",
                    "The 6 digit OTP to change your Password is - " + otp + "  . Visit the website and enter the OTP to change the password", null);
            result.setCode("200");
            result.setStatus("successfull");
            result.setMessage("OTP Successfully Send");
            result.setData(Integer.toString(otp));
        }
        return result;
    }

    @RequestMapping(value = "/changePass", method = RequestMethod.GET)
    public ModelAndView changePass(String newPass, String nemail, RedirectAttributes redirect, HttpServletRequest request) {
        User ur = userService.getUser(nemail);
        ModelAndView model;
        String password = BCrypt.hashpw(newPass, BCrypt.gensalt());
        if (ur != null) {
            User user = userService.changePass(nemail, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                model = new ModelAndView("redirect:/");
                redirect.addFlashAttribute("message", "Succusefully Changed Password");
            } else {
                model = new ModelAndView("redirect:/");
                redirect.addFlashAttribute("message", "Unable to change Password Try Again Later");
            }
        } else {
            model = new ModelAndView("redirect:/");
            redirect.addFlashAttribute("message", "Unable to fetch details");
        }
        return model;
    }

    @RequestMapping(value = "/saveProfile", method = RequestMethod.GET)
    public DataDTO saveProfile(String description, RedirectAttributes redirect, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        DataDTO result = new DataDTO();
        int status = 0;
        if (user != null) {
            Artist astist = artistService.getArtistByUserId(user.getUser_id());
            astist.setDescription(description);
            status = artistService.updateArtist(astist);
            if (status != 0) {
                result.setCode("200");
                result.setStatus("successful");
                result.setMessage("Updated Profile");
                result.setData(description);
            } else {
                result.setCode("400");
                result.setStatus("unsuccessful");
                result.setMessage("Something went wrong");
            }
        } else {
            result.setCode("400");
            result.setStatus("unsuccessful");
            result.setMessage("Login and Try Again !!");
        }

        return result;
    }

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
