<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
    <head>
        <title>Add New User</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
            rel="stylesheet"
        />
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">

         <style>
            .error {
                color:red
            }

            .form-container {
                border: 1px solid #ccc;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                background-color: #f9f9f9;
            }

            .form-container h2 {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
         <div class="container mt-5">
            <div class="form-container">
                <h2>Add New User</h2>
                <form:form method="POST" action="/admin/addUser" modelAttribute="user">

                    <div class="mb-3">
                        <label for="name" class="form-label">User Name</label>
                        <form:input type="text" path="name" class="form-control"
                        id="name" placeholder="Enter user name" />
                        <form:errors path="name" cssClass="error" />
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">User email</label>
                        <form:input type="text" path="email" class="form-control"
                        id="email" placeholder="Enter user email" />
                        <form:errors path="email" cssClass="error" />
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">User password</label>
                        <form:input type="text" path="password" class="form-control"
                        id="password" placeholder="Not more than 8 char" />
                        <form:errors path="password" cssClass="error" />
                    </div>

                    <div class="mb-3">
                        <label for="nid" class="form-label">User nid</label>
                        <form:input type="text" path="nid" class="form-control"
                        id="nid" placeholder="Exactly 10 character" />
                        <form:errors path="nid" cssClass="error" />
                    </div>

                    <div class="mb-3">
                        <label for="address" class="form-label">Address</label>
                        <form:textarea path="address" class="form-control"
                        id="address" placeholder="Enter user address" />
                    </div>

                    <button type="submit" class="btn btn-primary">Save</button>
                </form:form>
            </div>
        </div>
    </body>
</html>
