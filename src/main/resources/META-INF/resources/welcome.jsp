<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    Welcome ${cookie.usr.value} ${usr}!!
    </br>
    <font color="red">${errorMessage}</font>
    </br>
    ${roles}
    </br>


    <a href="/users">Users</a>
    <a href="/forum">Forum</a>
    <a href="/logout">Logout</a>
    </br>

</body>
</html>