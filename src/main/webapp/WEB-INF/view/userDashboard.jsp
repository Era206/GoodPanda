<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Search Restaurants</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .search-container {
            margin: 20px;
        }

        .search-container button {
            border: none;
            background-color: #ff3366;
            color: #fff;
            margin:20px;
            padding: 10px 10px;
            border-radius: 30px 30px 30px 30px;
        }

        .results-container {
            margin: 20px 0;
        }

        .restaurant-actions button {
            border: none;
            background-color: #ff3366;
            color: #fff;
            padding: 10px 10px;
            border-radius: 30px 30px 30px 30px;
        }

        .restaurant-actions button:hover,
         .search-container btn-primary:hover {
            background-color: #0056b3;
        }

    </style>
</head>
<body>
    <div class="mb-3">
        <h3>Switch Dashboard</h3>
        <form>
            <select class="form-select" onchange="navigateToDashboard(this)">
                <option value="" disabled selected>Select a Role</option>
                <c:forEach var="roleUrl" items="${rolesWithUrls}">
                    <option value="<c:out value='${roleUrl[1]}' />">
                        <c:out value="${roleUrl[0]}" />
                    </option>
                </c:forEach>
            </select>
        </form>
    </div>
    <div class="container">
        <h1>Search Restaurants</h1>
        <div class="search-container">
            <form:form method="GET" action="/user/${id}" modelAttribute="searchCriteria">
                <div class="row">
                    <div class="col-md-3">
                        <label for="category">Category</label>
                        <select name="categoryId" id="category" class="form-control">
                            <option value="">All Categories</option>
                            <c:forEach var="category" items="${categories}">
                                <option value="<c:out value='${category.id}' />"
                                        <c:if test="${searchCriteria.categoryId == category.id}">selected</c:if>>
                                    <c:out value="${category.name}" />
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-3">
                        <label for="location">Location</label>
                        <select name="locationId" id="location" class="form-control">
                            <option value="">All Locations</option>
                            <c:forEach var="location" items="${locations}">
                                <option value="<c:out value='${location.id}' />"
                                        <c:if test="${searchCriteria.locationId == location.id}">selected</c:if>>
                                    <c:out value="${location.name}" />
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-3">
                        <label for="restaurantName">Restaurant Name</label>
                        <input type="text" id="restaurantName" name="restaurantName"
                               class="form-control" value="<c:out value='${searchCriteria.restaurantName}' />"
                               placeholder="Search by name">
                    </div>
                    <div class="button col-md-3">
                        <button type="submit" class="btn btn-primary">Search</button>
                    </div>
                </div>

            </form:form>
        </div>

        <div class="results-container">
            <h3>Results</h3>
            <c:if test="${restaurants != null && !restaurants.isEmpty()}">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Locations</th>
                            <th>Categories</th>
                            <th>Select Restaurant</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="restaurant" items="${restaurants}">
                            <tr>
                                <td><c:out value="${restaurant.name}" /></td>
                                <td><c:out value="${restaurant.description}" /></td>
                                <td>
                                    <c:forEach var="location" items="${restaurant.locations}">
                                        <c:out value="${location.name}" />
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:forEach var="category" items="${restaurant.categories}">
                                        <c:out value="${category.name}" />
                                    </c:forEach>
                                </td>
                                <td>
                                    <div class="restaurant-actions">
                                          <button onclick="window.location.href='<c:url
                                          value="/user/${id}/order/${restaurant.id}"
                                          />'">Order Now</button>
                                      </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${restaurants == null || restaurants.isEmpty()}">
                <p>No restaurants found.</p>
            </c:if>
        </div>
        <div class="mb-3">
             <h3>Previous Orders</h3>
             <table class="table table-striped">
                 <thead>
                     <tr>
                         <th>Order ID</th>
                         <th>Restaurant Name</th>
                         <th>Delivery Date</th>
                         <th>Delivery Time</th>

                     </tr>
                 </thead>
                 <tbody>
                     <c:forEach var="order" items="${previousOrders}">
                         <tr>
                             <td><c:out value="${order.id}" /></td>
                             <td><c:out value="${order.restaurant.name}" /></td>
                             <td><c:out value="${order.orderDate}" /></td>
                             <td><c:out value="${order.deliveryTime}" /></td>
                         </tr>
                     </c:forEach>
                 </tbody>
             </table>
         </div>

         <div class="mb-3">
             <h3>Current Orders</h3>
             <table class="table table-striped">
                 <thead>
                     <tr>
                         <th>Order ID</th>
                         <th>Restaurant Name</th>
                         <th>Order Placement Date and Time</th>
                         <th>Order Status</th>
                     </tr>
                 </thead>
                 <tbody>
                     <c:forEach var="order" items="${runningOrders}">
                         <tr>
                             <td><c:out value="${order.id}" /></td>
                             <td><c:out value="${order.restaurant.name}" /></td>
                             <td><c:out value="${order.orderPlacementTime}" /></td>
                             <td><c:out value="${order.status}" /></td>
                         </tr>
                     </c:forEach>
                 </tbody>
             </table>
         </div>
    </div>
    <script>
        function navigateToDashboard(select) {
            if (select.value) {
                window.location.href = select.value;
            }
        }
    </script>
</body>
</html>
