<%@ page language="java" contentType="application/json; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
  "decks": [
    <c:forEach items="${decks}" var="deck" varStatus="stat">
      <c:out value="${deck.id}"/><c:if test="${!stat.last}">,</c:if>
    </c:forEach>
  ]
}
