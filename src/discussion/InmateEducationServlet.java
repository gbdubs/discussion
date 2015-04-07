package discussion;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class InmateEducationServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		Discussion discussion = Discussion.getDiscussion("InmateEducation");
		
		Snippet starter = Snippet.getSnippet(discussion.firstSnippet);
		List<Snippet> background = discussion.getSnippets();
		
		req.setAttribute("snippets", background);
		req.setAttribute("selectedSnippet", starter);
		
		resp.setContentType("text/html");
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/discussion.jsp");	
		jsp.forward(req, resp);
	}
}
