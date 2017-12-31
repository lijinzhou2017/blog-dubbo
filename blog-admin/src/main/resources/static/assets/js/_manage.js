/**
 * Created by Linyb on 2017/6/17.
 */
/**
 * 自定义一些插件 封装
 */
var pageSize = 10;
var _happy = {
    layer:{
        /*打开一个iframe*/
        iframe:function(){
            var url = arguments[0] ? arguments[0] : '#';
            var gd = arguments[1] ? arguments[1] : 'no';
            var width = arguments[2] ? arguments[2] : '50%';
            var height = arguments[3] ? arguments[3] : '50%';
            var title = arguments[4] ? arguments[4] : '操作';
            layer.open({
                title:false,
                type: 2,
                content: url,
                area:[width,height],
                offset:['5%','15%']
            });
        }
    }
};


/***
 * js日期格式化处理
 * yyyy 年
 * MM   月
 * dd   日
 * hh   小时(12小时制还是24小时制，都一样)
 * mm   分钟
 * ss   秒
 * eg:yyyy-MM-dd hh:mm:ss
 *
 * 调用方式：new Date().format("yyyy-MM-dd hh:mm:ss")
 *
 * @Author linyb
 * @param fmt  格式样式
 * @returns
 */
Date.prototype.format = function(fmt){
    var year    =   this.getFullYear();
    var month   =   this.getMonth()+1;
    var date    =   this.getDate();
    var hour    =   this.getHours();
    var minute  =   this.getMinutes();
    var second  =   this.getSeconds();

    fmt = fmt.replace("yyyy",year);
    fmt = fmt.replace("yy",year%100);
    fmt = fmt.replace("MM",fix(month));
    fmt = fmt.replace("dd",fix(this.getDate()));
    fmt = fmt.replace("hh",fix(this.getHours()));
    fmt = fmt.replace("mm",fix(this.getMinutes()));
    fmt = fmt.replace("ss",fix(this.getSeconds()));
    return fmt;
    function fix(n){
        return n<10?"0"+n:n;
    }
}
/**
 * 删除一个数组里面指定值
 * @Author Linyb
 * @Date 2016/12/14 18:05
 */
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

var ajaxError = function ajaxError(data){
    var result = data.result;
    if(result == 405){
        layer.alert("登录过期，请重新登录",{icon:2}, function (index) {
            parent.window.location.href = "/" ;
        });
    }else{
        layer.alert("系统错误",{icon:2});
    }
}

var ajaxSuccess = function ajaxSuccess(data){
    var code = data.result;
    if(code === 200){
        layer.msg(data.msg,{icon:1,time:200},function () {
            window.location.reload();
        });
    }else{
        layer.msg(data.msg);
    }
}