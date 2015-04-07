<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Welcome to Discussion</title>
    <link rel="stylesheet" href="/_static/css/developer.css" type="text/css"/>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

</head>
<body class="bg-grey-gradient">
   
        <div class="title">Discuss.on</div>
        <div class="menu">
            <a href="/about">About</a>
            <a href="/discuss">Discuss</a>
            <a href="/start">Start One</a>
        </div>
        <c:forEach var="snippet" items="${snippets}">
        	<c:choose>
        		<c:when test="${snippet == selectedSnippet}">
        			<div class="snippet-wrapper selected no-select"  data-snippet="${snippet.id}" data-left="${snippet.leftCoordinate}" 
        			data-top="{snippet.topCoordinate}" style="left: ${snippet.leftCoordinate}; top: ${snippet.topCoordinate}">
        		</c:when>
        		<c:otherwise>
        			<div class="snippet-wrapper background no-select"  data-snippet="${snippet.id}" data-left="${snippet.leftCoordinate}" 
        			data-top="{snippet.topCoordinate}" style="left: ${snippet.leftCoordinate}; top: ${snippet.topCoordinate}">
        		</c:otherwise>
        	</c:choose>
			
				<div class="snippet">
					<c:if test="${snippet.summary != ''}">
						<div class="statement bubble bubble-right" style="border-color: ${snippet.color};">
							<i class="fa fa-minus collapse-button"></i>
							<div class="collapsed-text hidden">Summary of Your Opinion</div>
							<div class="full-text">
								${snippet.summary}		
								<div class="editor-wrapper">
									Don't agree with how that was phrased? Edit it!
									<form class="editor no-height">
										<textarea>Put in your description of this viewpoint here. Try to remain concise!</textarea>
										<button>Submit</button>
									</form>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${snippet.response != ''}">
						<div class="response bubble bubble-left" style="border-color: ${snippet.randomSecondaryColor};">
							<i class="fa fa-minus collapse-button"></i>
							<div class="collapsed-text hidden">A Response From A Peer</div>
							<div class="full-text">
								${snippet.response}
							</div>
						</div>
					</c:if>
					<div class="question" style="background: ${snippet.randomSecondaryColor};">
						<c:choose>
							<c:when test="${snippet.response != ''}">
								After reading the above, what do you think about the following:
								<div class="prompt">${snippet.prompt}</div>
							</c:when>
							<c:otherwise>
								${snippet.prompt}
							</c:otherwise>
						</c:choose>
					</div>
					
					<div class="answers">
						<c:forEach var="answerText" items="${snippet.answerTexts}" varStatus="loop">
							<div class="answer" data-attached="${snippet.answerIds[loop.index]}" style="background: ${snippet.color};">
								${answerText}
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
        </c:forEach> 
    </div>
    
    <div class="modal">
    
    
    </div>
    <script src="/_static/js/developer.js"></script>
</body>
</html>