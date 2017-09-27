package com.assignment2;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MonitorServlet
 */
@WebServlet("/MonitorServlet")
public class MonitorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonitorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html"); 
		String action="";
        PrintWriter out=response.getWriter(); 
		
		if(GlobalConstants.state==States.LOGGEDIN) {
		System.out.println("Users action is "+request.getParameter("action"));
		switch(request.getParameter("action")) {
		case "Comment":
			
			action="Comment";
			break;
		case "Search":
			
			action="Search";
			break;
		case "Post":
			
			action="Post";
			break;
		case "Scroll":
			
			action="View";
			return;
		case "Favourite":
			
			action="Favorite";
			break;
		case "Upvote":
			
			action="Upvote";
			break;
		case "Downvote":
			
			action="Downvote";
			break;
			
			
		}
		
		String name= GlobalConstants.userName;
		String timeStamp = new SimpleDateFormat("dd-MM-YYYY").format(new Date());
		FileUtility.createOrAppendContent(GlobalConstants.directoryPrefix+name+"Actions.txt", "".join(",",action,timeStamp));
		
		}
		
		
		}
	

}
