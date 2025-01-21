<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*"%>
<html>
<head>
    <title>Users</title>
</head>
<body>
    ${users}
    </br>
    </br>

    <%
    ArrayList<String> users = (ArrayList<String>) request.getAttribute("users");

    for (String user : users) {
    %>
        <a href="/user?usr=<%=user%>"><%=user%></a></br>
    <%
    }
    %>

    </br>
    <a href="/welcome">Welcome</a>
    </br>
    <a href="/logout">Logout</a>
    </br>

</body>
</html>