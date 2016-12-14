<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:url value="/resources/js/school_classes/edit.js" var="editJsUrl" />
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="${editJsUrl}"></script>
    <script>
        $(function () {
            var pupilsAutoComplete = new InputAutocomplete('#search-pupils', '#list-pupils');
            pupilsAutoComplete.autoComplete('pupils', '#selected-pupils');
        });
    </script>
</head>
<body>
<form:form modelAttribute="schoolClass" method="post" servletRelativeAction="/services/classes">
    <div class="ui-widget">
        <form:label path="name">Name:</form:label>
        <br>
        <form:input path="name"/>
        <br>
        <form:label path="schoolDayStart">Start of school day:</form:label>
        <br>
        <fmt:formatDate value="${schoolDayStart}" var="schoolDayStartFormatted" type="time" pattern="HH:mm"/>
        <input type="time" name="schoolDayStart" value="${schoolDayStartFormatted}">
        <br>
        <form:label path="schoolDayStart">End of school day:</form:label>
        <br>
        <fmt:formatDate value="${schoolDayEnd}" var="schoolDayEndFormatted" type="time" pattern="HH:mm"/>
        <input type="time" name="schoolDayEnd" value="${schoolDayEndFormatted}">
        <br>
        <form:label path="pupils">Pupils</form:label>
        <br>
        <input id="search-pupils" type="text">
        <select id="list-pupils" style="display: none;">
            <c:forEach items="${pupilList}" var="pupil">
                <option value="${pupil.id}">${pupil.person.lastName}${' '}${pupil.person.firstName}</option>
            </c:forEach>
        </select>
        <div id="selected-pupils">
        </div>
        <br>
        <form:label path="school">School</form:label>
        <br>
        <form:select path="school" items="${schoolList}" itemValue="id">
        </form:select>
        <br>
        <br>
        <input type="submit" value="Create"/>
    </div>
</form:form>
</body>
</html>
