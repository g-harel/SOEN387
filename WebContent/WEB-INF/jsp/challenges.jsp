<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
	"challenges": [
		<c:forEach items="${challenges}" var="challenge" varStatus="stat">
			{
				"id": <c:out value="${challenge.id}"/>,
				"challenger": <c:out value="${challenge.challenger}"/>,
				"challengee": <c:out value="${challenge.challengee}"/>,
				"status": <c:out value="${challenge.status}"/>
			}
			<c:if test="${!stat.last}">,</c:if>
		</c:forEach>
	]
}