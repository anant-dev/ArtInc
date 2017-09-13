<%-- 
    Document   : cart
    Created on : Sep 11, 2017, 7:21:30 AM
    Author     : anants
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="bg">
            <div class="navbar-fixed">
                <nav class="nav z-depth-0">
                    <div class="nav-wrapper container">
                        <a href="#" class="brand-logo ">Art Inc.</a>
                        <ul id="nav-mobile" class="right hide-on-med-and-down">
                            <li id="navSignup"><a class="modal-trigger" href="#modal1">SignUp</a></li>
                            <li id="navLogin"><a class="modal-trigger" href="#loginModal">Login</a></li>
                            <li><a class="dropdown-button" href="#!" data-activates="dropdown1">Show By<i class="material-icons right">arrow_drop_down</i></a></li>
                            <li><a class="dropdown-button" href="#!" data-activates="dropdown2" id="myaccount">Show By<i class="material-icons right">arrow_drop_down</i></a></li>                        
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
            <li><a href="#!">Artist Name</a></li>
            <li class="divider"></li>
            <li><a href="#!">Art Size</a></li>
            <li class="divider"></li>
            <li><a href="#!">Category</a></li>
        </ul>
        <ul id="dropdown2" class="dropdown-content">
            <li><a href="#!">Artist Name</a></li>
            <li class="divider"></li>
            <li><a href="#!">Art Size</a></li>
            <li class="divider"></li>
            <li><a href="#!">Category</a></li>
        </ul>
    </body>
</html>
