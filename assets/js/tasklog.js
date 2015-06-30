var SPAN = new Array();
	SPAN.push("<span class='status0'>正常</span>");
	SPAN.push("<span class='status1'>警告</span>");
	SPAN.push("<span class='status2'>异常</span>");
	SPAN.push("<span class='status3'>刷新中...</span>");
	SPAN.push("<span class='status4'>网络异常</span>");
	
var table, button, page = 1, pageSize = 20, interval = 5;

function $(id){
	return document.getElementById(id);
}

function getEndTime(endTime){
	var end = new Date(endTime + ":00");
	var time = new Date().getTime() - end.getTime();
	time = time / (60 * 1000);
	if(time < interval * 2){
		endTime = "<span class='status0'>运行中</span>";
	}else{
		endTime = endTime.substr(5);
	}
	return endTime;
}

function loadData(){
	var data = JavaScriptInterface.getData(page);
	var html = [];
	eval("data = " + data);
	if(page == 1){
		var site = data[0];
		$("name").innerHTML = site.taskName;
		//$("url").innerHTML = site.siteUrl;
		interval = parseInt(site.interval);
		pageSize = parseInt(site.pageSize);
		html.push('<tr class="title"><td>编号</td><td>启动时间</td><td>结束时间</td><td>次数</td><td>流量(KB)</td><td>状态</td></tr>');
	}else{
		html.push('<tr class="line"><td colspan="6">&nbsp;</td></tr>');
	}
	for(var i = 1, kb; i < data.length; i++){
		kb = (data[i].streamLength / 1024.0).toFixed(3);
		html.push("<tr" + (i % 2 == 0 ? " class='alt'" : "") + ">");
		html.push("<td>" + data[i].logId + "</td>");
		html.push("<td>" + data[i].addTime.substr(5) + "</td>");	
		html.push("<td>" + getEndTime(data[i].endTime) + "</td>");
		html.push("<td>" + data[i].runTimes + "</td>");
		html.push("<td>" + kb + "</td>");
		html.push("<td>" + SPAN[parseInt(data[i].status)] + "</td></tr>");
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
