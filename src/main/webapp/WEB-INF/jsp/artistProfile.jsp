<%-- 
    Document   : artist_profile
    Created on : Sep 11, 2017, 7:21:57 AM
    Author     : anants
--%>

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
        <div >
            <div class="navbar-fixed">
                <nav class="indigo darken-4 z-depth-3">
                    <div class="nav-wrapper container">
                        <a href="home" class="brand-logo ">Art Inc.</a>
                        <ul id="nav-mobile" class="right hide-on-med-and-down">
                            <%
                                User user = (User) session.getAttribute("user");
                                Artist artist = (Artist) request.getAttribute("artist");
                                if (user != null) {
                            %>
                            <li><a class="dropdown-button" href="" data-activates="dropdown1">Show By<i class="material-icons right">arrow_drop_down</i></a></li>
                            <li><a class="dropdown-button" href="#!" data-activates="dropdown2" >My Account<i class="material-icons right">arrow_drop_down</i></a></li> 
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
        <!--    floating Cart Action Button-->
        <div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
            <a class="btn-floating btn-large waves-effect waves-light indigo darken-4 z-depth-3 modal-trigger" href="#modal1"><i class="fa fa-plus" aria-hidden="true"></i></a>
        </div>
        <!-- add Painting Modal -->
        <div id="modal1" class="modal"  style="weidth:600px; height: auto; padding: 20px 150px  0px 150px">
            <h4 class="pink-text text-darken-2" style="font-size: 30px"><b>Add Painting :</b></h4>
            <form enctype="multipart/form-data" action="saveProduct" method="post">
                <div class="row">
                    <div class="input-field col s12">
                        <input id="title" type="text" class="validate " name='title'>
                        <label for="title">Title *</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input id="description" type="text" class="validate " name='description'>
                        <label for="description">Description *</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input id="price" type="text" class="validate " name='price'>
                        <label for="price">Price in RS *</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s6">
                        <div class="input-field col s12">
                            <select name="category" required>
                                <option value="" disabled selected>Choose a Category</option>
                                <option value="1">abstract1</option>
                                <option value="2">animal</option>
                                <option value="3">automobile</option>
                                <option value="4">celebration</option>
                                <option value="5">creative</option>
                                <option value="6">food and drink</option>
                                <option value="7">nature</option>
                                <option value="8">scenery</option>
                                <option value="9">spiritual</option>
                                <option value="10">sports</option>
                                <option value="11">vector</option>
                                <option value="12">others</option>
                            </select>
                            <label>Category Select</label>
                        </div>

                    </div>
                    <div class="input-field col s6">
                        <select name="p_size" required>
                            <option value="" disabled selected>Choose the Size of Image</option>
                            <option value="1">small</option>
                            <option value="2">medium</option>
                            <option value="3">large</option>
                            <option value="4">extra Large</option>
                        </select>
                        <label>Image Size Select</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input type="file" style="display: none" id="productPicture" name="productPicture" required="required">
                    </div>
                    <div class="input-field col s12 center">
                        <a class="waves-effect waves-light btn  blue accent-3" id="upload" style="width : 50%"><i class="material-icons left">file_upload</i>Upload Picture</a>
                    </div>
                </div>
                <input type="hidden" id="artist_id" name="artist_id" value="<%=artist.getArtist_id()%>">
                <input type="hidden" id="artist_name" name="artist_name" value="<%=artist.getArtist_name()%>">
                <br>
                <div class="center">
                    <button class="btn waves-effect waves-light indigo darken-4 z-depth-2" type="submit" name="action" style="width : 50%">Save Painting
                        <i class="fa fa-fighter-jet right" aria-hidden="true"></i>
                    </button>
                </div>
            </form>
            <br>
        </div>

        <input type="hidden" id="artisId" value="<%=artist.getArtist_id()%>">
        <div class="row container">
            <div class="col s12 m4 l4 center">
                <div class="card z-depth-5">
                    <div class="card-image ">
                        <img id="profilePic" src="<%=artist.getProfile_pic()%>" height="400px">
                        <form method="post" enctype="multipart/form-data" id="picUploadForm">  
                            <input type="file" style="display: none" id="uploadFile" name="uploadFile">
                        </form>
                        <a id="uploadPic" name="uploadPic" title="Upload Your Profile Picture" class="indigo darken-4 left btn-floating btn-large halfway-fab z-depth-5"><i class="material-icons">file_upload</i></a>

                    </div>
                </div>
            </div>


            <div class="col s12 m8 l8">
                <div class="card" style="min-height: 400px">
                    <div class="card-content about">
                        <a id="editProfile" name="editProfile" title="edit Profile" class="indigo darken-4 right btn-floating btn-large halfway-fab z-depth-5"><i class="material-icons">mode_edit</i></a>
                        <a id="editProfile" name="editProfile" title="edit Profile" class="indigo darken-4 right btn-floating btn-large halfway-fab z-depth-5" style="display:none"><i class="material-icons">save</i></a>
                        
                        <span class="card-title "><h4 class="indigo-text text-darken-4 center"><b><%=  artist.getArtist_name()%></b></h4></span>
                        <span><p class="indigo-text text-darken-4 " style="font-size: 20px;"><b>Description :</b></p></span>
                        <textarea id="description" class="materialize-textarea" style="font-size: 18px; min-height: 120px" maxlength="300" disabled> <%=artist.getDescription()%></textarea>
                        <br>
                        <h5 class="indigo-text text-darken-4 center" style="font-size: 22px;"><b>Place :</b>  <%=artist.getPlace()%></h5>
                        <h5 class="indigo-text text-darken-4 center" style="font-size: 22px;"><b>Number of Works : </b><%= artist.getNumb_img()%></h5>
                    </div>
                </div>
            </div>
        </div>


        <div class="container">
            <div class="card z-depth-3" >
                <h4 class=" indigo-text text-darken-4 center" style="padding-top: 20px"> <b>Artist Works </b></h4>           

                <div class="row" id="products" style="margin: 20px">
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
            var val = document.getElementById("artisId");
            $(document).ready(function () {
                showArtistProductAjax("productByArtist", val.value);
            });
        </script>
    </body>
</html>
