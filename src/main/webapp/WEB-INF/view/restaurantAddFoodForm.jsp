<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
    <head>
        <title>Add New Food Item</title>
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
            <h2>Add New Food Item</h2>
            <form:form method="POST" action="/restaurantAdmin/${id}/modifyRestaurant/${restaurantId}/addFood" modelAttribute="food">
                <div class="mb-3">
                    <label for="name" class="form-label">Food Name</label>
                    <form:input type="text" path="name" class="form-control"
                    id="name" placeholder="Enter food name" />
                    <form:errors path="name" cssClass="error" />
                </div>

                <div class="mb-3">
                    <label for="price" class="form-label">Price</label>
                    <form:input type="number" step="0.50" path="price" class="form-control"
                    id="price" placeholder="Enter food price" />
                    <form:errors path="price" cssClass="error" />
                </div>

                <div class="mb-3">
                    <label for="categories" class="form-label">Select Food Categories</label>
                    <select multiple class="form-control" id="categories" name="categories">
                        <c:forEach var="category" items="${categories}">
                            <option value="<c:out value='${category.id}' />">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Add Food</button>
            </form:form>
        </div>
    </body>
</html>
