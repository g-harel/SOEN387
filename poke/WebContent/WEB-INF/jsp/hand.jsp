<%@ page language="java" contentType="application/json; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
  "hand": [
    <c:forEach items="${cards}" var="card" varStatus="stat">
      <c:out value="${card.cardId}"/><c:if test="${!stat.last}">,</c:if>
    </c:forEach>
  ]
}
