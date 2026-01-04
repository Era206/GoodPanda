<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
    <head>
        <title>Add New Restaurant</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
            rel="stylesheet"
        />
         <style>
            .error {color:red}
        </style>
    </head>
    <body>
        <div class="form-floating container mt-5">
            <h2>Add New Restaurant</h2>
            <form:form method="POST" action="/admin/addRestaurant"
                       modelAttribute="restaurant">
                <div class="mb-3">
                    <label for="name" class="form-label">Restaurant Name</label>
                    <form:input type="text" path="name" class="form-control"
                    id="name" placeholder="Enter restaurant name" />
                    <form:errors path="name" cssClass="error" />
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <form:textarea path="description" class="form-control"
                    id="description" placeholder="Enter restaurant description" />
                </div>

                <div class="mb-3">
                    <label for="admins" class="form-label">Select Restaurant Admins</label>
                    <select multiple class="form-control" id="admins" name="restaurantAdmins">
                        <c:forEach var="user" items="${users}">
                            <option value="<c:out value='${user.id}' />">${user.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="locations" class="form-label">Select Applied Locations</label>
                    <select multiple class="form-control" id="locations" name="locations">
                        <c:forEach var="location" items="${locations}">
                            <option value="<c:out value='${location.id}' />">${location.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Add Restaurant</button>
            </form:form>
        </div>
    </body>
</html>
