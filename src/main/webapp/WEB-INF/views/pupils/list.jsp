<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="iviv" uri="ivis.sdk" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pupils list</title>
</head>
<body>

<c:forEach items="${pupils}" var="pupil">
    Pupil #${pupil.id}
    <br>
    First name: ${pupil.person.firstName}
    <br>
    Last name: ${pupil.person.lastName}
    <br>
</c:forEach>


</body>
</html>
