<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoodPanda</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <style>
      .navbar-brand {
        display: flex;
        align-items: center;
        gap: 10px;
      }

      .navbar-brand img {
        width: 40px;
        height: 40px;
      }

      a.btn-login {
        background-color: transparent;
        color: #000;
        border: 1px solid #000;
      }

      a.btn-login:hover {
        background-color: #ff3366;
        color: #fff;
        border-color: #ff1a50;
      }

      a.btn-signup {
        background-color: #ff3366;
        color: white;
        border: none;
      }

      a.btn-signup:hover {
        background-color: #ff1a50;
      }

      .navbar-text {
        display: flex;
        align-items: center;
        gap: 15px;
      }

      .dropdown-toggle {
        background: none;
        border: none;
        color: inherit;
        padding: 0;
      }

      .cart-icon {
        font-size: 20px;
      }
    </style>
  </head>
  <body>
    <nav class="navbar navbar-light bg-light px-3">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">
          <img src="https://cdn.brandfetch.io/idwkVJ1Ig4/w/820/h/828/theme/dark/logo.png" alt="Logo" />
          <span>Goodpanda</span>
        </a>
        <div class="navbar-text">
          <a href="/login" class="btn btn-outline-dark btn-login">Log in</a>
          <a href="/signup" class="btn btn-signup">Sign up</a>
          <a href="/logout" class="btn btn-outline-dark btn-login">Log Out</a>
        </div>
      </div>
    </nav>
  </body>
</html>
