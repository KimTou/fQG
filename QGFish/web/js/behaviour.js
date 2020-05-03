
/*这个函数作用是当点击下划线英文时把注册的div容器display改为none，而登陆的div容器display改为block
从而实现当点击下划线英文时登陆注册页面的切换，不需要用到连接*/	
var TurnToLogin = document.getElementById('jump_to_denglu');
var TurnToRegister = document.getElementById('jump_to_zhuce');
var LoginContainer = document.getElementById("LoginContainer");
var RegisterContainer = document.getElementById("RegisterContainer");
var JumpToLogin = TurnToLogin.onclick = () =>{
		
	LoginContainer.style.display = "block";
	RegisterContainer.style.display = "none";
		
}

var JumpToRegister = TurnToRegister.onclick = () =>{
	
	LoginContainer.style.display = "none";
	RegisterContainer.style.display = "block";
		
}




	
	





	
	

	
