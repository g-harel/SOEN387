<%@ page language="java" contentType="application/json; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
  "game": {
   	"id": <c:out value="${game.id}"/>,
   	"version": <c:out value="${game.version}"/>,
   	"players": [
   	  <c:out value="${game.player1}"/>,
   	  <c:out value="${game.player2}"/>
   	],
    "current": <c:out value="${game.turn}"/>,
   	"decks": [
   	  <c:out value="${game.deck1}"/>,
   	  <c:out value="${game.deck2}"/>
   	],
    "play": {
      "<c:out value="${game.player1}"/>": {
        "status": "<c:out value="${game.status1}"/>",
        "handsize": <c:out value="${handsize1}"/>,
        "decksize": <c:out value="${decksize1}"/>
      },
      "<c:out value="${game.player2}"/>": {
        "status": "<c:out value="${game.status2}"/>",
        "handsize": <c:out value="${handsize2}"/>,
        "decksize": <c:out value="${decksize2}"/>
      }
    }
  }<c:if test="${!stat.last}">,</c:if>
}
