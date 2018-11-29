<%@ page language="java" contentType="application/json; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
  "games": [
    <c:forEach items="${games}" var="game" varStatus="stat">
      {
        "id": <c:out value="${game.id}"/>,
        "players": [
          <c:forEach items="${game.players}" var="playerId" varStatus="stat_inner">
            <c:out value="${playerId}"/><c:if test="${!stat_inner.last}">,</c:if>
          </c:forEach>
        ]
      }<c:if test="${!stat.last}">,</c:if>
    </c:forEach>
  ]
}