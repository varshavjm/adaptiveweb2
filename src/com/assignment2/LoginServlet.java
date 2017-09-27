package com.assignment2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (GlobalConstants.state == States.LOGGEDIN)
			request.getRequestDispatcher("index.jsp").include(request, response);
		else {
			
		}
			response.sendRedirect("login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (GlobalConstants.state != null && GlobalConstants.state == States.LOGGEDIN) {
			response.sendRedirect("index.jsp");
			return;
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		//Create the default logins for aaa,bbb,ccc
		CreateAccountServlet.createDirectory();
		
		
		if (validUser(name, password)) {
		
			GlobalConstants.state = States.LOGGEDIN;
			GlobalConstants.userName = name;
			
			// Append login timestamp to the userHistoryFile
			String timeStamp = new SimpleDateFormat("dd-MM-YYYY   HH:mm:ss").format(new Date());
			FileUtility.createOrAppendContent(GlobalConstants.directoryPrefix+name+"History.txt", timeStamp);
			
			
			
			System.out.println("Hey Varsha see the console");
			HttpSession session = request.getSession();
			session.setAttribute("name", name);
			request.getRequestDispatcher("index.jsp").include(request, response);
		} else {
			GlobalConstants.state = States.LOGGEDOUT;
			GlobalConstants.userName = "";
			out.print("<h1>Sorry, username or password error!</h1>");
			request.getRequestDispatcher("login.jsp").include(request, response);
		}
		out.close();
	}

	private boolean validUser(String name, String password) {
		// Reads the login file and checks for correct password and returns flag
		String file = GlobalConstants.directoryPrefix+"Login.txt";
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		br = new BufferedReader(fr);

		String sCurrentLine;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				String[] myarr = sCurrentLine.split(",");
				if (name.equals(myarr[0]) && password.equals(myarr[1]))
					return true;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req= (HttpServletRequest) request;
		HttpServletResponse res= (HttpServletResponse) response;
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("name") == null) {
			res.sendRedirect("login.jsp"); // No logged-in user found, so redirect to login page.
	    } else {
	    	res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	    	res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	    	res.setDateHeader("Expires", 0);
	        chain.doFilter(req, res);  
	    }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
