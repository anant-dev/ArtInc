/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    $('.modal').modal();
    $(".dropdown-button").dropdown();
    $('select').material_select();
    $('#description').trigger('autoresize');
    $(window).scroll(function () {
        if ($(window).scrollTop() >= 500) {
            $('.nav').css('background', '#1a237e');
        } else {
            $('.nav').css('background', 'transparent');
        }
    });

});
setInterval(function () {
    $('.carousel').carousel('next');
}, 5000);
//SCROLLSPY
//        $(document).ready(function () {
//            $('.scrollspy').scrollSpy();
//        });

//for typing string in loop using type.js
$(function () {
    $("#tag").typed({
        strings: [" Where Art Meets People "],
        typeSpeed: 100,
        backSpeed: 10,
        backDelay: 2000,
        showCursor: false,
        loop: true
    });
});

//for login button click
$("#login").click(function (event) {
    // Disble the search button
    $("#login").prop("disabled", true);
    // Prevent the form from submitting via the browser.
    event.preventDefault();
    if(isEmail($("#lemail").val())){
        if(isPass($('#lpassword').val())){
            loginAjax();
        }
        else{
            $("#login").prop("disabled", false);
        }
    }else{
        Materialize.toast("Invalid email format use abc@xyz.com pattern",4000);
        $("#login").prop("disabled", false);
    }
    
});

//successful
//unsuccessful


// Ajax call for login
function loginAjax() {
    $.ajax({
        type: "GET",
        url: "login",
        data: {
            email: $("#lemail").val(),
            password: $('#lpassword').val(),
        },
        success: function (data) {
            $('#loginModal').modal('close');
            $("#login").prop("disabled", false);
            if (data.status === "successful") {
                display(data);
            }else{
                Materialize.toast(data.message,4000);
                $("#lemail").val("");
                $("#lpassword").val("");
            }
                
        },
        error: function (e) {
            Materialize.toast("ERROR: "+e,4000);
        },
        done: function (e) {
             Materialize.toast(" Done ",4000);
            enableSearchButton(true);
        }
    });


}
//$( "signupForm" ).submit(function( event )
// for signup button click
$("#signup").click(function (event){
    // Disble the search button
    $("#signup").prop("disabled", true);
    // Prevent the form from submitting via the browser.
    event.preventDefault();
    signUpUserAjax();
});

function signUpUserAjax() {
    var user_type = 0;
    if ($('input[name = isArtist]:checked').val() === 'on'){
        user_type = 1;
    }
    $.ajax({
        type: "POST",
        url: "signup",
        data: {
            name: $("#name").val(),
            email: $("#semail").val(),
            password: $('#spassword').val(),
            contact: $("#contact").val(),
            gender: $('input[name=gender]:checked').val(),
            user_type: user_type
        },
        success: function (data) {
            $('#modal1').modal('close');
            if(user_type === 1){
                $('#artist').modal('open');
            }
            if (data.status === "successful") {
                display(data);
            }
        },
        error: function (e) {
            console.log("ERROR: ", e);
            display(e);
        },
        done: function (e) {
            console.log("DONE");
            enableSearchButton(true);
        }
    });
}

$("#uploadProfilePic").click(function (event) {
    event.preventDefault();
    $("#profilePic:hidden").trigger("click");
});
$("#profilePic:hidden").change(function () {
    document.getElementById('profilePic').style.display = "block";
});

//function signUpArtistAjax() {
//    var form = $('#signupForm')[0];
//    var data = new FormData(form);
//    $.ajax({
//        type: "POST",
//        enctype:'multipart/form-data',
//        url: "signupArtist",
//        processData: false,
//        contentType: false,
//        cache: false,
//        timeout: 600000,
//        data: data,
//        success: function (data) {
//            $('#modal1').modal('close');
//            if (data.status === "successful") {
//                display(data);
//            }
//        },
//        error: function (e) {
//            console.log("ERROR: ", e);
//            display(e);
//        },
//        done: function (e) {
//            console.log("DONE");
//            enableSearchButton(true);
//        }
//    });
//}

