<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1>Person List</h1>
<p>These are the person currently in the system</p>
<c:choose>
    <c:when test="${not empty data and fn:length(data) gt 0}">
            <table>
                <caption>
                    <a href="<s:url value='/personas/new' />" >
                       New
                    </a>
                </caption>
                <thead>
                    <th>Nombres</th>
                    <th>Apellidos</th>
                    <th>Nacimiento</th>
                    <th>Acciones</th>
                </thead>
                <tbody>
                    <c:forEach items="${data}" var="item">
                        <tr>
                            <td>${item.nombres}</td>
                            <td>${item.apellidos}</td>
                            <td>${item.nacimiento}</td>
                            <td>
                                <a href="<s:url value='/personas/${item.id}' />" >
                                    View
                                </a>||
                                <a href="<s:url value='/personas/edit/${item.id}' />" >
                                    Edit
                                </a>||
                                <a href="<s:url value='/personas/destroy/${item.id}' />" >
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
    </c:when>
    <c:otherwise>
        <p>there's no information</p>
    </c:otherwise>
</c:choose>