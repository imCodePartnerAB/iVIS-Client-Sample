<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: ruslan
  Date: 06.12.16
  Time: 12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Unauthorized</title>
</head>
<body>
${requestScope["javax.servlet.error.exception"].message}
</body>
</html>