// validation
function isEmail(email) {
  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  return regex.test(email);
}
function isPass(pass) {
  if (pass === "") {
        Materialize.toast('Password is compulsory', 4000);
        return false;
    }
  else if (pass.length > 12 || pass.length < 4) {
        Materialize.toast('Password should have 4 - 12 characters', 4000);
        return false;
    }
  else{
        return true;
  }
}



// on signup and login successfull
function display(data) {
    //document.getElementById('#navList').removeChild(document.getElementById(input.parentNode ));
    var ul = document.getElementById('nav-mobile');
    var li1 = document.getElementById('navSignup');
    var li2 = document.getElementById('navLogin');
    ul.removeChild(li1);
    ul.removeChild(li2);
    var acc = document.getElementById('myaccount');
    acc.style.display = "block";
    if (data.user.user_type === 1) {
        document.getElementById('myProfile').style.display = "block";
    }
    var li = document.createElement('li');
    li.innerHTML = '<a class="modal-trigger" href="logout">Logout</a>';
    ul.appendChild(li);
    Materialize.toast(data.message, 4000);
}

$('#artistCheck').change(function () {
    if ($(this).prop('checked')) {

    } else {
        alert("You have elected to turn off checkout history."); //not checked
    }
});

//$("#logout").click(function (event) {
//    event.preventDefault();
//    $.ajax({
//        type: "GET",
//        url: "logout",
//        data: {
//        },
//        success: function (data) {
//            if (data.status === "successful") {
//                Materialize.toast(data.message, 4000)
//            }
//        },
//        error: function (e) {
//            console.log("ERROR: ", e);
//            display(e);
//        },
//        done: function (e) {
//            console.log("DONE");
//            enableSearchButton(true);
//        }
//    });
//});

//$("#editProfile").click(function (event) {
//    
//}
//
//




// show prodct on index page
function showProductsAjax() {
    $.ajax({
        type: "GET",
        url: "product",
        data: {
        },
        success: function (data) {
            if (data.status === "successfull") {
                showProduct(data);
            }
        },
        error: function (e) {
            alert("ERROR: ", e);
        },
        done: function (e) {
            alert("DONE", e);
        }
    });
}
function showProduct(data) {
    $.each(data.plist, function (idx, obj) {
        $('#products').append('<div class="col s3">\n\
                                <div class="card" id="' + obj.product_id + '">\n\
                                    <div class="card-image">\n\
                                        <img src="' + obj.thumbnail + '" style="height:200px">\n\
                                            <a class="btn-floating btn-large halfway-fab waves-effect waves-light indigo darken-4 cart">\n\
                                            <i class="fa fa-shopping-cart" aria-hidden="true"></i></a>\n\
                                    </div>\n\
                                    <div class="card-content">\n\
                                        <span class="card-title">' + obj.title + '</span>\n\
                                        <p class="counter" style="font-size: 15px;"> By <a href="artistArt?aid='+obj.artist_id+'">' + obj.artist_name + '</a></p>\n\
                                        <p class="counter" style="font-size: 25px;">' + obj.price + '<span class="unit" style="font-size: 12px;"> RS</span>\n\
                                        <p><a href="productDetails?pid='+obj.product_id+'">More Info</a></p>\n\
                                    </div>\n\
                                </div>\n\
                            </div>');
    });
}

function showArtistAjax() {
    $.ajax({
        type: "GET",
        url: "artist",
        data: {
        },
        success: function (data) {
            if (data.status === "successfull") {
                showArtist(data);
            }
        },
        error: function (e) {
            alert("ERROR: ", e);
        },
        done: function (e) {
            alert("DONE", e);
        }
    });
}

function showArtist(data) {
    $.each(data.alist, function (idx, obj) {
        $('#artists').append('<div class="col s3">\n\
                                <div class="card" id="' + obj.artist_id + '">\n\
                                    <div class="card-image">\n\
                                        <img src="' + obj.profile_pic + '" style="height:200px">\n\
                                    </div>\n\
                                    <div class="card-content">\n\
                                        <span class="card-title">' + obj.artist_name + '</span>\n\
                                        <p class="counter" style="font-size: 15px;"> From : ' + obj.place + '</p>\n\
                                        <p><a href="artistArt?aid=' + obj.artist_id + '">Show Profile</a></p>\n\
                                    </div>\n\
                                </div>\n\
                            </div>');
    });
}

