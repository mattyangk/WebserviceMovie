<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Iterator"%>

<% int passive = (Integer) request
			.getAttribute("passiveCommentCount");%>
<% int active = (Integer) request
			.getAttribute("activeCommentCount");%>
<% HashMap<String, Integer> type=(HashMap<String, Integer>) request.getAttribute("catepreferenceMap");%>
<% HashMap<Date, Integer> trend=(HashMap<Date, Integer>)request.getAttribute("postTrendMap") ;%>
<%  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp" />
<html>
<head>
<title>Analytics</title>
<script type="text/javascript"
          src="https://www.google.com/jsapi?autoload={
            'modules':[{
              'name':'visualization',
              'version':'1',
              'packages':['corechart']
            }]
          }"></script>

    <script type="text/javascript">
      google.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = new google.visualization.DataTable();
          data.addColumn('date','Date');
          data.addColumn('number', 'Mentioned Times');
          data.addRows(<% Iterator<Map.Entry<Date, Integer>> that = trend.entrySet().iterator();out.print("[");
          if(that.hasNext()){
        	  Map.Entry<Date, Integer> entry = that.next();
           out.print("[new Date(\""+entry.getKey()+"\"),"+entry.getValue()+"]"); 
           }
          while(that.hasNext()){
        	  Map.Entry<Date, Integer> entry = that.next();
           out.print(",[new Date(\""+entry.getKey()+"\"),"+entry.getValue()+"]"); 
           }
          out.print("]");%>
           );
          
       

        var options = {
          title: '',
          curveType: 'function',
          legend: { position: 'bottom' }
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

        chart.draw(data, options);
      }
    </script>

 <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {

        var data = new google.visualization.DataTable();
          
          data.addColumn('string', 'Category');
          data.addColumn('number','Mentioned Times')
          data.addRows(<% Iterator<Map.Entry<String, Integer>> is = type.entrySet().iterator();out.print("[");
          if(is.hasNext()){
        	  Map.Entry<String, Integer> entry = is.next();
           out.print("[\""+ new String(entry.getKey())+"\","+entry.getValue()+"]"); 
           }
          while(is.hasNext()){
        	  Map.Entry<String, Integer> entry = is.next();
           out.print(",[\""+new String(entry.getKey())+"\","+entry.getValue()+"]"); 
           }
          out.print("]");%>
           );

        var options = {
          title: ''
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        chart.draw(data, options);
      }
    </script>

<script type="text/javascript">
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart);
    var passive=<%=passive%>;
    
    var active=<%=active%>;
    function drawChart() {
    	
      var data = google.visualization.arrayToDataTable([
        ["Active", "Passive", { role: "style" } ],
        ["Comments sent",active, "#FF0000"],
        ["Comments received",passive, "#0000FF"],
       
      ]);

      var view = new google.visualization.DataView(data);
      view.setColumns([0, 1,
                       { calc: "stringify",
                         sourceColumn: 1,
                         type: "string",
                         role: "annotation" },
                       2]);

      var options = {
        title: "      ",
        width: 600,
        height: 400,
        bar: {groupWidth: "50%"},
        legend: { position: "none" },
      };
      var chart = new google.visualization.ColumnChart(document.getElementById("columnchart_values"));
      chart.draw(view, options);
  }
  </script>
</head>
<body>
    <h1 style="text-align:center">Posts Trend</h1>
	<div id="curve_chart"
		style="width: 700px; height: 500px; padding-left: 350px"></div>
	<h1 style="text-align:center">Movie Category</h1>
	<div id="piechart"
		style="width: 700px; height: 500px; padding-left: 350px"></div>
	<h1 style="text-align:center">Interactions with other users</h1>
	<div id="columnchart_values"
		style="width: 700px; height: 500px; padding-left: 350px"></div>

</body>
</html>