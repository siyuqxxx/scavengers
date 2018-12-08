/**
 * Created by Bensn on 16/5/31.
 */

var errTitle = '异常';
var warTitle = '错误';
var errMsg = '服务器异常，请联系管理员';
var imgTitlte = '预览图片';

/**
 * 初始化
 */
$("body").css({"position":"absolute","width":"100%"});
$(function () {
    var formObj = $('form');
    if (!formObj.hasClass('noajax'))
        formAjax(formObj);
    tableSort($('.table-sort'));
    coverClick();

    $(".breadcrumb").css({"position":"absolute","width":"98%","top":"0px","padding":"0px 1%"});

    $("br").remove();
    $(".breadcrumb").next().css("margin-top","50px");

//    ie9以下placeholder有问题
    function placeholderSupport() {
        return 'placeholder' in document.createElement('input');
    }
    if(!placeholderSupport()){   // 判断浏览器是否支持 placeholder
        $("[placeholder]").each(function(){
            var _this = $(this);
            var left = _this.css("padding-left");
            _this.parent().append('<span class="placeholder" data-type="placeholder" style="left: ' + left + '">' + _this.attr("placeholder") + '</span>');
            if(_this.val() != ""){
                _this.parent().find("span.placeholder").hide();
            }
            else{
                _this.parent().find("span.placeholder").show();
            }
        }).on("focus", function(){
            $(this).parent().find("span.placeholder").hide();
        }).on("blur", function(){
            var _this = $(this);
            if(_this.val() != ""){
                _this.parent().find("span.placeholder").hide();
            }
            else{
                _this.parent().find("span.placeholder").show();
            }
        });
        // 点击表示placeholder的标签相当于触发input
        $("span.placeholder").on("click", function(){
            $(this).hide();
            $(this).siblings("[placeholder]").trigger("click");
            $(this).siblings("[placeholder]").trigger("focus");
        });
    }

});
/**
 * 后台登陆按钮样式控制
 */
function Style() {
    var flag = true;
    $(".Btns>.btn").click(function () {
        $(this).css({backgroundColor: "#E5B200"});


    })
    $("#online").click(function () {
        if ($(this).get(0).checked == true) {
            $(".online").css({backgroundColor: "#FDC100"})
        } else {
            $(".online").css({backgroundColor: "transparent"})
        }
    })
}
Style()
/**
 * 缩略图点击
 */
function coverClick() {

    $('.cover,.imd').on('click', function () {
        previewPictures($(this).attr('src'));
    });
}

/**
 * 预览图片
 * @param url
 */
function previewPictures(url) {
    layer.open({
        title: imgTitlte,
        type: 1,
        closeBtn: 2, //不显示关闭按钮
        shadeClose: true, //开启遮罩关闭
        content: '<img src="' + url + '" style="max-height:400px;" />'
    });
}


function tableSort(obj) {
    var actionColumnIndex = $('thead').children("th").length;
    if (!actionColumnIndex || actionColumnIndex < 1) {
        return;
    }
    obj.dataTable({
        "aaSorting": [[1, "desc"]],
        "bStateSave": true,
        "aoColumnDefs": [
            {
                "orderable": false, "aTargets": [0, actionColumnIndex - 1]
            }
        ]
    });
}

/**
 * 弹出覆层
 * @param title
 * @param url
 * @param w.
 * @param h
 */
var a;
function showIframe(title, url, w, h) {
    console.log(title, url, w, h);
    w = w ? w + "px" : '';
    h = h ? h + "px" : '';
    var index = layer.open({
        type: 2,
        title: title,
        content: url,
        area: [w, h]
    });
    if ((!w || w == '') && (!h || h == '')) {
        layer.full(index);
    }
}

/**
 * 删除提示
 * @param url
 */
function delItem(url) {
    layer.confirm('确认要删除吗？', function (index) {
        requestUrl(url);
    });
}

/**
 * 开启关闭
 * @param url
 */
function openswItem(url, vb) {
    console.log(1)

    if (parseInt(vb)==1) {

        layer.confirm('确认要关闭吗？', function (index) {
            requestUrl(url);
        });

    } else {
        layer.confirm('确认要开启吗？', function (index) {
            requestUrl(url);
        });
    }

}

