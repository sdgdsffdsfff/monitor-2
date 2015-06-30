var SPAN = new Array();
	SPAN.push("<span class='status0'>正常</span>");
	SPAN.push("<span class='status1'>警告</span>");
	SPAN.push("<span class='status2'>异常</span>");
	SPAN.push("<span class='status3'>刷新中...</span>");
	SPAN.push("<span class='status4'>网络异常</span>");
	
var table, button, page = 1, pageSize = 20;

function $(id){
	return document.getElementById(id);
}

function loadData(){
	var data = JavaScriptInterface.getData(page);
	var html = [];
	eval("data = " + data);
	if(page == 1){
		var site = data[0];
		$("name").innerHTML = site.siteName;
		$("url").innerHTML = site.siteUrl;
		pageSize = parseInt(site.pageSize);
		html.push('<tr class="title"><td>编号</td><td>检测时间</td><td>状态</td><td>链接<br/>数量</td><td>脚本<br/>数量</td><td>大小<br/>(KB)</td><td>速度<br/>(ms)</td></tr>');
	}else{
		html.push('<tr class="line"><td colspan="7">&nbsp;</td></tr>');
	}
	for(var i = 1; i < data.length; i++){
		html.push("<tr" + (i % 2 == 0 ? " class='alt'" : "") + ">");
		html.push("<td>" + data[i].recordId + "</td>");
		html.push("<td>" + data[i].addTime.substr(5) + "</td>");
		html.push("<td>" + SPAN[parseInt(data[i].status)] + "</td>");
		html.push("<td>" + data[i].links + "</td>");
		html.push("<td>" + data[i].scripts + "</td>");
		html.push("<td>" + data[i].size + "</td>");
		html.push("<td>" + data[i].connect + "</td></tr>");
	}
	table.getElementsByTagName("tbody")[0].innerHTML += html.join('');
	if(data.length < pageSize + 1){
		button.style.display = "none";
	}
}

window.onload = function(){
	table = $("table");
	button = $("more");
	table.getElementsByTagName("tbody")[0].innerHTML = "";
	
	loadData();
	button.onclick = function(){
		page++;
		loadData();
	};
};
