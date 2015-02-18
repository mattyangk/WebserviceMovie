<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Iterator"%>
<% int passive = (int) request
			.getAttribute("passiveCommentCount");%>
<% int active = (int) request
			.getAttribute("activeCommentCount");%>
<% HashMap<String, Integer> type=(HashMap<String, Integer>) request.getAttribute("catepreferenceMap");%>
<% HashMap<Date, Integer> trend=(HashMap<Date, Integer>)request.getAttribute("postTrendMap") ;%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="header.jsp" />
<html>
<head>
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
        var data = google.visualization.arrayToDataTable();
          addColumn('Date', 'Mentioned Times');
          addRows(<%=trend.size()%>);
          <% Iterator<Map.Entry<Date, Integer>> it = trend.entrySet().iterator();
          while(it.hasNext()){      
          Map.Entry<Date, Integer> entry = it.next();%>
          addColumn('<%=entry.getKey()%>',<%=entry.getValue()%>);
          <%  System.out.println(entry.getKey());}%>
     

        var options = {
          title: 'Company Performance',
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

        var data = google.visualization.arrayToDataTable();
          
          addColumn('Category', 'Mentioned Times');
          addRows(<%=type.size()%>);
           <% Iterator<Map.Entry<String, Integer>> iterator = type.entrySet().iterator();
          while(iterator.hasNext()){    
        	
          Map.Entry<String, Integer> entry = iterator.next();%>
          addColumn('<%=entry.getKey()%>',<%=entry.getValue()%>);
          <%  System.out.println(entry.getKey());}%>

        var options = {
          title: 'Movie Category'
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
    	alert(passive);
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
        title: "             Comments between user and other people",
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
	<div id="curve_chart"
		style="width: 700px; height: 500px; padding-left: 200px"></div>
	<div id="piechart"
		style="width: 700px; height: 500px; padding-left: 200px"></div>
	<div id="columnchart_values"
		style="width: 700px; height: 500px; padding-left: 200px"></div>

</body>
</html>