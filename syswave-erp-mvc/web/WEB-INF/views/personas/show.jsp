<%-- 
    Document   : show
    Created on : 5/02/2019, 11:45:53 PM
    Author     : victor
--%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<p>
    Nombres: ${item.nombres}
</p>
<p>
    Apellidos: ${item.apellidos}
</p>
<p>
    Observaciones: ${item.observaciones}
</p>
<p>
    Nacimiento: ${item.nacimiento}
</p>
<p>
    Activo: ${item.activo}
</p>

<br/>
<a href="<s:url value='/personas/'/>" > Regresar </a>
