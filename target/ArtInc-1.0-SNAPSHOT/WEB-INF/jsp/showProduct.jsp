<%-- 
    Document   : showProduct
    Created on : Sep 17, 2017, 12:38:51 AM
    Author     : anants
--%>

<%@page import="com.mindfire.model.Category"%>
<%@page import="com.mindfire.model.Artist"%>
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
                                User user = (User) session.getAttribute("user");
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
            <li><a href="order">My Orders</a></li>
            <li class="divider"></li>
            <li><a href="cart">My Cart</a></li>
        </ul>
        <!--Navbar Ends here-->
        <!--    floating Cart Action Button-->
        <div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
            <a class="btn-floating btn-large waves-effect waves-light indigo darken-4 z-depth-3" href="mycart"><i class="fa fa-shopping-cart" aria-hidden="true"></i></a>
        </div>

        <!--Login Modal-->
        <div id="loginModal" class="modal" >
            <h5 class="light-blue-text text-darken-4 center" style="font-size: 22px"><b>-- LOGIN --</b></h5>
            <!--<form class="col s12">-->
            <div class="row">
                <div class="input-field col s12">
                    <input id="lemail" type="text" class="validate" name="email">
                    <label for="lemail">Email Address :</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <input id="lpassword" type="password" class="validate"  name="password">
                    <label for="lpassword">Password :</label>
                </div>
            </div>

            <div class="row">
                <div class="  col s12">
                    <label><a id="forget">Forget Password ?</a></label>
                </div>

            </div><br>
            <div class="row" >
                <div class="center col s12">
                    <button class="btn waves-effect waves-light light-blue darken-4 z-depth-2" id="login" name="action" style="width : 100%">Log In
                    </button>
                </div>
            </div>
            <br>
            <!--</form>-->
        </div>
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
            <h4 class="pink-text text-darken-2" style="font-size: 30px"><b>Artist Data  :</b></h4>
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
        <!-- Forget Modal -->
        <div id="forgetModal" class="modal"  style="weidth:600px; height: auto; padding: 20px 150px  0px 150px">
            <h4 class="pink-text text-darken-2" style="font-size: 30px"><b>Forget Password :</b></h4>
            <div class="row">
                <div class="input-field col s12">
                    <input id="femail" type="text" class="validate " name='femail'>
                    <label for="femail">Email *</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <a class="btn  waves-effect waves-light light-blue darken-4 " id="getOtp">Get OTP</a>
                </div>
            </div>

            <!--            <form id="forgetPass" method="post" class="col s12" style="display: none">-->
            <div class="row">
                <div class="input-field col s12">
                    <input id="otp" type="text" class="validate" name='otp'>
                    <label for="otp">Enter the OTP *</label>
                </div>
            </div>
            <div class="center">
                <button class="btn waves-effect waves-light light-blue darken-4 z-depth-2" type="submit" name="action" id="checkOtp" style="margin-left: 30px">Submit
                    <i class="fa fa-fighter-jet right" aria-hidden="true"></i>
                </button>
            </div>
            <br>
            <!--/fo<rm>-->
        </div>
        <input type="hidden" id="defaultOtp" value="000000">
        <!-- Change Password Modal -->
        <div id="passModal" class="modal"  style="weidth:600px; height: auto; padding: 20px 150px  0px 150px">
            <h4 class="pink-text text-darken-2" style="font-size: 30px"><b>Sign Up :</b></h4>
            <form id="changePass" method="post" class="col s12">
                <input type="hidden" id="nemail" name="nemail" value="">
                <div class="row">
                    <div class="input-field col s12">
                        <input id="newPass" type="password" class="validate" name='newPass'>
                        <label for="newPass">New Password *</label>
                    </div>
                </div>
                <div class="center">
                    <button class="btn waves-effect waves-light light-blue darken-4 z-depth-2" type="submit" name="action" style="margin-left: 30px">Submit
                        <i class="fa fa-fighter-jet right" aria-hidden="true"></i>
                    </button>
                </div>
                <br>
            </form>
        </div>
        <!--        show Artists profile in cards-->
        <% String type = (String) request.getAttribute("type");
            System.out.println("type" + type);%>   
        <input type="hidden" id="type" value="<%=type%>">


        <!--        show Artist profile in card-->
        <% if (type.equals("productByArtist")) {
                Artist artist = (Artist) request.getAttribute("artist");
                System.out.println("Artist");
        %>
        <div class="row container">
            <div class="col s12 m4 l4 center">
                <div class="card z-depth-5">
                    <div class="card-image ">
                        <img src="<%=artist.getProfile_pic()%>" height="400px">

                    </div>
                </div>
            </div>


            <div class="col s12 m8 l8">
                <div class="card" style="min-height: 400px">
                    <div class="card-content about">
                        <span class="card-title "><h4 class="indigo-text text-darken-4 center"><b><%=  artist.getArtist_name()%></b></h4></span>

                        <span><p class="indigo-text text-darken-4 center" style="font-size: 20px;"><b>Description :</b> <%=artist.getDescription()%></p></span>
                        <br>
                        <h5 class="indigo-text text-darken-4 center" style="font-size: 22px;"><b>Place :</b>  <%=artist.getPlace()%></h5>
                        <h5 class="indigo-text text-darken-4 center" style="font-size: 22px;"><b>Number of Works : </b><%= artist.getNumb_img()%></h5>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="parameter" value="<%=artist.getArtist_id()%>">
        <div class="container" style="padding-top: 50px; margin-bottom: 100px; min-height: 400px " >
            <div class="card z-depth-3" >
                <h4 class=" indigo-text text-darken-4 center" style="padding-top: 20px"> <b>Artist Works </b></h4>
                <% } else if (type.equals("productByCategory")) {
                    Category category = (Category) request.getAttribute("category");
                    System.out.println("category");
                %>
                <input type="hidden" id="parameter" value="<%=category.getC_id()%>">
                <div class="container" style="padding-top: 50px; margin-bottom: 100px; min-height: 400px" >
                    <div class="card z-depth-3" >
                        <h4 class=" indigo-text text-darken-4 center" style="padding-top: 20px"> <b>Category : <%=category.getName()%> </b></h4>
                        <% } else {
                            System.out.println("size" + request.getAttribute("size"));
                            int size = (int) request.getAttribute("size");%>
                        <input type="hidden" id="parameter" value="<%=size%>">
                        <div class="container" style="padding-top: 50px; margin-bottom: 100px; min-height: 400px" >
                            <div class="card z-depth-3" >
                                <% if (size == 1) {%>
                                <h4 class=" indigo-text text-darken-4 center" style="padding-top: 20px"> <b>Small Size Art</b></h4>
                                <%    } else if (size == 2) {
                                %><h4 class=" indigo-text text-darken-4 center" style="padding-top: 20px"> <b>Medium Size Art</b></h4>
                                <%    } else if (size == 3) {
                                %><h4 class=" indigo-text text-darken-4 center" style="padding-top: 20px"> <b>Large Size Art</b></h4>
                                <%    } else if (size == 4) {%>
                                <h4 class=" indigo-text text-darken-4 center" style="padding-top: 20px"> <b>Extra Large Size Art</b></h4>
                                <% } else {%>
                                <h4 class=" indigo-text text-darken-4 center" style="padding-top: 20px"> <b>Bad Request</b></h4>

                                <% } }%>

                                <div class="row" id="products" style="margin: 20px; min-height: 400px">
                                    <div class="col s12 center">
                                        <img src="resources/Images/loader.gif" height="100px" width="100px"/>
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
                            var val = document.getElementById("parameter");
                            var type = document.getElementById("type");
                            showProductAjax(type.value, val.value);
                        </script>
                        </body>
                        </html>