function $(id){
	return document.getElementById(id);
}
window.onload = function(){
	var data = JavaScriptInterface.getData();
	eval("data = " + data);
	
	var table = $("table"), html = [], month = {};
	html.push('<tr class="title"><td width="25%">月份</td><td width="25%">流量</td><td width="25%">次数</td><td width="25%">平均</td></tr>');
	for(var i = 0; i < data.length; i++){
		var d = new Date(data[i].addTime + ":00");
		var m = d.getFullYear() + "年" + (d.getMonth() + 1) + "月";
		
		if(month[m]){
			month[m].length += data[i].streamLength;
			month[m].count++;
		}else{
			month[m] = {length: data[i].streamLength, count: 1};
		}
	}
	
	var i = 1, allCount = 0, kb;
	for(var o in month){
		kb = month[o].length / 1024.0;
		allCount += kb;
		html.push("<tr" + (i++ % 2 == 0 ? " class='alt'" : "") + ">");
		html.push("<td>" + o + "</td>");
		html.push("<td>" + kb.toFixed(3) + "KB</td>");
		html.push("<td>" + month[o].count + "</td>");
		html.push("<td>" + (kb / month[o].count).toFixed(3) + "KB</td></tr>");
	}
	html.push("<tr" + (i++ % 2 == 0 ? " class='alt'" : "") + ">");
	html.push("<td>合计</td><td colspan='3'>" + allCount.toFixed(3) + "KB</td></tr>");
	
	table.getElementsByTagName("tbody")[0].innerHTML = html.join('');
};
