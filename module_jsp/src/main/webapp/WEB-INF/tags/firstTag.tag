<%@ tag import="java.text.DateFormat" %>
<%@ tag import="java.util.Date" %><%
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    Date now = new Date(System.currentTimeMillis());
    out.println(dateFormat.format(now));
%>