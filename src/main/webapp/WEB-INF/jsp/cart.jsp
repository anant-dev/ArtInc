<%-- 
    Document   : cart
    Created on : Sep 11, 2017, 7:21:30 AM
    Author     : anants
--%>

<%@page import="com.mindfire.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="resources/css/style.css">
    </head>
    <body>
        <!--Navbar starts here-->
        <div >
            <div class="navbar-fixed">
                <nav class="indigo darken-4 z-depth-3">
                    <div class="nav-wrapper container">
                        <a href="home" class="brand-logo ">Art Inc.</a>
                        <ul id="nav-mobile" class="right hide-on-med-and-down">
                            <%
                                User user = (User) session.getAttribute("user");
                                if (user != null) {
                            %>
                            <li><a class="dropdown-button" href="#!" data-activates="dropdown1">Show By<i class="material-icons right">arrow_drop_down</i></a></li>
                            <li><a class="dropdown-button" href="#!" data-activates="dropdown2" >My Account<i class="material-icons right">arrow_drop_down</i></a></li> 
                                <% if (user.getUser_type() == 1) {%>
                            <li><a href="myProfile">My Profile</a></li>
                                <% } %>
                            <li><a id="logout" href="logout">Logout</a></li>
                                <% }%>
                        </ul>
                    </div>
                </nav>
            </div>
             

        </div>    
        <!-- Dropdown Structure -->
        <ul id="dropdown1" class="dropdown-content">
            <li><a href="byArtist">Artist Name</a></li>
            <li class="divider"></li>
            <li><a href="bySize">Art Size</a></li>
            <li class="divider"></li>
            <li><a href="byType">Category</a></li>
        </ul>
        <ul id="dropdown2" class="dropdown-content">
            <li><a href="order">My Orders</a></li>
            <li class="divider"></li>
            <li><a href="cart">My Cart</a></li>
        </ul>
        <!--Navbar Ends here-->
        <!--        show Artists profile in cards-->
        <h4 class="center" style="padding-top: 30px"> My Cart</h4>
        <div style="display: flex;padding-left: 50px; padding-top: 30px">
            <div class="card row" style="flex: 2;padding: 20px" id="cart" >
                
            </div>

            <div style="flex: 1">
                <div class=" row ">
                    <div class="col s12">
                        <div class="card horizontal">
                            <div class="card-stacked">
                                <div class="card-content" style="padding-left: 100px">
                                    <h5 class="card-title"><b>Order Details:</b></h5>
                                    <p style="font-size: 20px;padding-top: 20px" id="items"></p>
                                    <p style="font-size: 20px;padding-top: 20px" id="price"><b> </b></p>
                                    <p style="font-size: 20px;padding-top: 20px">Shipping Charges: FREE </b></p>                                    
                                    <p class="divider indigo darken-4" style="padding-top: 5px; margin-top: 20px"></p>                               
                                    <p style="font-size: 20px;padding-top: 20px" id="t_price"><b> </b></p>
                                    <p class="divider indigo darken-4" style="padding-top: 5px; margin-top: 20px"></p>  
                                </div>
                                <div class="card-action center">
                                    <a href="checkout" class="indigo-text text-darken-4">Proceed to confirm order</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>      
        </div>


        <!--        footer starts here-->
        <footer class="page-footer indigo darken-4">
            <div class="footer-copyright">
                <div class="container">
                    © 2017 Copyright &emsp; &emsp; &emsp; &emsp; Made with <i class="fa fa-heart" aria-hidden="true"></i>	 while drinking <i class="fa fa-coffee" aria-hidden="true"></i>
                    <a class="grey-text text-lighten-4 right" href="#!">More Links</a>
                </div>
            </div>
        </footer>
        <!--        footer ends here-->
        <!--Scripts to be imported in sequence-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/typed.js/1.1.1/typed.min.js"></script>
        <script src="resources/js/main.js"></script>
        <script>
            $(document).ready(function () {
                showCartAjax();
            });
        </script>
    </body>
</html>
