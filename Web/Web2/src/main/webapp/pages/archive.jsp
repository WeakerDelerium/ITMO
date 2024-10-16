<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/archive.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="archive">
    <div class="container">
        <h2 class="archive__heading">Архив</h2>
        <table class="archive__table table-archive">
            <thead class="table-archive__head">
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Hit</th>
                <th>Sent</th>
                <th>Exec</th>
            </tr>
            </thead>
            <tbody class="table-archive__main">
            <jsp:include page="fields.jsp"/>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}" class="archive__return">Вернуться назад</a>
        <div class="viperr"></div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>