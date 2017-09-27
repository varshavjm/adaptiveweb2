<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.assignment2.FileUtility"%>
<%@ page import="com.assignment2.GlobalConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
text{
  cursor:pointer;
}
text:hover{
    tool-tip: "Click on the label for drillDown";
}
#content{
position:absolute;
top:550px;
bottom:0;
font-weight:500;
}

.pattern
{
 font-family: "Palatino Linotype", "Book Antiqua", Palatino, serif;
 font-size:16px;
 padding:15px;
}



button{
height:50px !important;
width:200px !important;
}
#piechart{
width: 600px;
 height: 500px;
 float:right;
  display:inherit;
}
#piechartGroup{
width: 600px;
 height: 500px;
 float:left;
 display:inherit;
}

#block{
display:none;
position:relative;
margin-top:15%;
}



#chart_div{
style="width: 1000px; 
height: 500px;"
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome!!!</title>
<link rel="stylesheet"
	href="CSS/style.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
	  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
      <script type="text/javascript" src="jquery.js"></script>
    <script type="text/javascript">
$(document).ready(function() {
      var data;
      var options;
      var chart; 
      var length;
      var old_view;
      var globaluser;

      var userList= new Array();
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawVisualization);
      google.charts.setOnLoadCallback(drawChart2);
      google.charts.setOnLoadCallback(drawChart1);

 var handler = function(e) {
        var parts = e.targetID.split('#');
        //alert(e);
        if (parts.indexOf('label') >= 0) {
            var idx = parts[parts.indexOf('label') + 1];

           var olduser=globaluser;
           globaluser=data.getValue(parseInt(idx), 0);
           if(idx<userList.length){
           if(olduser===globaluser || olduser===undefined)
              $("#block").toggle();
           else
        	   	$("#block").css("display","block");
           drawChart1();
           drawChart2();
           }

        }
    };

//try writing handler on text



       function getData(fileName){
        var dataResult;
        $.ajax({                                                                        
          url: fileName,                                                      
          async: false,                                                               
          type: 'GET', 
          contentType:"application/json; charset=utf-8",
           // added data type                                        
          success: function(res) {                                  
            dataResult = res;      
          } ,
          error: function(XMLHttpRequest, textStatus, errorThrown){alert("Status: " + textStatus); alert("Error: " + errorThrown); }
          
        });  
        return dataResult;
      }

/*This one is a group chart only if user clicks on label*/
       function drawChart1() {

         var dataResult=getData("https://adaptivewebone.herokuapp.com/ActionMapServlet");
         let all_items = {'Search':0, 'Favorite':0,'Upvote':0,'Downvote':0,'Comment':0,'Post':0 }
         

         for(key in dataResult) {
           let value = dataResult[key];
           value=value.actions;
           for(ele in value) {
             all_items[ele]=all_items[ele]+value[ele];
           }
         }


         var data = google.visualization.arrayToDataTable([
           ['Task', 'Actions'],
           ['Post',     all_items['Post']],
           ['Comment',      all_items['Comment']],
           ['Upvote',  all_items['Upvote']],
           ['Downvote', all_items['Downvote']],
           ['Search',    all_items['Search']]
         ]);

         var options = {
           title: 'All User Activities'
         };

         var chart = new google.visualization.PieChart(document.getElementById('piechartGroup'));

         chart.draw(data, options);
       }

      function drawVisualization() {
        data=getData("https://adaptivewebone.herokuapp.com/ActionMapServlet");
        console.log(data);
        //data=JSON.parse(data);
        var all_items = ['Search', 'Favorite','Upvote','Downvote','Comment','Post'];
        //For filter use delete

        var all_itemsSize=0;
        for(var item in all_items)
          all_itemsSize++;

        result=[];
        result.push(["username"].concat(all_items));                                                                                               
        for(userid in data) {                                                                                                                      
          let user = data[userid];                                                                                                               
          let new_user_data = [user["userName"]]
          userList.push(user["userName"]);

          for (index in all_items) {                                                                                                             
              let item = all_items[index];                                                                                                       
              if (item in user.actions) {                                                                                                        
                  new_user_data.push(+user.actions[item])                                                                                        
              }else{                                                                                                                             
                  new_user_data.push(0)                                                                                                          
              }                                                                                                                                  
          } 
        length = result.length - 1;                                                                                                               
        result.push(new_user_data);                                                                                                            
    }                                                                      

        
    data = google.visualization.arrayToDataTable(result);


    options = {
      title : 'Actions of different Users',
      vAxis: {title: 'Units'},
      hAxis: {title: 'Users'},
      seriesType: 'bars',
      width:1000,
      height:400
    };
      var view = new google.visualization.DataView(data);
      view.setColumns([0,1,2,3,4,5,6]);

    chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
    google.visualization.events.addListener(chart, 'select', function () {
        highlightBar(chart, options, view);
    });
    google.visualization.events.addListener(chart, 'click', handler);


    chart.draw(view, options);
  }


  function highlightBar(chart, options, view) {
    //view = new google.visualization.DataView(data);
    old_view = view;
    var selection = chart.getSelection();
    console.log(selection);
        var column = selection[0].column;
   var row = selection[0].row;
    if (selection.length) {
        if(row==null){
        console.log(row);
        console.log(column);
        view.hideColumns([view.getTableColumnIndex(column)]);
        console.log("Removed column "+ column);
         chart.draw(view, options);
       } else {
view.hideRows([view.getTableRowIndex(row)]);
 chart.draw(view, options);
       }
    }
}

// this one is for drawing the individual chart only if user clicks on the label
  function drawChart2() {
          var dataResult=getData("https://adaptivewebone.herokuapp.com//ActionMapServlet");
          console.log(dataResult);
          let all_items = {'Search':0, 'Favorite':0,'Upvote':0,'Downvote':0,'Comment':0,'Post':0 }
         for(key in dataResult) {
           let value = dataResult[key];
            if(value['userName']===globaluser){
             value=value.actions;
              for(ele in value) {
               all_items[ele]=all_items[ele]+value[ele];
            }
            break;
             }
          }

         for(ele in all_items) 
              console.log(ele+all_items[ele]);
           

        var data = google.visualization.arrayToDataTable([
          ['Actions', 'Percent'],
          ['Post',     all_items['Post']],
          ['Favorite',     all_items['Favorite']],
          ['Comment',      all_items['Comment']],
          ['Upvote',  all_items['Upvote']],
          ['Downvote', all_items['Downvote']],
          ['Search',    all_items['Search']]
        ]);

        var options = {
          title: 'Individual Activities Chart for '+globaluser
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));
        
        chart.draw(data, options);
      }

    // Will accure at document load
  $("#ResetColumn").on("click",function(){
      old_view.setColumns([0,1,2,3,4,5,6]);
       chart.draw(old_view, options);
  });

  $("#ResetRow").on("click",function(){
      old_view.setRows(0, length);
       chart.draw(old_view, options);
  });

  $("#disappear").on("click",function(){
     $("#piechart").style.display='none';
  });


});


    </script>
