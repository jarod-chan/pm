
(function($) {     
	//jquery 自定义插件 把数组按spring格式提交
	$.fn.formatName = function() {   
		var _options = {
    		match : "_"
        };
		var re=new RegExp(_options.match,"g"); 
    	
        this.init = function () {
        	if($(this).is("tbody")){
        		var list=$(this).find("tr");
        	}else if($(this).is("ul")){
        		var list=$(this).find("li");
        	}else{
        		var list=$(this);//传入数组本身，其它两个方法可以修改
        	}
        	list.each(function(index){
        		$(this).find('input[name*='+_options.match+'],select[name*='+_options.match+'],textarea[name*='+_options.match+']').each(function(){	
       				$(this).attr("name",$(this).attr("name").replace(re,"["+index+"]."));
        		});
        	});
        }
        this.init();
	}; 
	
	//锁定摸个按钮对象	
	$.fn.lock = function() {   
		$(this).attr("disabled","disabled");
	};  
	$.fn.unlock = function() {   
		$(this).removeAttr("disabled");
	};
	$.fn.autoLock=function(lock){
		if(lock){
			$(this).attr("disabled","disabled");
		}else{
			$(this).removeAttr("disabled");
		}
		return $(this);
	}
	$.fn.highColor=function(){
		$(this).hover(function() {
			$(this).addClass("high-color")},
			function(){
			$(this).removeClass("high-color")
		});
		return $(this);
	}
	
	
})(jQuery);  



