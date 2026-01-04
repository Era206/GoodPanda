<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>adminpage</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f9f9f9;
            }

            admin-view {
                display: flex;
                flex-direction: column;
                gap: 20px;
                padding: 20px;
                background-color: #fff;
            }

            .admin-view > div {
                margin-bottom: 20px;
            }

            .table {
                margin-bottom: 0;
            }

            .admin-view .text-content {
                max-width: 50%;
            }

            .admin-view h1 {
                font-size: 2.5rem;
                font-weight: bold;
                color: #333;
                margin-bottom: 20px;
            }

            .admin-view p {
                font-size: 1rem;
                color: #666;
                margin-bottom: 30px;
            }

            .admin-view .search-bar {
                display: flex;
                align-items: center;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                border-radius: 30px;
                overflow: hidden;
            }

            .admin-view .search-bar input {
                border: none;
                padding: 15px;
                flex-grow: 1;
                border-radius: 30px 0 0 30px;
            }

            .admin-view .search-bar button {
                border: none;
                background-color: #ff3366;
                color: #fff;
                padding: 10px 20px;
                border-radius: 0 30px 30px 0;
            }

            .admin-view img {
                max-width: 40%;
                height: auto;
            }

            .admin-view .btn-secondary {
                margin-left: 10px;
                background-color: #f2f2f2;
                color: #555;
                border: none;
            }

            .admin-view .btn-secondary:hover {
                background-color: #e6e6e6;
            }

            .restaurant-container {
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
                margin-bottom: 20px;
            }

            .restaurant-table {
                flex: 2;
                margin-right: 20px;
            }

            .restaurant-actions {
                display: flex;
                margin-left: 10px;
                text-align: center;
            }

            .add-restaurant img,
              .add-restaurant button {
                  display: block;
                  margin: 0 auto;
                  margin-bottom: 2px;
              }


            .restaurant-actions button,
                .add-restaurant button,
                .restaurant-actions delete-button {
                border: none;
                background-color: #ff3366;
                color: #fff;
                padding: 10px 10px;
                border-radius: 30px 30px 30px 30px;
            }

            .restaurant-actions button:hover,
                .restaurant-actions delete-button:hover,
                .add-restaurant button:hover {
                background-color: #0056b3;
            }

            .add-restaurant img {
                max-width: 100%;
            }
        </style>
    </head>
    <body>
        <div class="admin-view container-fluid d-flex flex-column gap-3">
            <div class = "restaurant">
                <h2>Restaurant List</h2>
                 <div class="restaurant-container">
                      <div class="restaurant-table">
                            <table id="restaurantTable" class="table table-striped display" border="1">
                                  <thead>
                                        <tr>
                                          <th scope="col">Name</th>
                                          <th scope="col">Description</th>
                                          <th scope="col">Action</th>
                                        </tr>
                                  </thead>
                                  <tbody>
                                        <c:forEach var="restaurant" items="${restaurants}">
                                            <tr>
                                                  <td><c:out value="${restaurant.name}" /></td>
                                                  <td><c:out value="${restaurant.description}" />
                                                  <td>
                                                      <div class="restaurant-actions">
                                                          <button onclick="window.location.href='<c:url
                                                          value="/admin/modifyRestaurant/${restaurant.id}" />'">Edit</button>
                                                      </div>
                                                  </td>
                                            </tr>
                                        </c:forEach>
                                  </tbody>
                            </table>
                      </div>
                      <div class="add-restaurant">
                          <img src="${pageContext.request.contextPath}/resources/images/panda_eating.png" alt="Restaurant Image">
                          <button class="btn btn-success mt-3" onclick="window.location.href='<c:url value="/admin/addRestaurant" />'">
                          Add New Restaurant
                          </button>
                      </div>
                 </div>
            </div>
            <div class = "user">
                <h2>User List</h2>
                <table id="userTable" class="table table-striped display" border="1">
                      <thead>
                            <tr>
                              <th scope="col">Name</th>
                              <th scope="col">Email</th>
                              <th scope="col">Address</th>
                              <th scope="col">NID</th>
                            </tr>
                      </thead>
                      <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>

                                  <td><c:out value="${user.name}" /></td>
                                  <td><c:out value="${user.email}" /></td>
                                  <td><c:out value="${user.address}" /></td>
                                  <td><c:out value="${user.nid}" /></td>
                                </tr>
                            </c:forEach>
                      </tbody>
                </table>
                <div class="add-user">
                    <button class="btn btn-success mt-3" onclick="window.location.href='<c:url value="/admin/addUser"/>'">Add New User</button>
                </div>
            </div>

            <div class="rider mb-5">
                <h2>Rider List</h2>
                <table id="riderTable" class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Location</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="rider" items="${riders}">
                            <tr>
                                <td>${rider.id}</td>
                                <td>${rider.user.name}</td>
                                <td>${rider.location.name}</td>
                                <td>${rider.status}</td>
                                <td>
                                    <button class="btn btn-info btn-sm"
                                            onclick="window.location.href='<c:url value="/admin/rider/${rider.id}/orders" />'">
                                        View Orders
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <button class="btn btn-success mt-3" onclick="window.location.href='<c:url value="/admin/addRider" />'">
                    Add New Rider
                </button>
            </div>
        </div>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

        <script>
            $(document).ready(function() {
                $('#restaurantTable, #userTable, #riderTable').DataTable({
                    "paging": true,
                    "searching": true,
                    "info": true,
                    "lengthChange": false,
                    "ordering": true,
                    "pageLength": 5
                });
            });
        </script>
    </body>
</html>
