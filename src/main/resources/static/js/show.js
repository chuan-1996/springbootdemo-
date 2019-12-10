$( function() {
    $.ajax({
        url: "/show-user",
        type: "POST",
        dataType: "json",
        success: function(result){
            showData(result.data);//我们仅做数据展示
            $("#error").text(result.msg);
        },
        error: function(msg){
            alert("ajax连接异常：");
        }
    });
    //展示数据
    function showData(data) {
        var str = "";//定义用于拼接的字符串
        for (var i = 0; i < data.length; i++) {
            //拼接表格的行和列
            str = "<tr><td>" + data[i].id + "</td><td>" + data[i].username + "</td><td>" + data[i].realname + "</td><td>" + data[i].role + "</td></tr>"  ;
            //追加到table中
            $("#tab").append(str);
        }
    }
} );