function showCategoryAjax() {
    $.ajax({
        type: "GET",
        url: "category",
        data: {
        },
        success: function (data) {
            if (data.status === "successfull") {
                showCategory(data);
            }
        },
        error: function (e) {
            alert("ERROR: ", e);
        },
        done: function (e) {
            alert("DONE", e);
        }
    });
}

function showCategory(data) {
    $.each(data.category, function (idx, obj) {
        $('#category').append('<div class="col s3">\n\
                                <div class="card" id="' + obj.c_id + '">\n\
                                    <div class="card-image">\n\
                                        <img src="' + obj.image + '" style="height:200px">\n\
                                    </div>\n\
                                    <div class="card-content">\n\
                                        <span class="card-title">' + obj.name + '</span>\n\
                                        <p><a href="categoryArt?caid=' + obj.c_id + '">Show Profile</a></p>\n\
                                    </div>\n\
                                </div>\n\
                            </div>');
    });
}

function showProductAjax(url, id) {
    $.ajax({
        type: "GET",
        url: url,
        data: {
            id: id
        },
        success: function (data) {
            if (data.status === "successfull") {
                showProducts(data);
            }
        },
        error: function (e) {
            alert("ERROR: ", e);
        },
        done: function (e) {
            alert("DONE", e);
        }
    });
}
function showProducts(data) {
    $.each(data.plist, function (idx, obj) {
        $('#products').append('<div class="col s3">\n\
                                <div class="card" id="' + obj.product_id + '">\n\
                                    <div class="card-image">\n\
                                        <img src="' + obj.thumbnail + '" style="height:200px">\n\
                                            <a class="btn-floating btn-large halfway-fab waves-effect waves-light indigo darken-4 cart">\n\
                                            <i class="fa fa-shopping-cart" aria-hidden="true"></i></a>\n\
                                    </div>\n\
                                    <div class="card-content">\n\
                                        <span class="card-title">' + obj.title + '</span>\n\
                                        <p class="counter" style="font-size: 15px;"> By <a href="artistArt?aid='+obj.artist_id+'">' + obj.artist_name + '</a></p>\n\
                                        <p class="counter" style="font-size: 25px;">' + obj.price + '<span class="unit" style="font-size: 12px;"> RS</span>\n\
                                        <p><a href="productDetails?pid='+obj.product_id+'">More Info</a></p>\n\
                                    </div>\n\
                                </div>\n\
                            </div>');
    });
}

function showArtistProductAjax(url, id) {
    $.ajax({
        type: "GET",
        url: url,
        data: {
            id: id
        },
        success: function (data) {
            if (data.status === "successfull") {
                showArtistProducts(data);
            }
        },
        error: function (e) {
            alert("ERROR: ", e);
        },
        done: function (e) {
            alert("DONE", e);
        }
    });
}
function showArtistProducts(data) {
    $.each(data.plist, function (idx, obj) {
        $('#products').append('<div class="col s3">\n\
                                <div class="card" id="' + obj.product_id + '">\n\
                                    <div class="card-image">\n\
                                        <img src="' + obj.thumbnail + '" style="height:200px">\n\
                                    </div>\n\
                                    <div class="card-content">\n\
                                        <span class="card-title">' + obj.title + '</span>\n\
                                        <p class="counter" style="font-size: 15px;"> By ' + obj.artist_name + '</p>\n\
                                        <p class="counter" style="font-size: 25px;">' + obj.price + '<span class="unit" style="font-size: 12px;"> RS</span>\n\
                                        <p><a href="productDetails?pid='+obj.product_id+'">More Info</a></p>\n\
                                    </div>\n\
                                </div>\n\
                            </div>');
    });
}

