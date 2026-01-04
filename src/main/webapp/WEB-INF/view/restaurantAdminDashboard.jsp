<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Restaurant Admin Dashboard</title>
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
        rel="stylesheet"
    />
    <style>
        .error { color: red; }
        .modify-restaurant button {
            margin-top: 10px;
            border: 10px;
            background-color: #ff3366;
            color: #fff;
            padding: 5px 10px;
        }
        .modify-restaurant button:hover {
            background-color: #0056b3;
        }
        a.button {
            margin-top: 10px;
            border: 10px;
            background-color: transparent;
            color: #000;
            border: 1px solid #000;
        }
        a.button:hover {
            background-color: #ff3366;
            color: #fff;
            border-color: #ff1a50;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <h1>Restaurant Admin Dashboard</h1>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <c:if test="${not empty restaurant}">
        <h2>Summary of Restaurant</h2>
        <p><strong>Name:</strong> <c:out value="${restaurant.name}" /></p>
        <p><strong>Description:</strong> <c:out value="${restaurant.description}" /></p>
        <p><strong>Locations:</strong>
            <c:forEach var="location" items="${restaurant.locations}">
                <c:out value="${location.name}" />
            </c:forEach>
        </p>

        <h3>Orders</h3>
        <div>
            <h4>Completed Orders</h4>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer</th>
                    <th>Delivery Date</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${completedOrders}">
                    <tr>
                        <td><c:out value="${order.id}" /></td>
                        <td><c:out value="${order.user.name}" /></td>
                        <td><c:out value="${order.deliveryTime}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

         <div class="mb-3">
              <h3>Pending Orders</h3>
              <table class="table table-striped">
                  <thead>
                      <tr>
                          <th>Order ID</th>
                          <th>Customer Name</th>
                          <th>Order Date</th>
                          <th>Location</th>
                          <th>Actions</th>
                      </tr>
                  </thead>
                  <tbody>
                      <c:forEach var="order" items="${pendingOrders}">
                          <tr>
                              <td><c:out value="${order.id}" /></td>
                              <td><c:out value="${order.user.name}" /></td>
                              <td><c:out value="${order.orderDate}" /></td>
                              <td><c:out value="${order.location.name}" /></td>
                              <td>
                                  <a href="/restaurantAdmin/${id}/acceptOrder/${order.id}"
                                  class="btn btn-warning btn-sm">Accept Order</a>
                              </td>
                          </tr>
                      </c:forEach>
                  </tbody>
              </table>
          </div>

         <div class="mb-3">
             <h3>Currently Running Orders</h3>
             <table class="table table-striped">
                 <thead>
                     <tr>
                         <th>Order ID</th>
                         <th>Customer Name</th>
                         <th>Order Date</th>
                         <th>Location</th>
                         <th>Actions</th>
                     </tr>
                 </thead>
                 <tbody>
                     <c:forEach var="order" items="${runningOrders}">
                         <tr>
                             <td><c:out value="${order.id}" /></td>
                             <td><c:out value="${order.user.name}" /></td>
                             <td><c:out value="${order.orderDate}" /></td>
                             <td><c:out value="${order.location.name}" /></td>
                             <td>
                                 <a href="<c:url value='/restaurantAdmin/${id}/cancelOrder/${order.id}' />"
                                 class="btn btn-warning btn-sm">Cancel Order</a>
                             </td>
                         </tr>
                     </c:forEach>
                 </tbody>
             </table>
         </div>

        <button type="button" class="btn btn-primary mt-3" data-bs-toggle="modal"
                data-bs-target="#modifyRestaurantModal">
            Modify Restaurant
        </button>
        <a href="<c:url value='/user/${id}' />" class="btn btn-outline-dark mt-3">Go to User Dashboard</a>
    </c:if>
</div>

<div class="modal fade" id="modifyRestaurantModal" tabindex="-1" aria-labelledby="modifyRestaurantModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modifyRestaurantModalLabel">Modify Restaurant</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form:form method="POST" action="/restaurantAdmin/${id}/modifyRestaurant/${restaurant.id}"
                           modelAttribute="restaurant">

                    <div class="mb-3">
                        <label for="name" class="form-label">Restaurant Name</label>
                        <form:input type="text" path="name" class="form-control"
                        id="name" />
                        <form:errors path="name" cssClass="error" />
                    </div>

                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <form:textarea path="description" class="form-control"
                        id="description" />
                    </div>

                    <div class="mb-3">
                        <label for="admins" class="form-label">Restaurant Admins</label>
                        <select multiple class="form-control" id="admins" name="restaurantAdminIds" disabled>
                            <c:forEach var="user" items="${restaurant.restaurantAdmins}">
                                <option value="<c:out value='${user.id}' />" selected>${user.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <c:forEach var="user" items="${restaurant.restaurantAdmins}">
                        <input type="hidden" name="restaurantAdminIds" value="<c:out value='${user.id}' />" />
                    </c:forEach>

                    <div class="mb-3">
                        <label for="locations" class="form-label">Select Applied Locations</label>
                        <select multiple class="form-control" id="locations" name="locations">
                            <c:forEach var="location" items="${locations}">
                                <option value="<c:out value='${location.id}' />"
                                <c:if test="${selectedLocations != null && selectedLocations.contains(location.id)}">selected</c:if>>
                                       <c:out value='${location.name}' />
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="food mb-3">
                        <label for="foods" class="form-label">Food List</label>
                        <select multiple class="form-control" id="locations" name="foods" disabled>
                            <c:forEach var="food" items="${restaurant.foods}">
                                <option value="<c:out value='${food.id}' />"><c:out value='${food.name}' /></option>
                            </c:forEach>
                        </select>

                        <a href="<c:url value='/restaurantAdmin/${id}/modifyRestaurant/${restaurant.id}/addFood' />"
                        class="btn btn-outline-dark button">Add New Food</a>
                    </div>

                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
