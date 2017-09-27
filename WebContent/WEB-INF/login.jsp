<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login</title>
<link rel="stylesheet" href="CSS/style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>
<body id="loginPage">
<div id="form">
<h1>Enter your Login Details</h1>
<form action="LoginServlet" method="post">  
<label for="name">Name</label>
<input type="text" name="name"><br> 

<label for="password">Password</label>
<input type="password" name="password"><br> 
<div class="buttons">
<input id="submit" type="submit" value="Login">  
</form>  
<button><a href="create.html">Create Account</a></button>  
</div>
</div>
</body>
</html>