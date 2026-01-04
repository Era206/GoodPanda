<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
%>

<html>
    <head>
        <title><sitemesh:write property='title'/></title>
        <sitemesh:write property='head'/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
    </head>

    <body>
        <jsp:include page="../components/navigationBar.jsp" />
        <main>
            <sitemesh:write property='body'/>
        </main>
        <jsp:include page="../components/footer.jsp" />
    </body>
</html>