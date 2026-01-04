<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Add Order</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .food-item-container {
            margin: 20px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <form:form method="POST" action="/user/${id}/order/${foods[0].restaurant.id}">
        <h2>Order from <c:out value="${foods[0].restaurant.name}" /></h2>
        <div class="food-item-container">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Food Name</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="food" items="${foods}">
                        <tr>
                            <td><c:out value="${food.name}" /></td>
                            <td><c:out value="${food.price}" /></td>
                            <td>
                                <a href="/user/${id}/order/${food.restaurant.id}/${food.id}/addOrderItem"
                                   class="btn btn-outline-dark button">Add Item to Order</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="mb-3">
            <label for="deliveryAddress" class="form-label">Delivery Address</label>
            <input type="text" name="deliveryAddress" class="form-control"
                   id="deliveryAddress" placeholder="Enter delivery address" />
        </div>

        <div class="mb-3">
            <label for="location" class="form-label">Select Delivery Location</label>
            <select name="location" class="form-control" id="location" required>
                <option value="" disabled selected>Select a location</option>
                <c:forEach var="location" items="${locations}">
                    <option value="<c:out value='${location.id}' />">
                        <c:out value="${location.name}" />
                    </option>
                </c:forEach>
            </select>
            <form:errors path="location" cssClass="error" />
        </div>

        <button type="submit" class="btn btn-primary">Add Order</button>
    </form:form>
    <a href="<c:url value='/user/${id}/' />" class="btn btn-secondary">Back to Dashboard</a>
</div>
</body>
</html>
