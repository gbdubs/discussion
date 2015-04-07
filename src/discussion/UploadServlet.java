package discussion;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html");
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/upload.jsp");	
		jsp.forward(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String discussionName = req.getParameter("discussion-name");
		String discussionSnippets = req.getParameter("discussion-snippets");
		String startingSnippet = req.getParameter("start-snippet");
		
		Discussion.createNewDiscussion(discussionName, startingSnippet, discussionSnippets);
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Added Successfully");
	}
}