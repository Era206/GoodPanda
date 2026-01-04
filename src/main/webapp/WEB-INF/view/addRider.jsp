<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Add New Rider</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2>Add New Rider</h2>
            <form:form method="POST" action="/admin/addRider" modelAttribute="rider">
                <div class="mb-3">
                    <label for="user" class="form-label">User</label>
                    <form:select path="user.id" class="form-control" id="user">
                        <form:option value="" label="-- Select User --" />
                        <c:forEach var="user" items="${users}">
                            <form:option value="${user.id}" label="${user.name}" />
                        </c:forEach>
                    </form:select>
                </div>
                <div class="mb-3">
                    <label for="location" class="form-label">Location</label>
                    <form:select path="location.id" class="form-control" id="location">
                        <form:option value="" label="-- Select Location --" />
                        <c:forEach var="location" items="${locations}">
                            <form:option value="${location.id}" label="${location.name}" />
                        </c:forEach>
                    </form:select>
                </div>
                <button type="submit" class="btn btn-primary">Save</button>
            </form:form>
            <div class="mt-4">
                <a href="<c:url value='/admin' />" class="btn btn-primary">Cancel</a>
            </div>
        </div>
    </body>
</html>
