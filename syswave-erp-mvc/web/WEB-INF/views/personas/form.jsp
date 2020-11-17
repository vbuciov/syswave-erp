<%-- 
    Document   : form
    Created on : 6/02/2019, 10:45:38 PM
    Author     : victor
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="not empty error">
    <p>${error}</p>
</c:if>
<c:choose>
    <c:when test="${new_record}">
        <h1>New Person</h1> 
    </c:when>    
    <c:otherwise>
        <h1>Modify Person</h1>
    </c:otherwise>
</c:choose>
<s:url var="action_url"  value="${action}" />
<sf:form method="${method}" modelAttribute="item" action="${action_url}">
    <sf:hidden path="id"/>
    <div>
        <label for="nombres">Nombres:</label>
        <sf:input path="nombres" />
    </div>
    <div>
        <label for="apellidos">Apellidos:</label>
        <sf:input path="apellidos" />
    </div>
    <div>
        <label for="nacimiento">Fecha Nacimiento:</label>
        <sf:input type="date" path="nacimiento" />
    </div>
    <div>
        <label for="observaciones">Observacioens:</label>
        <sf:textarea path="observaciones" />
    </div>
    <div>
        <label for="activo">Activo:</label>
        <sf:checkbox path="activo" />
    </div>
    <sf:button>Guardar</sf:button>
    <a href="<s:url value= '/personas/index'/> ">Cancelar</a>
</sf:form>
