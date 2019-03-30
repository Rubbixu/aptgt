package edu.auburn.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.StringUtils;

import edu.auburn.domain.EduUser;
import edu.auburn.domain.Semester;
import edu.auburn.service.ISemesterService;
import edu.auburn.service.IUserService;
import edu.auburn.service.impl.SemesterService;
import edu.auburn.service.impl.UserService;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
	IUserService userService = new UserService();
	ISemesterService semesterService = new SemesterService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session == null) {
			resp.sendRedirect(req.getContextPath() + "/user");
		} else {
			String user = (String) session.getAttribute("user");
			
			if (user == null || "".equals(user)) {
				resp.sendRedirect(req.getContextPath() + "/user");
			} else {
				EduUser u = userService.getUserByName(user);
				if(null != u && u.getType() == 1){
					String method = req.getParameter("method");
					if(StringUtils.isNullOrEmpty(method)){
						req.getRequestDispatcher("/jsp/admin_manage.jsp").forward(req, resp);
					}else if(method.equals("usermanage")){
						showuser(req, resp);
					}else if(method.equals("updatet")){
						int uid = Integer.parseInt(req.getParameter("uid"));
						EduUser eu = userService.getUserById(uid);
						if(null != eu && eu.getType() != 1){
							userService.updateUserTypeById(uid, 2);
						}
						showuser(req, resp);
					}else if(method.equals("updates")){
						int uid = Integer.parseInt(req.getParameter("uid"));
						EduUser eu = userService.getUserById(uid);
						if(null != eu && eu.getType() != 1){
							userService.updateUserTypeById(uid, 4);
						}
						showuser(req, resp);
					}else if(method.equals("deletes")){
						int uid = Integer.parseInt(req.getParameter("uid"));
						EduUser eu = userService.getUserById(uid);
						if(null != eu && eu.getType() != 1){
							userService.delUserById(uid);
						}
						showuser(req, resp);
					} else if (method.equals("managesemester")){
						manageSemester(req, resp);
					} else if (method.equals("setcurrent")) {
						setCurrentSemester(req, resp);
					} else if (method.equals("addSemester")) {
						addSemester(req, resp);
					} else if (method.equals("all")) {
						showuser(req, resp);
					} else if (method.equals("teachers")) {
						teachers(req, resp);
					} else if (method.equals("students")) {
						students(req, resp);
					} else if (method.equals("non")) {
						nons(req, resp);
					} else if (method.equals("search")){
						String e = req.getParameter("email");
						EduUser eu = userService.getUserByEmail(e);
						List<EduUser> list = new ArrayList<>();
						if (eu!=null && eu.getType() != 1){			// could not search administrator
							list.add(eu);
						}
						req.setAttribute("list", list);
						req.getRequestDispatcher("/jsp/admin_user_manage.jsp").forward(req, resp);
					}
				}else{
					resp.sendRedirect(req.getContextPath()+"");
				}
			}
		}
	}

	private void setCurrentSemester(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String semesterid = req.getParameter("semesterid");
		int sid = Integer.parseInt(semesterid);
		semesterService.setDefaultById(sid);
		manageSemester(req, resp);
	}
	
	
	private void addSemester(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String y = req.getParameter("year");
		String s = req.getParameter("semester");
		if(!StringUtils.isNullOrEmpty(y) && !StringUtils.isNullOrEmpty(s)){
			int year = Integer.parseInt(y);
			int sem = Integer.parseInt(s);
			Semester semester = new Semester();
			semester.setIscurrent(0);
			semester.setSemester(sem);
			semester.setYear(year);
			semesterService.addSemester(semester);
		}
		manageSemester(req, resp);
	}
	
	
	private void manageSemester(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Semester> list = semesterService.getSemesterList();
		req.setAttribute("list", list);
		req.getRequestDispatcher("/jsp/admin_semester_manage.jsp").forward(req, resp);
	}
	private void teachers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<EduUser> list = userService.getTeachers();
		req.setAttribute("list", list);
		req.getRequestDispatcher("/jsp/admin_user_manage.jsp").forward(req, resp);
	}
	private void nons(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<EduUser> list = userService.getNons();
		req.setAttribute("list", list);
		req.getRequestDispatcher("/jsp/admin_user_manage.jsp").forward(req, resp);
	}
	private void students(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<EduUser> list = userService.getStudents();
		req.setAttribute("list", list);
		req.getRequestDispatcher("/jsp/admin_user_manage.jsp").forward(req, resp);
	}
	private void showuser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<EduUser> list = userService.getAll();
		req.setAttribute("list", list);
		req.getRequestDispatcher("/jsp/admin_user_manage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
