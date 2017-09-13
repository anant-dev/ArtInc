/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    $('.modal').modal();
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

$("#login").click(function (event) {
    // Disble the search button
    $("#login").prop("disabled", false);
    // Prevent the form from submitting via the browser.
    event.preventDefault();
    loginAjax();
});

$("#signup").click(function (event) {
    // Disble the search button
    $("#signup").prop("disabled", true);
    // Prevent the form from submitting via the browser.
    event.preventDefault();
    signUpAjax();
});

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
            var json = "<h4>Ajax Response</h4><pre>"
				+ JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);
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

function signUpAjax() {
    var user_type = 0;
    if($('input[name =artistCheck]:checked').val()=== 'on'){
        user_type= 1;
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
            user_type:user_type
        },
        success: function (data) {
            $('#modal1').modal('close');
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
function display() {
    //document.getElementById('#navList').removeChild(document.getElementById(input.parentNode ));
    var ul = document.getElementById('nav-mobile');
    var li1 = document.getElementById('navSignup');
    var li2 = document.getElementById('navLogin');
    ul.removeChild(li1);
    ul.removeChild(li2);
    var acc = document.getElementById('myaccount');
    acc.style.display = "block";
    var li = document.createElement('li');
    li.innerHTML = '<a class="modal-trigger" href="#">Logout</a>';
    ul.appendChild(li);
    Materialize.toast('Login Succssful !', 4000)

}

$('#artistCheck').change(function () {
    if ($(this).prop('checked')) {

    } else {
        alert("You have elected to turn off checkout history."); //not checked
    }
});
