<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="com.eh.demo.view.ForumView"%>
<html>
<head>
    <title>Forum</title>
</head>
<body>
    <font color="red">${errorMessage}</font>
    </br>

    <%
    ArrayList<ForumView> comments = (ArrayList<ForumView>) request.getAttribute("forums");

    for (ForumView comment : comments) {
    %>
        <%=comment.getTimestamp()%> | <%=comment.getComment()%> | => <%=comment.getUser()%> </br></br>
    <%
    }
    %>
    <form method="post">
        Comment : <input type="text" name="comment" />
        <input type="submit" />
    </form>

    </br>
    <a href="/welcome">Welcome</a>
    </br>
    <a href="/logout">Logout</a>
    </br>

</body>
</html>