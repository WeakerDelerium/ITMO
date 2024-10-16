<%@ page import="model.ArchiveRecord" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="util.Transformer" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Web2</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/archive.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="main">
    <div class="container">
        <div class="main__inner">
            <div class="form">
                <form class="form__body">
                    <div class="X-input">
                        <p class="X-input__text form-text">X</p>
                        <ul class="X-input__list form-list">
                            <% for (String value: new String[]{"-2", "-1.5", "-1", "-0.5", "0", "0.5", "1", "1.5", "2"}) { %>
                                <li class="X-input__item form-item">
                                    <label>
                                        <input type="button" name="X" value="<%= value %>" class="X-input__btn form-btn">
                                    </label>
                                </li>
                            <% } %>
                        </ul>
                    </div>
                    <div class="Y-input">
                        <p class="Y-input__text form-text">Y</p>
                        <label>
                            <input type="text" name="Y" placeholder="Введите Y" class="Y-input__input form-input">
                        </label>
                    </div>
                    <div class="R-input">
                        <p class="R-input__text form-text">R</p>
                        <label>
                            <select name="R" class="R-input__select form-select">
                                <option value="-1" disabled selected hidden>Выберите R</option>
                                <% for (String value: new String[]{"1", "1.5", "2", "2.5", "3"}) { %>
                                    <option value="<%= value %>"><%= value %></option>
                                <% } %>
                            </select>
                        </label>
                    </div>
                    <input type="submit" class="form-submit">
                </form>
                <div class="console"></div>
            </div>
            <div class="graph">
                <svg class="graph__body" width="600" height="600" viewBox="-300 -300 600 600" xmlns="http://www.w3.org/2000/svg">
                    <line x1="-300" x2="300" y1="0" y2="0" class="graph__axis"></line>
                    <line x1="0" x2="0" y1="-300" y2="300" class="graph__axis"></line>
                    <path d="M 280 10 L 300 0 L 280 -10" class="graph__arrow"></path>
                    <path d="M 10 -280 L 0 -300 L -10 -280" class="graph__arrow"></path>
                    <line x1="-200" x2="-200" y1="5" y2="-5" class="graph__serif"></line>
                    <line x1="-100" x2="-100" y1="5" y2="-5" class="graph__serif"></line>
                    <line x1="100" x2="100" y1="5" y2="-5" class="graph__serif"></line>
                    <line x1="200" x2="200" y1="5" y2="-5" class="graph__serif"></line>
                    <line x1="5" x2="-5" y1="-200" y2="-200" class="graph__serif"></line>
                    <line x1="5" x2="-5" y1="-100" y2="-100" class="graph__serif"></line>
                    <line x1="5" x2="-5" y1="100" y2="100" class="graph__serif"></line>
                    <line x1="5" x2="-5" y1="200" y2="200" class="graph__serif"></line>
                    <text x="282" y="33" class="graph__sub">X</text>
                    <text x="192" y="33" class="graph__sub sub-R">R</text>
                    <text x="82" y="33" class="graph__sub sub-R_div_2">R/2</text>
                    <text x="-135" y="33" class="graph__sub sub-R_div_2">-R/2</text>
                    <text x="-220" y="33" class="graph__sub sub-R">-R</text>
                    <text x="-33" y="-282" class="graph__sub">Y</text>
                    <text x="-30" y="-192" class="graph__sub sub-R">R</text>
                    <text x="-50" y="-92" class="graph__sub sub-R_div_2">R/2</text>
                    <text x="-67" y="108" class="graph__sub sub-R_div_2">-R/2</text>
                    <text x="-45" y="208" class="graph__sub sub-R">-R</text>
                    <path d="M -100 0 A 100 100 0 0 1 0 -100 L 0 -200 200 -200 200 0 0 0 0 200 -200 0 Z" class="graph__figure"></path>
                    <%
                        @SuppressWarnings("unchecked")
                        ArrayList<ArchiveRecord> archiveData = (ArrayList<ArchiveRecord>) request.getSession().getAttribute("archive");
                        if (archiveData != null) for (ArchiveRecord record : archiveData) {
                            double r = record.getR();

                            double scale = 200 / r;
                            double x = Transformer.doubleTransform.apply(record.getX()) * scale;
                            double y = -Transformer.doubleTransform.apply(record.getY()) * scale;

                            String classPosition = record.isHit() ? "in" : "out";
                    %>
                    <circle cx="<%= x %>" cy="<%= y %>" r="5" class="graph__point <%= classPosition %> resonance"></circle>
                    <% } %>
                </svg>
            </div>
            <div class="archive">
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
                <div class="viperr"></div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script>window.root = "${pageContext.request.contextPath}"</script>
<script src="${pageContext.request.contextPath}/static/js/main.js"></script>
</body>
</html>