/**
 * 删除选中
 * @param url
 * @param itemObj
 */
function delCheck(url, itemObj) {
    layer.confirm('确认要删除选中数据吗？', function (index) {
        layer.close(index);
        var itemAll = itemObj;
        var ids = '';
        for (var i = 0; i < itemAll.size(); i++) {
            var item = itemAll.eq(i);
            var id = item.val();
            if (id > 0) {
                if (!item.is(':checked')) {
                    continue;
                }
                ids += id + ',';
            }
        }
        if ($("input[type='checkbox']:checked").length == 0) {
            layer.msg('请选择要删除的数据!');
            return;
        } else if ($("input[type='checkbox']:checked").length == 1 && $('#box').is(':checked')) {
            layer.msg('对不起，您没有可以删除的数据!');
            return;
        } else {
            //window.location.href = url + '?id=' + ids.substring(0,ids.length - 1);
            requestUrl(url + '?id=' + ids);
        }
        // alert($("input[type='radio']:checked").val());
    });
}


/**
 * 提交试卷
 * @param url
 * @param itemObj
 */


/**
 * ajax提交表单
 * @param formObj
 */
function formAjax(formObj) {
    formObj.Validform({
        tiptype: 2,
        callback: function (form) {
            // debugger;
            var layars = layer.load(0, {shade: [0.8, '#fff']});
            formObj.ajaxSubmit({
                url: formObj.attr('action'),
                type: 'post',

                enctype: 'multipart/form-data',
                success: function (data) {
                    layer.close(layars);
                    runAjaxResSuccess(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layer.close(layars);
                    runAjaxResError(XMLHttpRequest, textStatus, errorThrown);
                }
            });
            return false;
        }
    });
}

/**
 * 跳转url
 * @param url
 * @param obj
 */
function goUrl(url, obj) {
    obj = obj ? obj : self;
    obj.location = url;
}


/**
 * ajax提交
 * */
function requestUrl(url) {
    var layars = layer.load(0, {shade: [0.8, '#fff']});
    $.ajax({
        url: url,
        success: function (data) {
            layer.close(layars);
            runAjaxResSuccess(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            layer.close(layars);
            runAjaxResError(XMLHttpRequest, textStatus, errorThrown);
        }
    });
}


/**
 * 解析ajax请求成功时返回的数据
 * @param data
 */
function runAjaxResSuccess(data) {
    // if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)) {
    //     console.log(1)
    //     var data = JSON.parse(data)
    // }
    if (data.code === 1) {
        layer.msg(data.msg, {
            icon: 1,
            time: 1000
        }, function (index) {
            //如果页面是在弹窗
            var indexbox = parent.layer.getFrameIndex(window.name);
            if (indexbox) {
                //关闭弹窗
                if (data.url) {
                    goUrl(rootPath + data.url, parent);
                } else {
                    parent.location.reload();
                }
                parent.layer.close(indexbox);
            }
            layer.close(index);
            if (data.url) {
                goUrl(rootPath + data.url);
            }
        });
    } else if (data.code === 0) {
        layer.alert(data.msg, {
            title: warTitle,
            icon: 2
        }, function (index) {
            //如果页面是在弹窗
            var indexbox = parent.layer.getFrameIndex(window.name);
            if (indexbox) {
                //关闭弹窗
                if (data.url) {
                    goUrl(rootPath + data.url, parent);
                }
                parent.layer.close(indexbox);
            }
            layer.close(index);
            if (data.url) {
                goUrl(rootPath + data.url);
            }
        });
    } else {
        layer.alert(errMsg, {
            icon: 0,
            title: errTitle
        });
    }
}


/**
 * 解析ajax请求失败时返回的数据
 * @param XMLHttpRequest
 * @param textStatus
 * @param errorThrown
 */
function runAjaxResError(XMLHttpRequest, textStatus, errorThrown) {
    var errorInfo = $(XMLHttpRequest.responseText).find('h1').text();
    layer.alert(errorInfo + '<br/>' + errMsg, {
        icon: 0,
        title: errTitle
    });
}

/**
 * 编辑用户-切换用户角色重新加载权限列表
 * @param obj
 */
function reloadFunctions(url) {
    var submit = $('.btn');
    var selBox = $(':input[name="functionsArr"]');
    submit.attr('disabled', 'disabled');
    selBox.attr('disabled', 'disabled');
    var layars = layer.load(0, {shade: [0.8, '#fff']});
    $.ajax({
        url: url,
        success: function (data) {
            layer.close(layars);
            if (data.code == 1) {
                var htmls = "";
                for (var i = 0; i < data.data.length; i++) {
                    htmls += "<option value=" + data.data[i].id + ">" + data.data[i].id + " - " + data.data[i].title + "</option>";
                }
                selBox.html(htmls);
            }
            submit.removeAttr('disabled');
            selBox.removeAttr('disabled');
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            layer.close(layars);
        }
    });
}


/**
 * 荐购列表导出
 */
function exportList(url, itemObj) {
    layer.confirm('确认要导出选中数据吗？', function (index) {
        layer.close(index);
        var ids = '';
        for (var i = 0; i < itemObj.size(); i++) {
            var item = itemObj.eq(i);
            var id = item.val();
            if (id > 0) {
                if (!item.is(':checked')) {
                    continue;
                }
                ids += id + ',';
            }
        }
        // for (var i = 0; i < itemObj.length; i++) {
        //     //选中的数据集
        //     ids += itemObj.eq(i).val() + ",";
        // }
        if ($("input[type='checkbox']:checked").length == 0) {
            layer.msg('请勾选您要导出的数据集!');
            return;
        } else {
            window.location.href = url + '?id=' + ids.substring(0, ids.length - 1);
        }
    });
}


/**
 * excel表导出
 */
function exportFindBookList(url, itemObj) {

    layer.confirm('确认要导出选中数据吗？', function (index) {
        layer.close(index);
        var ids = '';
        for (var i = 0; i < itemObj.size(); i++) {
            var item = itemObj.eq(i);
            var id = item.val();
            if (id > 0) {
                if (!item.is(':checked')) {
                    continue;
                }
                ids += id + ',';
            }
        }
        // for (var i = 0; i < itemObj.length; i++) {
        //     //选中的数据集
        //     ids += itemObj.eq(i).val() + ",";
        // }
        if ($("input[type='checkbox']:checked").length == 0) {
            layer.msg('要导出的数据不能为空，请重新选择!');
        } else if ($("input[type='checkbox']:checked").length == 1 && $('#box').is(':checked')) {
            layer.msg('对不起，您没有可以导出的数据!');
        } else {
            window.location.href = url + '?id=' + ids.substring(0, ids.length - 1);
        }
    });
}

/**
 * excel表导出
 */
// function allotIbeacon(url, itemObj){
//     layer.confirm('确认要分配选中数据吗？', function (index) {
//         layer.close(index);
//         var ids = '';
//         for (var i = 0; i < itemObj.size(); i++) {
//             var item = itemObj.eq(i);
//             var id = item.val();
//             if (id > 0) {
//                 if (!item.is(':checked')) {
//                     continue;
//                 }
//                 ids += id + ',';
//             }
//         }
//         // for (var i = 0; i < itemObj.length; i++) {
//         //     //选中的数据集
//         //     ids += itemObj.eq(i).val() + ",";
//         // }
//         if ($("input[type='checkbox']:checked").length == 0){
//             layer.msg('要分配的数据不能为空，请重新选择!');
//         }else if($("input[type='checkbox']:checked").length == 1 && $('#box').is(':checked')){
//             layer.msg('对不起，您没有可以分配的数据!');
//         }else {
//             window.location.href = url + '?id=' + ids.substring(0,ids.length - 1);
//         }
//     });
// }
//


/**
 * 列表页翻页
 * @param dom
 */
function goPage(dom) {
    location.href = '?p=' + $(dom).val();
}
/**
 * ajax
 */
function ajaxs(url){
    var objs;
    $.ajax({
        url:url,
        type:"get",
        async:false,
        success:function(data){
            objs=data;
        }
    });
    return objs;
}


function ajaxs1(url,data){
    var objs;
    $.ajax({
        url:url,
        type:"get",
        data:data,
        async:false,
        success:function(data){
            objs=data;
        }
    });
    return objs;
}

function ajaxs2(url,hrefUrl){
    var objs;
    $.ajax({
        url:url,
        type:"post",
        async:false,
        success:function(data){
            objs=data;
            if(hrefUrl!='' && hrefUrl!=null){
                layer.msg("操作成功");
                setTimeout(function(){
                    location.href=hrefUrl
                },1000);
            }
        }
    });
    return objs;
}

function ajaxsk(url,data,type){
    var objs;
    $.ajax({
        url:url,
        type:type,
        data:data,
        async:false,
        success:function(data){
            objs=data;
        }
    });
    return objs;
}
//tips提示
function tip(txt,obj,diraction,color){
    layer.tips(txt, obj, {
        tips: [diraction, color],
        time: 5000
    });
}
//获取url传递参数（单个）
function getIds(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

//时间戳转换
    function timeZh(timestamp,data) {
        var formDat=data.split("");
        var date = new Date(timestamp);
        if(formDat.indexOf("y")!=-1){
            Y = date.getFullYear() + '-';
        }
        if(formDat.indexOf("M")!=-1){
            M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        }
        if(formDat.indexOf("d")!=-1){
            D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate())+ ' ';
        }
        if(formDat.indexOf("h")!=-1){
            h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours())+ ':';
        }
        if(formDat.indexOf("m")!=-1){
            m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes())+ ':';
        }
        if(formDat.indexOf("s")!=-1){
            s = (date.getSeconds()< 10 ? '0'+(date.getSeconds()) : date.getSeconds());
        }
        return Y+M+D+h+m+s;
    }
