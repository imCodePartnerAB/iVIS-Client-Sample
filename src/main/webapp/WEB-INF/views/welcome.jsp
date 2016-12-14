<%@ taglib prefix="ivis" uri="ivis.sdk" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Welcome page</h1>
<ivis:authorized>
    <a href="${pageContext.servletContext.contextPath}/services/classes">Create school class</a>
</ivis:authorized>
</body>
</html>
