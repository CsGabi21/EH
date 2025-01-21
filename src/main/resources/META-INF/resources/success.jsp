<%@ page import = "java.io.*,java.util.*" %>

<html>
   <head>
      <title>Success login</title>
   </head>

   <body>
      <center>
         <h1>SUCCESS</h1>
      </center>
      <%
         String site = new String("http://localhost:8080/welcome.jsp");
         response.setStatus(response.SC_MOVED_TEMPORARILY);
         response.setHeader("Location", site);
      %>
   </body>
</html>