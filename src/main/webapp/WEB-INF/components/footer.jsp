<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Footer Example</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <style>
      footer {
        background-color: #f8f9fa;
        padding: 20px 0;
      }

      footer h5 {
        font-size: 1.2rem;
        color: #343a40;
        font-weight: bold;
        margin-bottom: 15px;
      }

      footer a {
        color: #6c757d;
        text-decoration: none;
        font-size: 0.9rem;
      }

      footer a:hover {
        color: #ff3366;
        text-decoration: underline;
      }

      footer p, footer li {
        font-size: 0.9rem;
        color: #6c757d;
        margin: 5px 0;
      }

      .social-icons a {
        font-size: 1.2rem;
        color: #343a40;
        margin-right: 10px;
        transition: color 0.3s;
      }

      .social-icons a:hover {
        color: #ff3366;
      }

      .footer-bottom {
        background-color: #e9ecef;
        text-align: center;
        padding: 10px 0;
        margin-top: 20px;
      }
    </style>
  </head>

  <body>
    <footer>
      <div class="container">
        <div class="row">
          <div class="col-md-4">
            <h5>About Goodpanda</h5>
            <p>Goodpanda is your one-stop solution for food delivery, groceries, and more.
            Delivering happiness every day!</p>
          </div>

          <div class="col-md-4">
            <h5>Quick Links</h5>
            <ul class="list-unstyled">
              <li><a href="#">Home</a></li>
              <li><a href="#">Services</a></li>
              <li><a href="#">Privacy Policy</a></li>
              <li><a href="#">Terms and Conditions</a></li>
            </ul>
          </div>

          <div class="col-md-4">
            <h5>Contact Us</h5>
            <ul class="list-unstyled">
              <li><i class="bi bi-telephone"></i> Phone: +123 456 7890</li>
              <li><i class="bi bi-envelope"></i> Email: support@goodpanda.com</li>
              <li><i class="bi bi-geo-alt"></i> Address: 123 Panda Street, City</li>
            </ul>
            <div class="social-icons">
              <a href="#"><i class="bi bi-facebook"></i></a>
              <a href="#"><i class="bi bi-twitter"></i></a>
              <a href="#"><i class="bi bi-instagram"></i></a>
            </div>
          </div>
        </div>

        <div class="footer-bottom">
          <p class="mb-0">&copy; 2024 Goodpanda. All rights reserved. Powered by Era.</p>
        </div>
      </div>
    </footer>
  </body>
</html>