//    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//上传图片
//target(当前input=file)
// ,formId(提交的formId)
// ,url(提交路径),arrNum(上传图片支持格式字符串'01234567'格式)
// ,size(图片大小"450,50")
// ,ylDom(图片上传后预览对象--为''时不需要对象);
function toHuiXian(target,formId,url,arrNum,size,ylDom){
    isPictrue(target,arrNum);
    var b={
        url:url,
        type:"post",
        dataType:"json",
        success:function(mes){
            console.log(mes,"上传图片返回");
            if(ylDom!=undefined){
                showResponse(mes,ylDom);
            }
        }
    };
    verificationPicFile(target,size,formId,b);
    // $("#"+formId).ajaxSubmit(b);
}
//    上传图片格式判断
function isPictrue(target,arr){
    var fileSize = 0;
    var filetypes = [  ".GIF", ".gif", ".JPEG",".jpeg",".JPG",".jpg",
        ".PNG", ".png"];
    var indexArr=arr.split("");
    var  newfiletypes=[];
    $(indexArr).each(function(i,j){
        newfiletypes.push(filetypes[j])
    });
    console.log(newfiletypes,"图片限制格式");
    var filepath = target.value;
    if (filepath) {
        var isnext = false;//判断类型
        var fileend = filepath.substring(filepath.lastIndexOf("."));
            for (var i = 0; i < newfiletypes.length; i++) {
                if (newfiletypes[i] == fileend) {
                    isnext = true;
                    break;
                }
            }
        if (!isnext) {
            layer.msg("不接受此文件类型！");
            target.value = "";
            return ;
        }
    } else {
        layer.msg("请选择图片！");
        return ;
    }


}

