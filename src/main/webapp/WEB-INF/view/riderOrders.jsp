<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Rider Orders</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Rider Orders</h2>

    <h4>Assigned Orders</h4>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Order ID</th>
            <th>Restaurant</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${assignedOrders}">
            <tr>
                <td>${order.id}</td>
                <td>${order.restaurant.name}</td>
                <td>${order.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h4>Completed Orders</h4>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Order ID</th>
            <th>Restaurant</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${completedOrders}">
            <tr>
                <td>${order.id}</td>
                <td>${order.restaurant.name}</td>
                <td>${order.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="mt-4">
        <a href="<c:url value='/admin' />" class="btn btn-primary">Back to Admin Dashboard</a>
    </div>
</div>
</body>
</html>
