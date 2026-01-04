# GoodPanda 🐼

GoodPanda is a structured Java web application leveraging servlets, JSP, filters, and a layered architecture for scalable enterprise-ready development. It features well-defined layers for controllers, services, domains, DTOs, and schedulers—making it ideal for backend logistics, delivery proof management, or similar workflows.

---

## 🚀 Features

- 📦 Place and manage food orders
- 👥 Role-based access: Admins, Riders, and Customers
- 🗺️ Track assigned and completed orders
- 🖼️ Upload and view delivery proof (with image display using Base64)
- 🔐 Login and session-based authentication
- 🌐 JSP frontend with clean UI and navigation

---

## 🛠️ Tech Stack

- **Backend:** Java, Spring Framework (Spring MVC, Spring Web)
- **Frontend:** JSP, JSTL, Bootstrap
- **Database:** OracleDB with Hibernate ORM
- **Other:** Apache Tomcat, SiteMesh for layouting

---

## 📁 Project Structure
```
GoodPanda/
├── src/
│   └── main/
│       ├── java/
│       │   └── goodpanda/
│       │       ├── controller/       # Servlet controllers
│       │       ├── domain/           # Entity classes
│       │       ├── dto/              # Data Transfer Objects
│       │       ├── filter/           # Authentication and request filters
│       │       ├── helper/           # Utility/helper classes
│       │       ├── propertyEditor/   # Property editors for custom binding
│       │       ├── scheduler/        # Scheduled jobs
│       │       ├── service/          # Service layer for business logic
│       │       ├── util/             # Utility functions
│       │       └── validator/        # Input validators
│       ├── resources/
│       │   ├── images/               # Static images
│       │   ├── log4j2.xml            # Logging configuration
│       │   └── messages.properties   # Internationalization messages
│       └── webapp/
│           ├── resources/            # Static web resources (CSS, JS, etc.)
│           └── WEB-INF/
│               ├── jsp/              # JSP views
│               └── web.xml           
├── queries/                         
├── good_panda.svg                   
├── build.gradle                     
└── .gitignore                        
```
---

## ⚙️ How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/Era206/GoodPanda.git
   cd GoodPanda
   
2. **Configure database**

   - Update your DB connection details in the Hibernate configuration (usually hibernate.cfg.xml or Spring’s applicationContext.xml).

3. **Build and deploy**

   - Build the project with Gradle or your IDE.

   - Deploy the WAR or source to Apache Tomcat or another compatible servlet container.
   
## 🧪 Testing

JUnit tests are written using assertEquals() and other assertions to validate controller logic and service behavior.

## 🙋 Author

Developed by Sanjida Islam Era

## 📃 License

This project is licensed under the Apache 2.0 License.
---
