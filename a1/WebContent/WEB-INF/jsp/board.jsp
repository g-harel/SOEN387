<%@ page language="java" contentType="application/json; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
  "board": {
  	"id": <c:out value="${gameId}"/>,
    "players": [
      <c:forEach items="${hands}" var="hand" varStatus="stat">
        <c:out value="${hand.playerId}"/><c:if test="${!stat.last}">,</c:if>
      </c:forEach>
    ],
    "decks": [
      <c:forEach items="${hands}" var="hand" varStatus="stat">
        <c:out value="${hand.deckId}"/><c:if test="${!stat.last}">,</c:if>
      </c:forEach>
    ],
    "play": {
      <c:forEach items="${hands}" var="hand" varStatus="stat">
        "<c:out value="${hand.playerId}"/>": {
          "status": "<c:out value="${hand.status}"/>",
          "handsize": <c:out value="${hand.handSize}"/>,
          "decksize": <c:out value="${hand.deckSize}"/>,
          "discardsize": <c:out value="${hand.discardSize}"/>,
          "bench": [
            <c:forEach items="${hand.bench}" var="card" varStatus="stat_inner">
              <c:out value="${card}"/><c:if test="${!stat_inner.last}">,</c:if>
            </c:forEach>
          ]
        }<c:if test="${!stat.last}">,</c:if>
      </c:forEach>
    }
  }
}