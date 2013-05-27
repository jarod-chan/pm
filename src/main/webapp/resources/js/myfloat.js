//浮点数加法
function FloatAdd(arg1,arg2){  
var s1=arg1.toString(),s2=arg2.toString(),r1,r2,m;  
try{r1=s1.split(".")[1].length}catch(e){r1=0}  
try{r2=s2.split(".")[1].length}catch(e){r2=0}
with(Math){
m=max(r1,r2); 
s1=Number(arg1).toFixed(m).toString().replace('.',''); 
s2=Number(arg2).toFixed(m).toString().replace('.',''); 
return (Number(s1)+Number(s2))/pow(10,m);
}  
} 

//浮点数减法运算  
function FloatSub(arg1,arg2){  
var s1=arg1.toString(),s2=arg2.toString(),r1,r2,m;  
try{r1=s1.split(".")[1].length}catch(e){r1=0}  
try{r2=s2.split(".")[1].length}catch(e){r2=0}
with(Math){
m=max(r1,r2); 
s1=Number(arg1).toFixed(m).toString().replace('.',''); 
s2=Number(arg2).toFixed(m).toString().replace('.',''); 
return (Number(s1)-Number(s2))/pow(10,m);
}  
}  

 //浮点数乘法运算  
function FloatMul(arg1,arg2)   
{   
var m=0,s1=arg1.toString(),s2=arg2.toString();   
try{m+=s1.split(".")[1].length}catch(e){}   
try{m+=s2.split(".")[1].length}catch(e){}   
return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)   
}   
  
//浮点数除法运算  
function FloatDiv(arg1,arg2){   
var t1=0,t2=0,r1,r2;   
try{t1=arg1.toString().split(".")[1].length}catch(e){}   
try{t2=arg2.toString().split(".")[1].length}catch(e){}   
with(Math){   
r1=Number(arg1.toString().replace(".",""))   
r2=Number(arg2.toString().replace(".",""))   
return (r1/r2)*pow(10,t2-t1);   
}   
} 


//保留小数位 ,四舍五入，保留0
function hold(num,n)
{
	var N = Math.pow(10,n);
    return (Math.round(num * N)/N).toFixed(n);
} 


function Trim(str){
	return $.trim(str);
}

/*
IsFloat(string,string,int or string)测试字符串,+ or - or empty,empty or 0)
功能：判断是否为浮点数、正浮点数、负浮点数、正浮点数+0、负浮点数+0
*/
function IsFloat(objStr,sign,zero)
{
    var reg;
    var bolzero;

    if(Trim(objStr)=="")
    {
        return false;
    }
    else
    {
        objStr=objStr.toString();
    }

    if((sign==null)||(Trim(sign)==""))
    {
        sign="+-";
    }

    if((zero==null)||(Trim(zero)==""))
    {
        bolzero=false;
    }
    else
    {
        zero=zero.toString();
        if(zero=="0")
        {
            bolzero=true;
        }
        else
        {
            alert("检查是否包含0参数，只可为(空、0)");
        }
    }

    switch(sign)
    {
        case "+-":
            //浮点数
            reg=/^((-?|\+?)\d+)(\.\d+)?$/;
            break;
        case "+":
            if(!bolzero)
            {
                //正浮点数
                reg=/^\+?(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
            }
            else
            {
                //正浮点数+0
                reg=/^\+?\d+(\.\d+)?$/;
            }
            break;
        case "-":
            if(!bolzero)
            {
                //负浮点数
               reg=/^-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
            }
            else
            {
                //负浮点数+0
                reg=/^((-\d+(\.\d+)?)|(0+(\.0+)?))$/;
            }
            break;
        default:
            alert("检查符号参数，只可为(空、+、-)");
            return false;
            break;
    }

    var r=objStr.match(reg);
    if(r==null)
    {
        return false;
    }
    else
    {
        return true;
    }
}