</head>
<body>
	<%
		String username = GlobalConstants.userName;
	if(username.length()<=0)
		response.sendRedirect("login.jsp");

	%>
	<h1>
		Welcome,
		<%=username%></h1>
	<h2>
		<a href="LogoutServlet">Logout</a> | <a
		href="https://stackoverflow.com/questions/tagged/java?sort=frequent&pageSize=15"
		target="#">Click on Stack overflow link</a>
	</h2>
	<div>
	<button id="ResetColumn"> Reset Filters on Columns</button>
    <button id="ResetRow"> Reset Filters on Rows</button>
    <div id="chart_div" ></div>
    <div id="block">
    <div id="piechartGroup"></div>
    <div id="piechart" "></div>
    </div>
    </div>
 	<div id="content">
<ul class="pattern">
<li> <u>User Behaviour</u> <br/> Pie chart drills down and shows if the user is deviating from the the average user's behaviour.
To view a pie chart, <b>click on the user label on the X axis of the bar chart and scroll down.</b>> To hide it back, click on the same label. The pie chart 
on the left side shows average behaviour and the right side shows individual behaviour of a user over time. 
This can be used to detect anomalous user behaviour.</li>
<li> <u>Filter on Actions</u> <br/>  Users performing frequent upvotes do not downvote. At the same time, users who have high number of favourites tend to have comparatively high number of upvotes too. This pattern be seen by clicking 
on unwanted actions on the bar chart legend to the right side. Remove all actions except upvote, downvote and favourite by clicking on all except these 3. Reset filter by clicking on <b>'Reset Filter on Columns'</b> button.
This interaction can be used to detect correlation between 2 or more actions.</li>
<li> <u>Filter on Users</u><br/>General pattern of behaviour can be seen by comparing 2 or more users.
People with high number of searches are looking for something specific. If they have high upvotes, it deduces that they found valid 
answer. If they have high downvotes, they further keep searching. Reset filter by clicking on <b>'Reset Filter on Rows'</b> button. This interaction can be used to define general pattern of actions by users.</li>

</ul>
</div>
</body>
</html>