package com.assignment2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class ActionMapServlet
 */
@WebServlet("/ActionMapServlet")
public class ActionMapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionMapServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//user is not logged in send response to login page
		if (GlobalConstants.state != States.LOGGEDIN)
			response.sendRedirect("login.jsp");
		
		ArrayList<String> fileContent= FileUtility.readFromFile("./Database/login.txt");
		//Populate action list with userNames
		
		String responseString=populateActionListWithUserNames(fileContent);
		GlobalConstants.actionlist=new ArrayList<Action>();
		System.out.println(responseString);
		 response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(responseString);
		    return;
		
	}

	private String populateActionListWithUserNames(ArrayList<String> fileContent) {
		StringBuilder bld= new StringBuilder();
		for(String file:fileContent) {
			String []fileSplit=file.split(",");
			Action action = new Action(fileSplit[0]);
			action=populateActions(action);
			bld=bld.append(action.print());
			GlobalConstants.actionlist.add(action);
		}
		
		String json = new Gson().toJson(GlobalConstants.actionlist);
	   return json;
		//FileUtility.overWrite(GlobalConstants.directoryPrefix+"AllActions.txt", new Gson().toJson(GlobalConstants.actionlist));
	}

	private Action populateActions(Action act) {
		Map<String,Integer>map= act.actions;
		map.put("Favorite", 0);
		map.put("Upvote", 0);
		map.put("Downvote", 0);
		map.put("Comment", 0);
		map.put("Post", 0);
		
		ArrayList<String> fileContent= FileUtility.readFromFile(GlobalConstants.directoryPrefix+act.userName+"Actions.txt");
		
		for(String line:fileContent) {
			String [] temp=line.split(",");
			map.put(temp[0], map.getOrDefault(temp[0],0)+1);
		}
		act.setActionsMap(map);
		return act;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
