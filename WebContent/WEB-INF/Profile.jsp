<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ page import="com.assignment2.FileUtility"%>
     <%@ page import="com.assignment2.GlobalConstants"%>
     <%@ page import="java.util.ArrayList" %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="refresh" content="3">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Action History</title>
<link rel="stylesheet"
	href="CSS/style.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
</head>
<body>
<a href="index.jsp">Go Back</a>
<!-- Read the user ka profile action and render it -->
<% 
response.setIntHeader("Refresh", 3); 
String user= GlobalConstants.userName;
if(user.length()>0){
	

	ArrayList<String> list=FileUtility.readFromFile(GlobalConstants.directoryPrefix+user+"Actions.txt");
%><table id="actions">
<th>Actions</th>
<th>TimeStamp</th>    
<% 	for(String str:list){
		String [] temp=str.split(",");
		%><tr>
		<td><%=temp[0]%></td>
		<td><%=temp[1]%></td>
		</tr>
<% 	}
	
%>
</table>
<% 
}
%>
</body>
</html>