<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Add Order Item</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <h2>Add Order Item</h2>
    <form:form method="POST" modelAttribute="orderItem" class="form">
        <div class="mb-3">
            <label for="foodName" class="form-label">Food Name</label>
            <input type="text" id="foodName" class="form-control" value="<c:out value='${orderItem.food.name}' />" readonly />
        </div>

        <div class="mb-3">
            <label for="quantity" class="form-label">Quantity</label>
            <form:input type="number" path="quantity" id="quantity" class="form-control" value="${orderItem.quantity}" min="0"/>
            <form:errors path="quantity" cssClass="text-danger" />
        </div>

        <button type="submit" class="btn btn-primary">Add Item</button>

        <a href="/user/${id}/order/${orderItem.food.restaurant.id}" class="btn btn-secondary">Cancel</a>
    </form:form>
</div>
</body>
</html>
