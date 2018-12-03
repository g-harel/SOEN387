<%@ page language="java" contentType="application/json; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
  "games": [
    <c:forEach items="${challenges}" var="challenge" varStatus="stat">
      {
      	"id": <c:out value="${challenge.id}"/>,
      	"version": <c:out value="${challenge.version}"/>,
      	"challenger": <c:out value="${challenge.challenger}"/>
      }<c:if test="${!stat.last}">,</c:if>
    </c:forEach>
  ]
}