//判断图片尺寸是否合适
function verificationPicFile(file,size,formId,b) {
    var isSize=size.split(",");
    var filePath = file.value;
    if(filePath){
        //读取图片数据
        var filePic = file.files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload=function(){
                var width = image.width;
                var height = image.height;
                var newW=parseInt(isSize[0]);
                var newH=parseInt(isSize[1]);
                console.log(newW,newH);
                if (((newW+10)<width || width<(newW-10)) && ((newH+10)<height || height<(newH-10))){
                    layer.msg("文件尺寸应为："+newW+"*"+newH+"！");
                    file.value = "";
                    return false;
                }else {
                    $("#"+formId).ajaxSubmit(b);
                }
            };
            image.src= data;
        };
        reader.readAsDataURL(filePic);
    }else{
        return false;
    }
}
//图片上传成功后的操作
function showResponse(mes,dom){
    console.log(mes,dom);
    $("#"+dom).css("background","url('/upload/"+mes.data+"')no-repeat center center/100% 100% #9fbfd8")
}
//全选(父级check的class='fCheck',子级check的class='zCheck') ；
//当选中时添加ck类名反之删除ck
function fCheck(dom){
    if($(dom).is(":checked")==true){
        $(".zCheck").prop("checked",true).addClass("ck");
    }else {
        $(".zCheck").prop("checked",false).removeClass("ck");
    }
}
function zCheck(dom){
    if($(dom).is(":checked")==true){
        $(dom).prop("checked",true).addClass("ck");
    }else {
        $(dom).prop("checked",false).removeClass("ck");
    }
}
//单个删除
function oneDel(url){
    layer.confirm('确认要删除吗？', function (index) {
            console.log(url);
            ajaxs2(url,'/library/seatExamWhite/cribberSeat.html');
    });
};
//批量删除check选中的
function allDels(url,idName) {
    layer.confirm('确认要删除吗？', function (index) {
        if($(".ck").length==0){
            layer.msg("请选择要删除的数据！")
        }else {
            var arr=[];
            $(".ck").each(function(i,j){
                arr.push(
                    $(j).attr("id")
                );
            });
            url=url+"?"+idName+"="+arr.join(",");
            console.log(url);
            ajaxs2(url,'/library/seatExamWhite/cribberSeat.html');
        }
    });
}
//textarea弹框
function popText(txt,type,arr,id,url){
    console.log(url);
    var arrs=arr.split(",");
    layer.prompt({
        formType: type,
        title:txt,
        type:1,
        id:id,
        area: [arrs[0]+"px", arrs[1]+"px"],
        btnAlign: 'c',
        btn: ['提交', '取消'],
        btn1: function(index, layero){
            console.log(index,layero);
            // 获取文本框输入的值
            var value = layero.find(".layui-layer-input").val();
            if (value) {
                layer.close(index);
                var data={
                    id:id,
                    cardNumber:value

                };
               var a= ajaxsk(url,data,'post');
                layer.msg(a.msg);
                setTimeout(function(){
                    location.href="/library/seatExamWhite/cribberSeat.html";
                },1300)
            } else {
                layer.msg("请输入内容！");
            }
        }
    })
}
//针对于page分页参数拼接处理
    function pages(obj,str){
        $(obj).each(function(i,j){
            if($(j).children("a").attr("href")!="javascript:void(0);"){
                $(j).children("a").attr("href",$(j).children("a").attr("href")+str);
            }
        })
    }

//    指定时间戳转换为日期00:00:00
    function formatTime(mss){
        var days = parseInt(mss / (1000 * 60 * 60 * 24));
        var hours = parseInt((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = parseInt((mss % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = parseInt((mss % (1000 * 60)) / 1000);
        return (((days*24)+hours)<10? "0"+((days*24)+hours):((days*24)+hours))+ ":" + (minutes<10? "0"+minutes:minutes) + ":"+(seconds<10? "0"+seconds:seconds);
    }

    //删除数组中指定的具体对象（继承）
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
    //上传文件
function fileUpload(url,obj,name){
    var a;
    var formData = new FormData();
    formData.append(name, $(obj)[0].files[0]);
    $.ajax({
        url : url,
        type : 'POST',
        data : formData,
        contentType: false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置
        processData: false,// 是否序列化data属性，默认true(注意：false时type必须是post)
        dataType: 'json',//这里是返回类型，一般是json,text等
        clearForm: true,//提交后是否清空表单数据
        async:false,
        success: function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
            a=data
        },
        error: function(data, status, e) {  //提交失败自动执行的处理函数。
            console.error(e);
        }

    });
    return a;
}
