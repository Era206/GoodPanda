<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Rider Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <h1>Rider Dashboard</h1>

        <div class="mb-4">
            <h3>Assigned Orders</h3>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Restaurant</th>
                        <th>Order Placement Time</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${assignedOrders}">
                        <tr>
                            <td><c:out value="${order.id}" /></td>
                            <td><c:out value="${order.restaurant.name}" /></td>
                            <td><c:out value="${order.orderPlacementTime}" /></td>
                            <td>
                                <form:form method="POST" action="/rider/${riderId}/order/${order.id}/deliver" enctype="multipart/form-data">
                                    <input type="file" name="deliveryProofImage" placeholder="Delivery Proof" class="form-control mb-2" required />
                                    <button type="submit" class="btn btn-success">Mark as Delivered</button>
                                </form:form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="mb-4">
            <h3>Completed Orders</h3>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Restaurant</th>
                        <th>Delivery Time</th>
                        <th>Delivery Proof</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${completedOrders}">
                        <tr>
                            <td><c:out value="${order.id}" /></td>
                            <td><c:out value="${order.restaurant.name}" /></td>
                            <td><c:out value="${order.deliveryTime}" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty order.deliveryProofBase64}">
                                        <img src="data:image/jpg;base64, ${order.deliveryProofBase64}" alt="Proof" style="max-width: 100px; max-height: 100px;" />
                                    </c:when>
                                    <c:otherwise>
                                        No Proof Available
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <a href="<c:url value='/user/${rider.user.id}' />" class="btn btn-outline-dark">Switch to User Dashboard</a>
    </div>
</body>
</html>
