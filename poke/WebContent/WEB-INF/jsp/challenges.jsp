<%@ page language="java" contentType="application/json; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
  "challenges": [
    <c:forEach items="${challenges}" var="challenge" varStatus="stat">
      {
      	"id": <c:out value="${challenge.id}"/>,
      	"version": <c:out value="${challenge.version}"/>,
      	"challenger": <c:out value="${challenge.challenger}"/>,
      	"challengee": <c:out value="${challenge.challengee}"/>,
      	"status": <c:out value="${challenge.status}"/>,
      	"deck": <c:out value="${challenge.deckId}"/>
      }<c:if test="${!stat.last}">,</c:if>
    </c:forEach>
  ]
}
