//jQuery time
var current_fs, next_fs, previous_fs; //fieldsets
var left, opacity, scale; //fieldset properties which we will animate
var animating; //flag to prevent quick multi-click glitches

$("input[name='next1']").click(
function(){
	
	/*var un=$("input[name='uname']").val();
	if(un.length<6){
		alert("长度不能小于6");
		return false;
	}
    var flag = true; //默认你输入的用户名是能用的。假设。
    $.ajax({
        async:false,//同步提交
        url:"login2", //执行的动作地址
        data:{username:un}, //传参数
        success:function(msg){
            if(msg=='username_error')//说明该用户名可用
            {
                flag = false;
            }
        }
    });

    if(!flag){
    	alert("该用户名已被占用");
        return false;
    }
	var ps=$("input[name='pass']").val();
	var cps=$("input[name='cpass']").val();
	if(ps == "" || ps == null){ 
	alert("密码不能为空");
	return false; 
	}
    if(ps!=cps){
		alert("两次密码不一样");
		return false;
	}*/
	if(animating) return false;
	animating = true;
	
	current_fs = $(this).parent();
	next_fs = $(this).parent().next();
	
	//activate next step on progressbar using the index of next_fs
	$("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");
	
	//show the next fieldset
	next_fs.show(); 
	//hide the current fieldset with style
	current_fs.animate({opacity: 0}, {
		step: function(now, mx) {
			//as the opacity of current_fs reduces to 0 - stored in "now"
			//1. scale current_fs down to 80%
			scale = 1 - (1 - now) * 0.2;
			//2. bring next_fs from the right(50%)
			left = (now * 50)+"%";
			//3. increase opacity of next_fs to 1 as it moves in
			opacity = 1 - now;
			current_fs.css({'transform': 'scale('+scale+')'});
			next_fs.css({'left': left, 'opacity': opacity});
		}, 
		duration: 800, 
		complete: function(){
			current_fs.hide();
			animating = false;
		}, 
		//this comes from the custom easing plugin
		easing: 'easeInOutBack'
	});
}
);
/*$("#sendEmail").click(function(){
	 $.ajax({
         url: "sendValidateEmail",
         data:{toEmail:$("#email").val()},
         async: true,
         //dataType:'text',
         success:function(msg){
             if(msg=='send_ok'){
            	 alert("验证码已经成功发送至邮箱，请查收！");            
             }else{
            	 alert("验证码发送失败！");              
             }
         }
     })
   //  return false; //阻止提交表单，只需要方法结尾添加return false;
 });*/


$(".previous").click(function(){
	if(animating) return false;
	animating = true;
	
	current_fs = $(this).parent();
	previous_fs = $(this).parent().prev();
	
	//de-activate current step on progressbar
	$("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");
	
	//show the previous fieldset
	previous_fs.show(); 
	//hide the current fieldset with style
	current_fs.animate({opacity: 0}, {
		step: function(now, mx) {
			//as the opacity of current_fs reduces to 0 - stored in "now"
			//1. scale previous_fs from 80% to 100%
			scale = 0.8 + (1 - now) * 0.2;
			//2. take current_fs to the right(50%) - from 0%
			left = ((1-now) * 50)+"%";
			//3. increase opacity of previous_fs to 1 as it moves in
			opacity = 1 - now;
			current_fs.css({'left': left});
			previous_fs.css({'transform': 'scale('+scale+')', 'opacity': opacity});
		}, 
		duration: 800, 
		complete: function(){
			current_fs.hide();
			animating = false;
		}, 
		//this comes from the custom easing plugin
		easing: 'easeInOutBack'
	});
});

$(".submit").click(function(){
	var un=$("input[name='yname']").val();
	if(un==""||un==null){
		alert("真实姓名不能为空");
		return false;
	}

	var formParam = $("#verifyCheck").serialize();
        /*]]>*/
        $.ajax({
            url:"reg1",
            data:$("#msform").serialize(),
            async:true,          
            success:function(resp){
                //layer.msg(resp);
                if(resp=='reg_success'){
                	alert("注册成功！");
                   

                    window.location.href="test2";
                }else if(resp=='reg_validate_error'){
                    alert("验证码不正确,注册失败！");
                }
                else{
                    alert("注册失败！");
                }
            }
        })


        return false;
    });

