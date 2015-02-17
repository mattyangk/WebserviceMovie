<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp" />
<html>
    <head>
        <title>What's the movie'</title>
        <meta name="keywords" content="jquery" />
        <meta name="description" content="jquery for home page" />
        <meta charset="utf-8">
            <link href="css/home.css?v=2" rel="stylesheet" type="text/css" />
            <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
    </head>
    <body>
        <div class="wrap">
            <div class="banner-show" id="js_ban_content">
                <div class="cell bns-01">
                    <div class="con"> </div>
                </div>
                <div class="cell bns-02" style="display:none;">
                    <div class="con">  <i></i></a> </div>
                </div>
                <div class="cell bns-03" style="display:none;">
                    <div class="con">  <i></i></a> </div>
                </div>
            </div>
            <div class="banner-control" id="js_ban_button_box"> <a href="javascript:;" class="left">left</a> <a href="javascript:;" class="right">right</a> </div>
            <script type="text/javascript">
                ;(function(){
                  
                  var defaultInd = 0;
                  var list = $('#js_ban_content').children();
                  var count = 0;
                  var change = function(newInd, callback){
                  if(count) return;
                  count = 2;
                  $(list[defaultInd]).fadeOut(400, function(){
                                              count--;
                                              if(count <= 0){
                                              if(start.timer) window.clearTimeout(start.timer);
                                              callback && callback();
                                              }
                                              });
                  $(list[newInd]).fadeIn(400, function(){
                                         defaultInd = newInd;
                                         count--;
                                         if(count <= 0){
                                         if(start.timer) window.clearTimeout(start.timer);
                                         callback && callback();
                                         }
                                         });
                  }
                  
                  var next = function(callback){
                  var newInd = defaultInd + 1;
                  if(newInd >= list.length){
                  newInd = 0;
                  }
                  change(newInd, callback);
                  }
                  
                  var start = function(){
                  if(start.timer) window.clearTimeout(start.timer);
                  start.timer = window.setTimeout(function(){
                                                  next(function(){
                                                       start();
                                                       });
                                                  }, 8000);
                  }
                  
                  start();
                  
                  $('#js_ban_button_box').on('click', 'a', function(){
                                             var btn = $(this);
                                             if(btn.hasClass('right')){
                                             //next
                                             next(function(){
                                                  start();
                                                  });
                                             }
                                             else{
                                             //prev
                                             var newInd = defaultInd - 1;
                                             if(newInd < 0){
                                             newInd = list.length - 1;
                                             }
                                             change(newInd, function(){
                                                    start();
                                                    });
                                             }
                                             return false;
                                             });
                  
                  })();
                </script>
            <div class="container">
                <form method="post" action="LoginAction.do">
                <div class="register-box">
                    <div class="reg-slogan"> Welcome to what's the movie!</div>
                    <div class="reg-form" id="js-form-mobile"> <br>
                        <br>
                        <span><h3>Username</h3></span>

                        <div class="cell">
                           
                            <input type="text" name="username" id="js-mobile_ipt" class="text" maxlength="11" />
                        </div>
                        <span><h3>Password</h3></span>
                        <div class="cell">
                           
                            <input type="password" name="password" id="js-mobile_pwd_ipt" class="text" />
                            
                        </div>
                        
                        
                        <div class="bottom"> <input class="button btn-green" type="submit" value= "Log in"></div>
                    </div>
                    
                </div>
               </form>
            </div>
        </div>
    </body>
</html>
