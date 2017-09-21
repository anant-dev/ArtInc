<%-- 
    Document   : index
    Created on : Sep 7, 2017, 3:48:52 PM
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
        <div id="bg">
            <div class="navbar-fixed">
                <nav class="nav z-depth-0">
                    <div class="nav-wrapper container">
                        <a href="home" class="brand-logo ">Art Inc.</a>
                        <ul id="nav-mobile" class="right hide-on-med-and-down">
                            <%
                                HttpSession ss = request.getSession(false);
                                User user = (User) ss.getAttribute("user");

                                if (user == null) {
                            %>
                            <li id="navSignup"><a class="modal-trigger" href="#modal1">SignUp</a></li>
                            <li id="navLogin"><a class="modal-trigger" href="#loginModal">Login</a></li>
                            <li><a class="dropdown-button" href="#!" data-activates="dropdown1">Show By<i class="material-icons right">arrow_drop_down</i></a></li>
                            <li><a class="dropdown-button" href="#!" data-activates="dropdown2" id="myaccount">My Account<i class="material-icons right">arrow_drop_down</i></a></li>                        
                            <li><a id="myProfile" href="myProfile">My Profile</a></li>
                                <% } else {
                                    System.out.println("user id  " + user.getUser_type());
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
            <div class="container" style="padding-top: 100px">
                <h1 class="center white-text"> Art Inc.</h1>
                <h4 id="tag" class="center white-text"></h4>
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
            <li><a href="myorder">My Orders</a></li>
            <li class="divider"></li>
            <li><a href="mycart">My Cart</a></li>
        </ul>
        <!--Navbar Ends here-->

        <!--Login Modal-->
        <div id="loginModal" class="modal" >
            <h5 class="light-blue-text text-darken-4 center" style="font-size: 22px"> <i class="fa fa-lock red-text text-darken-4" aria-hidden="true" style="padding-right: 20px"></i><b> AUTHENTICATION </b></h5>
            <!--<form class="col s12">-->
            <div class="row">
                <div class="input-field col s12">
                    <input id="lemail" type="text" class="validate" name="email" required>
                    <label for="lemail">Email Address :</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <input id="lpassword" type="password" class="validate"  name="password" required>
                    <label for="lpassword">Password :</label>
                </div>
            </div>

            <div class="row">
                <div class="  col s12">
                    <label><a href="signup.html">Forget Password ?</a></label>
                </div>

            </div><br>
            <div class="row" >
                <div class="center col s12">
                    <button class="btn waves-effect waves-light light-blue darken-4 z-depth-2" id="login" name="action" style="width : 100%">Let Me In
                        <i class="fa fa-rocket right" aria-hidden="true"></i></button>
                </div>
            </div>
            <br>
            <!--</form>-->
        </div>
        <!--    <div class="carousel carousel-slider" style="height: 500px">
                <a class="carousel-item"  ><img src="resources/Images/bg1.jpg"></a>
                <a class="carousel-item"  ><img src="resources/Images/bg2.jpg"></a>
                <a class="carousel-item" ><img src="resources/Images/bg3.jpg"></a>
                    <a class="carousel-item" ><img src="resources/Images/bg4.jpg"></a>
                </div>-->
        <!--    floating Cart Action Button-->
        <div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
            <a class="btn-floating btn-large waves-effect waves-light indigo darken-4 z-depth-3" href="mycart"> <i class="fa fa-shopping-cart" aria-hidden="true"></i></a>
        </div>
        <!--    <p id="feedback"></p>-->
        <!-- SignUp Modal -->
        <div id="modal1" class="modal"  style="weidth:600px; height: auto; padding: 20px 150px  0px 150px">
            <h4 class="pink-text text-darken-2" style="font-size: 30px"><b>Sign Up :</b></h4>
            <form id="signupForm" method="post" class="col s12">
                <div class="row">
                    <div class="input-field col s12">
                        <input id="name" type="text" class="validate " name='name'>
                        <label for="name">Name *</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input id="semail" type="email" class="validate" name='semail'>
                        <label for="semail">Email *</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input id="spassword" type="password" class="validate" name='spassword'>
                        <label for="spassword">Password *</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input id="cnfpassword" type="password" class="validate" name='cnfpass'>
                        <label for="cnfpassword">Confirm Password *</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col s3">
                        <label>Gender : *</label>
                    </div>
                    <div class="col s3">
                        <input name="gender" type="radio" id="male" value="male"/>
                        <label for="male">Male</label>
                    </div>
                    <div class="col s3">
                        <input name="gender" type="radio" id="female" value="female"/>
                        <label for="female">Female</label>
                    </div>
                    <div class="col s3">
                        <input name="gender" type="radio" id="others" value="others"/>
                        <label for="others">Others</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input id="contact" type="number" class="validate" name='contact'>
                        <label for="contact">Contact Number *</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12">
                        <input type="checkbox" id="isArtist" name="isArtist"/>
                        <label for="isArtist">Do you want To be an Artist</label>
                    </div>
                </div>
                <br>
                <div class="center">
                    <button class="btn waves-effect waves-light light-blue darken-4 z-depth-2" type="submit" name="action" id="signup" style="margin-left: 30px">SignUp
                        <i class="fa fa-fighter-jet right" aria-hidden="true"></i>
                    </button>
                </div>
                <br>
            </form>
        </div>
        
        <!-- add Artist Profile Modal Starts here-->
        <div id="artist" class="modal"  style="weidth:600px; height: auto; padding: 20px 150px  0px 150px">
            <h4 class="pink-text text-darken-2" style="font-size: 30px"><b>Add Painting :</b></h4>
            <form enctype="multipart/form-data" action="signupArtist" method="post">
                <div class="row">
                    <div class="input-field col s12">
                        <input id="description" type="text" class="validate " name='description'>
                        <label for="description">Description *</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input id="place" type="text" class="validate " name='place'>
                        <label for="place">Place *</label>
                    </div>
                </div>
                
                <div class="row">
                    <div class="input-field col s12">
                        <input type="file" style="display: none" id="profilePic" name="profilePic" required="required">
                    </div>
                    <div class="input-field col s12 center">
                        <a class="waves-effect waves-light btn  blue accent-3" id="uploadProfilePic" style="width : 50%"><i class="material-icons left">file_upload</i>Upload Picture</a>
                    </div>
                </div>
                <br>
                <div class="center">
                    <button class="btn waves-effect waves-light indigo darken-4 z-depth-2" type="submit" name="action" style="width : 50%">Save Profile
                        <i class="fa fa-fighter-jet right" aria-hidden="true"></i>
                    </button>
                </div>
            </form>
            <br>
        </div>
         <!-- add Artist Profile Ends here -->
         
        <div class="container" style="padding-top: 50px;margin-bottom: 100px; height: 900px" >
            <div class="row" id="products">
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
                showProductsAjax();
            });
        </script>
    </body>
</html>
