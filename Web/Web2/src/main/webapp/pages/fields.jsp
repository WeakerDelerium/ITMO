<%@ page import="model.ArchiveRecord" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="util.Transformer" %>
<%
    @SuppressWarnings("unchecked")
    ArrayList<ArchiveRecord> archiveData = (ArrayList<ArchiveRecord>) request.getSession().getAttribute("archive");
    if (archiveData != null) for (ArchiveRecord record : archiveData) {
%>
<tr>
    <td><%= Transformer.doubleTransform.apply(record.getX()) %></td>
    <td><%= Transformer.doubleTransform.apply(record.getY()) %></td>
    <td><%= record.getR() %></td>
    <td><%= Transformer.booleanTransform.apply(record.isHit()) %></td>
    <td><%= record.getSent() %></td>
    <td><%= Transformer.doubleTransform.apply(record.getExec()) %></td>
</tr>
<% } %>