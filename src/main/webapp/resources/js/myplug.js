//jquery 自定义插件 把数组按spring格式提交
(function($) {     
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
	
	$.fn.lock = function() {   
		$(this).attr("disabled","disabled");
	};  
	$.fn.unlock = function() {   
		$(this).removeAttr("disabled");
	};
	
})(jQuery);  

//判断两个对象相等
Object.equals = function( x, y ) {
    // If both x and y are null or undefined and exactly the same
    if ( x === y ) {
        return true;
    }

    // If they are not strictly equal, they both need to be Objects
    if ( ! ( x instanceof Object ) || ! ( y instanceof Object ) ) {
        return false;
    }

    // They must have the exact same prototype chain, the closest we can do is
    // test the constructor.
    if ( x.constructor !== y.constructor ) {
        return false;
    }

    for ( var p in x ) {
        // Inherited properties were tested using x.constructor === y.constructor
        if ( x.hasOwnProperty( p ) ) {
            // Allows comparing x[ p ] and y[ p ] when set to undefined
            if ( ! y.hasOwnProperty( p ) ) {
                return false;
            }

            // If they have the same strict value or identity then they are equal
            if ( x[ p ] === y[ p ] ) {
                continue;
            }

            // Numbers, Strings, Functions, Booleans must be strictly equal
            if ( typeof( x[ p ] ) !== "object" ) {
                return false;
            }

            // Objects and Arrays must be tested recursively
            if ( ! Object.equals( x[ p ],  y[ p ] ) ) {
                return false;
            }
        }
    }

    for ( p in y ) {
        // allows x[ p ] to be set to undefined
        if ( y.hasOwnProperty( p ) && ! x.hasOwnProperty( p ) ) {
            return false;
        }
    }
    return true;
};


