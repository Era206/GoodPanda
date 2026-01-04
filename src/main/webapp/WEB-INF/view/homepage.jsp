<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Homepage</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f9f9f9;
            }

            .home-view {
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding: 50px;
                background-color: #fff;
            }

            .home-view .text-content {
                max-width: 50%;
            }

            .home-view h1 {
                font-size: 2.5rem;
                font-weight: bold;
                color: #333;
                margin-bottom: 20px;
            }

            .home-view .search-bar {
                display: flex;
                align-items: center;
                gap: 10px;
                margin-top: 20px;
            }

            .home-view select.form-control {
                width: auto;
                flex-grow: 1;
            }

            .home-view .btn-primary {
                background-color: #ff3366;
                color: #fff;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
            }

            .home-view .btn-primary:hover {
                background-color: #e64a5f;
            }

            .home-view img {
                max-width: 40%;
                height: auto;
            }
        </style>
    </head>
    <body>
        <div class="home-view container-fluid">
            <div class="text-content">
                <h1>It’s the food and groceries you love, delivered</h1>
                <form action="<c:url value='/' />" method="GET" class="search-bar">
                    <label for="location" class="visually-hidden">Location</label>
                    <select name="locationId" id="location" class="form-control">
                        <option value="">All Locations</option>
                        <c:forEach var="location" items="${locations}">
                            <option value="${location.id}"
                                    <c:if test="${searchCriteria.locationId == location.id}">selected</c:if>>
                                ${location.name}
                            </option>
                        </c:forEach>
                    </select>
                    <button type="submit" class="btn btn-primary">Search</button>
                </form>
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
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="restaurant" items="${restaurants}">
                                    <tr>
                                        <td>${restaurant.name}</td>
                                        <td>${restaurant.description}</td>
                                        <td>
                                            <c:forEach var="location" items="${restaurant.locations}">
                                                ${location.name}
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="category" items="${restaurant.categories}">
                                                ${category.name}
                                            </c:forEach>
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
            </div>

            <img src="${pageContext.request.contextPath}/resources/images/winking.png" alt="Delivery">
        </div>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    </body>
</html>