function showCartAjax() {
    $.ajax({
        type: "GET",
        url: "mycartitems",
        data: {
        },
        success: function (data) {
            if (data.status === "successfull") {
                showCart(data);
            }
        },
        error: function (e) {
            alert("ERROR: ", e);
        },
        done: function (e) {
            alert("DONE", e);
        }
    });
}
function showCart(data) {
    $( "#items" ).html( "Total Items:  "+data.items );
    $( "#price" ).html( "SubTotal :  "+data.price );
    $( "#t_price" ).html( "Total Amout To be Paid :  "+data.price );
    $.each(data.plist, function (idx, obj) {
        $('#cart').append('<div class="col s12 m12">\n\
                                    <div class="card horizontal" id="' + data.olist[idx].order_id + '">\n\
                                        <div class="card-image">\n\
                                            <img src="' + obj.thumbnail + '" style="height: 200px; width: 200px;">\n\
                                        </div>\n\
                                        <div class="card-stacked">\n\
                                            <div class="card-content" style="padding-left: 100px">\n\
                                                <a class="waves-effect waves-light right" id="delete" href="deleteOrder?oid=' + data.olist[idx].order_id + '"><i class="material-icons">delete</i></a>\n\
                                                <h5>' + obj.title + '</h5>\n\
                                                <p class="counter" style="font-size: 18px;"> By <a href="artistArt?aid='+obj.artist_id+'">' + obj.artist_name + '</a></p>\n\
                                                <p class="counter" style="font-size: 20px;">Price : ' + obj.price + '<span class="unit" style="font-size: 12px;"> RS</span>\n\
                                            </div>\n\
                                        </div>\n\
                                    </div>\n\
                                </div>');
    });
    
    } 
    function showOrderAjax() {
    $.ajax({
        type: "GET",
        url: "myorderitems",
        data: {
        },
        success: function (data) {
            if (data.status === "successfull") {
                showOrder(data);
            }
        },
        error: function (e) {
            alert("ERROR: ", e);
        },
        done: function (e) {
            alert("DONE", e);
        }
    });
}
function showOrder(data) {
    $.each(data.plist, function (idx, obj) {
        $('#order').append('<div class="col s12 m12">\n\
                                    <div class="card horizontal" id="' + data.olist[idx].order_id + '">\n\
                                        <div class="card-image">\n\
                                            <img src="' + obj.thumbnail + '" style="height: 200px; width: 200px;">\n\
                                        </div>\n\
                                        <div class="card-stacked">\n\
                                            <div class="card-content" style="padding-left: 100px">\n\
                                                <h5>' + obj.title + '</h5>\n\
                                                <p class="counter" style="font-size: 18px;"> By <a href="artistArt?aid='+obj.artist_id+'">' + obj.artist_name + '</a></p>\n\
                                                <p class="counter" style="font-size: 20px;">Price : ' + obj.price + '<span class="unit" style="font-size: 12px;"> RS</span>\n\
                                            </div>\n\
                                        </div>\n\
                                    </div>\n\
                                </div>');
    });
    
    
}

//upload profile picture
$("#uploadPic").click(function (event) {
    event.preventDefault();
    $("#uploadFile:hidden").trigger("click");

});

$("#uploadFile:hidden").change(function () {
    uploadAjax();
});

function uploadAjax() {
    var form = $('#picUploadForm')[0];
    var data = new FormData(form);
    var a_id= $("#artisId:hidden").val();
    var img = document.getElementById("profilePic");
     $.ajax({
        type: "POST",
        enctype:'multipart/form-data',
        url: "uploadPic",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            img.src = data.data;
            Materialize.toast(data.message,4000);
        },
        error: function (e) {
            alert("error");
        },
        done: function (e) {

        }
    });
}
$("#upload").click(function (event) {
    event.preventDefault();
    $("#productPicture:hidden").trigger("click");

});

$("#productPicture:hidden").change(function () {
    document.getElementById("productPicture").style.display = "block";
    document.getElementById("upload").style.display = "none";
    
    //uploadAjax();
});

// Add to cart functionality
$("#products").on("click", "a.cart", function (event) {
    event.preventDefault();
    var id = $(this).parent().parent().attr("id");
    $.ajax({
        type: "GET",
        url: "addToCart",
        data: {
            product_id : id
        },
        success: function (data) {
            Materialize.toast(data.message, 4000);
        },
        error: function (e) {
            Materialize.toast("There was an error performing operation", 4000);
        },
        done: function (e) {
            alert(e);
        }

    });
});