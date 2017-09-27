package com.assignment2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateAccount
 */
@WebServlet("/CreateAccount")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateAccountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Add login and password to an existing login.txt file
		createDirectory();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		if(userExists(request.getParameter("username"))) {
			
			out.println("<h2> User already exists!!!</h2>");
			request.getRequestDispatcher("create.html").include(request, response);
			return;
		}
		
		addLoginEntry(request.getParameter("username"), request.getParameter("password"));
		
		
		System.out.println("Entry successfully put in file.\nLogin name is" + request.getParameter("username"));
		
	
		out.println("You have successfully created your account");
		GlobalConstants.state=States.LOGGEDOUT;
		request.getRequestDispatcher("login.jsp").include(request, response);
	}

	private boolean userExists(String username) {
		//read the login file and check if username exists. if yes, return true
		ArrayList<String> userList = FileUtility.readFromFile(GlobalConstants.directoryPrefix+"Login.txt");
		if(userList.size()==0)
			return true;
		for(String str:userList) {
			String [] splitter= str.split(",");
			String user=splitter[0];
			if(user.equals(username))
				return true;
		}
		return false;
	}

	public static void createDirectory() {
		String directoryName = GlobalConstants.directoryPrefix;
		File directory = new File(directoryName);
		if (!directory.exists()) {
			directory.mkdir();
			File file = new File("./Database/Login.txt");
			FileWriter writer = null;
			try {
				writer= new FileWriter(file,true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BufferedWriter bw = new BufferedWriter(writer);
			try {
				bw.write("aaa,123");
				bw.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				bw.write("bbb,123");
				bw.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				bw.write("ccc,123");
				bw.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(bw!=null)
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		} else
			System.out.println("Directory already exists");
	}

	private void addLoginEntry(String username, String password) {
		String s = String.join(",", username, password);
		FileUtility.createOrAppendContent(GlobalConstants.directoryPrefix+"Login.txt", s);

	}

}
