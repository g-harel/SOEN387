<%@ page language="java" contentType="application/json; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
  "games": [
    <c:forEach items="${games}" var="game" varStatus="stat">
      {
      	"id": <c:out value="${game.id}"/>,
      	"version": <c:out value="${game.version}"/>,
      	"players": [
      	  <c:out value="${game.player1}"/>,
      	  <c:out value="${game.player2}"/>
     	]
      }<c:if test="${!stat.last}">,</c:if>
    </c:forEach>
  ]
}
