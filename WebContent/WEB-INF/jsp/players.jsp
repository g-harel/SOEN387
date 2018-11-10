<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
	"players": [
		<c:forEach items="${players}" var="player" varStatus="stat">
			{
				"id": <c:out value="${player.id}"/>,
				"user": "<c:out value="${player.user}"/>"
			}
			<c:if test="${!stat.last}">,</c:if>
		</c:forEach>
	]
}