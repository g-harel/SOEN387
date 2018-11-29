<%@ page language="java" contentType="application/json; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
  "deck": {
    "id": <c:out value="${deck.id}"/>,
    "cards": [
      <c:forEach items="${deck.cards}" var="card" varStatus="stat">
        {
          "t": <c:out value="${card.type}"/>,
          "n": <c:out value="${card.name}"/>
        }<c:if test="${!stat.last}">,</c:if>
      </c:forEach>
    ]
  }
}