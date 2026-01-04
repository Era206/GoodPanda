<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
    <head>
        <title>Modifying Restaurant</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
            rel="stylesheet"
        />
         <style>
            .error {color:red}
            .modify-restaurant button{
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
        <div class="modify-restaurant form-floating container mt-5">

            <form:form method="POST" action="/admin/modifyRestaurant/${id}"
                       modelAttribute="restaurant">

            <h2>Modify Restaurant with ID <c:out value="${restaurant.id}" /></h2>

                <div class="mb-3">
                    <label for="id" class="form-label">Restaurant ID</label>
                    <input type="text" path="id" class="form-control"
                    id="id" placeholder="<c:out value="${restaurant.id}" />" readonly>
                </div>

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
                    <select multiple class="form-control" id="admins" name="restaurantAdmins" disabled>
                        <c:forEach var="user" items="${restaurant.restaurantAdmins}">
                            <option value="<c:out value='${user.id}' />"><c:out value="${user.name}" /></option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="locations" class="form-label">Select Applied Locations</label>
                    <select multiple class="form-control" id="locations" name="locations">
                        <c:forEach var="location" items="${locations}">
                            <option value="<c:out value='${location.id}' />"
                            <c:if test="${selectedLocations != null && selectedLocations.contains(location.id)}">selected</c:if>>
                                   <c:out value="${location.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="food mb-3">
                    <label for="foods" class="form-label">Food List</label>
                    <select multiple class="form-control" id="locations" name="foods" disabled>
                        <c:forEach var="food" items="${restaurant.foods}">
                            <option value="<c:out value='${food.id}' />"><c:out value="${food.name}" /></option>
                        </c:forEach>
                    </select>

                </div>

                <div class="mb-3">
                    <label for="categories" class="form-label">Food Categories</label>
                    <select multiple class="form-control" id="categories" name="categories" disabled>
                        <c:forEach var="category" items="${restaurant.categories}">
                            <option value="<c:out value='${category.id}' />"><c:out value="${category.name}" /></option>
                        </c:forEach>
                    </select>
                </div>

                 <div class="mb-3">
                     <h3>Previously Completed Orders</h3>
                     <table class="table table-striped">
                         <thead>
                             <tr>
                                 <th>Order ID</th>
                                 <th>Customer Name</th>
                                 <th>Delivery Date</th>
                                 <th>Actions</th>
                             </tr>
                         </thead>
                         <tbody>
                             <c:forEach var="order" items="${completedOrders}">
                                 <tr>
                                     <td><c:out value="${order.id}" /></td>
                                     <td><c:out value="${order.user.name}" /></td>
                                     <td><c:out value="${order.deliveryTime}" /></td>
                                     <td>
                                         <button class="btn btn-danger btn-sm" disabled>Cancel Order</button>
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
                                 <th>Actions</th>
                             </tr>
                         </thead>
                         <tbody>
                             <c:forEach var="order" items="${runningOrders}">
                                 <tr>
                                     <td><c:out value="${order.id}" /></td>
                                     <td><c:out value="${order.user.name}" /></td>
                                     <td><c:out value="${order.orderDate}" /></td>
                                     <td>
                                         <a href="<c:url value='/admin/cancelOrder/${order.id}' />"
                                         class="btn btn-warning btn-sm">Cancel Order</a>
                                     </td>
                                 </tr>
                             </c:forEach>
                         </tbody>
                     </table>
                 </div>

                <button type="submit" class="btn btn-outline-dark button">Update Restaurant</button>
            </form:form>
        </div>
    </body>
</html>
