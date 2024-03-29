package edu.auburn.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.mysql.jdbc.StringUtils;

import edu.auburn.domain.Comment;
import edu.auburn.domain.DisplayStudentExamResult;
import edu.auburn.domain.EduUser;
import edu.auburn.domain.Exam;
import edu.auburn.domain.ExamResult;
import edu.auburn.domain.ExamTeacherResult;
import edu.auburn.domain.ExamVideo;
import edu.auburn.domain.ExamWord;
import edu.auburn.domain.Lesson;
import edu.auburn.domain.LessonFile;
import edu.auburn.domain.LessonStudent;
import edu.auburn.domain.ResultScoreDistribute;
import edu.auburn.domain.Semester;
import edu.auburn.domain.WordStudent;
import edu.auburn.domain.WordVideo;
import edu.auburn.service.ICommentService;
import edu.auburn.service.IDistributeService;
import edu.auburn.service.IExamResultService;
import edu.auburn.service.IExamService;
import edu.auburn.service.IExamVideoService;
import edu.auburn.service.IExamWordService;
import edu.auburn.service.ILessonFileService;
import edu.auburn.service.ILessonService;
import edu.auburn.service.ILessonStudentService;
import edu.auburn.service.IResultScoreDistributeService;
import edu.auburn.service.ISemesterService;
import edu.auburn.service.IUserService;
import edu.auburn.service.IWordStudentService;
import edu.auburn.service.IWordVideoService;
import edu.auburn.service.impl.CommentService;
import edu.auburn.service.impl.DistributeService;
import edu.auburn.service.impl.ExamResultService;
import edu.auburn.service.impl.ExamService;
import edu.auburn.service.impl.ExamVideoService;
import edu.auburn.service.impl.ExamWordService;
import edu.auburn.service.impl.LessonFileService;
import edu.auburn.service.impl.LessonService;
import edu.auburn.service.impl.LessonStudentService;
import edu.auburn.service.impl.ResultScoreDistributeService;
import edu.auburn.service.impl.SemesterService;
import edu.auburn.service.impl.UserService;
import edu.auburn.service.impl.WordStudentService;
import edu.auburn.service.impl.WordVideoService;
import edu.auburn.utils.CalculateScore;
import edu.auburn.utils.DownloadUtils;
import edu.auburn.utils.LessonFileType;
import edu.auburn.utils.StringConfig;

@WebServlet("/teacher")
public class TeacherServlet extends HttpServlet {
	private ISemesterService semesterServic = new SemesterService();
	private IUserService userService = new UserService();
	private ILessonService lessonService = new LessonService();
	private int uid;
	private ILessonFileService fileService = new LessonFileService();
	private ILessonStudentService studentService = new LessonStudentService();
	private IExamService examService = new ExamService();
	private IExamVideoService videoService = new ExamVideoService();
	private IExamWordService wordService = new ExamWordService();
	private IExamResultService resultService = new ExamResultService();
	private IWordVideoService wordVideoService = new WordVideoService();
	private IWordStudentService wordStudentService = new WordStudentService();
	private ICommentService commentService = new CommentService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();
		if (session == null) {
			resp.sendRedirect(req.getContextPath() + "/user");
		} else {
			String user = (String) session.getAttribute("user");

			EduUser u = userService.getUserByName(user);
			if (null != u && u.getType() == 2) {
				uid = u.getUid();
				req.setAttribute("uid", uid);
				String method = req.getParameter("method");
				if (StringUtils.isNullOrEmpty(method)) {
					req.getRequestDispatcher("/jsp/teacher_manage.jsp").forward(req, resp);
				} else if (method.equals("lessonlist")) {
					showlessons(req, resp);
				} else if (method.equals("add")) {
					addLesson(req, resp);
					showlessons(req, resp);
				} else if (method.equals("delete")) {
					delete(req, resp);
					showlessons(req, resp);
				} else if (method.equals("details")) {
					details(req, resp);
				} else if (method.equals("managestudent")) {
					manageStudent(req, resp);
				} else if (method.equals("update")) {
					updateRoleTa(req, resp);
				} else if (method.equals("degrade")) {
					updateRoleStu(req, resp);
				} else if (method.equals("upload")) {
					upload(req, resp);
				} else if (method.equals("deletef")) {
					deleteFile(req, resp);
				} else if (method.equals("editlesson")) {
					editLesson(req, resp);
				} else if (method.equals("updatelesson")) {
					updateLesson(req, resp);
				} else if (method.equals("archive")) {
					archive(req, resp);
				} else if (method.equals("searchclass")){
					searchClass(req, resp);
				} else if (method.equals("copyclass")) {
					copyClass(req, resp);
				} else if (method.equals("searchstudent")) {
					searchStudent(req, resp);
				} else if (method.equals("addstudenttolesson")) {
					addStudentToLesson(req, resp);
				} else if (method.equals("delstudentfromlesson")) {
					delStudentFromLesson(req, resp);
				}
				/*************************************************************************
				 *************************************************************************
				 ******* from here the functions are all the functions relate to
				 * exam******
				 *************************************************************************
				 *************************************************************************
				 */
				else if (method.equals("examlist")) {
					examList(req, resp);
				} else if (method.equals("addexam")) {
					addExam(req, resp, user);
				} else if (method.equals("delexam")) {
					delExam(req, resp);
				} else if (method.equals("examdetails")) {
					examDetails(req, resp);
				} else if (method.equals("addvideo")) {
					addVideo(req, resp);
				} else if (method.equals("delv")) {
					deleteVideo(req, resp);
				} else if (method.equals("addword")) {
					addWord(req, resp);
				} else if (method.equals("delw")) {
					deleteWord(req, resp);
				} else if (method.equals("teacher_download")){
					teacherDownLoad(req, resp);
				} else if (method.equals("checke")){
					examResult(req, resp);
				} else if (method.equals("checkse")){
					checkStudentExamResult(req, resp);
				} else if (method.equals("wordDetails")){
					wordDetails(req, resp);
				} else if (method.equals("wordAddVideo")){
					wordAddVideo(req, resp);
				} else if (method.equals("deleteWordVideo")){
					deleteWordVideo(req, resp);
				} else if (method.equals("edite")){
					editExam(req, resp);
				} else if (method.equals("updateexam")){
					updateExam(req, resp);
				} else if (method.equals("addComment")){
					addComment(req, resp);
				} else if (method.equals("comments")){
					commentPage(req, resp);
				} else if (method.equals("checkdistance")){
					checkDistance(req, resp);
				} else if (method.equals("checkotheranswer")){
					checkOtherAnswer(req, resp);
				} else if (method.equals("checkdistribution")){
					checkDistribution(req, resp);
				}
			} else {
				resp.sendRedirect(req.getContextPath() + "");
			}
		}
	}
	
	private void copyClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cid = req.getParameter("id");
		String level = req.getParameter("level");
		String name = req.getParameter("name");
		String desc = req.getParameter("desc");
		lessonService.copyClass(Integer.parseInt(cid), level, name, desc, uid);
		showlessons(req, resp);
	}
	
	private void searchClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String year = req.getParameter("year");
		String s = req.getParameter("semester");
		if(!StringUtils.isNullOrEmpty(year) && !StringUtils.isNullOrEmpty(s)) {
			Semester semester = semesterServic.getSemesterByYearAndSmallId(Integer.parseInt(year), Integer.parseInt(s));
			List<Lesson> list = null;
			if(semester != null){
				//list = lessonService.getLessonsBySemesterId(semester.getId()); 
				list = lessonService.getLessonsByUid(uid, semester.getId());// in this way, we will only have this teacher's class
			}
			req.setAttribute("classes", list);
		}
		archive(req, resp);
	}
	
	private void archive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Semester semester = semesterServic.getDefaultSemester();
		req.setAttribute("semester", semester);
		req.getRequestDispatcher("/jsp/teacher_manage_archive.jsp").forward(req, resp);
	}
	/**
	 * teacher - update lesson
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateLesson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int lid = Integer.parseInt(req.getParameter("lid"));
		String name = req.getParameter("lname");
		String desc = req.getParameter("ldesc");
		if(StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(desc)){
			req.setAttribute("message", "name and desc can not be empty!");
			req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		}else{
			lessonService.updateLessonNameAndDescById(name, desc, lid);
			showlessons(req, resp);
		}
	}
	/**
	 * teacher - edit exam
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void editLesson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int lid = Integer.parseInt(req.getParameter("lid"));
		Lesson lesson = lessonService.getLessonByLid(lid);
		req.setAttribute("lesson", lesson);
		req.getRequestDispatcher("/jsp/teacher_edit_lesson.jsp").forward(req, resp);
	}

	
	
	IDistributeService distributeService = new DistributeService();
	/**
	 * teacher-check-word-distribution
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void checkDistribution(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int wid = Integer.parseInt(req.getParameter("wid"));
		int eid = Integer.parseInt(req.getParameter("eid"));
		int sid = Integer.parseInt(req.getParameter("sid"));
		HashMap<String, Integer> pieChart = distributeService.getAnswerGroup(eid, wid);
		ArrayList<Integer> barChart = distributeService.getDistanceGroup(eid, wid);
		List<Integer> position = distributeService.getPositionAndCount(sid, eid, wid);
		String sname = userService.getUserById(sid).getName();
		Exam exam = examService.getExamById(eid);
		List<WordStudent> wordStudentList = distributeService.getDistanceListByEidAndWid(eid, wid);
		req.setAttribute("answerList", wordStudentList);
		req.setAttribute("sid", sid);
//		resp.getWriter().write(""+barChart.toString() + "," + pieChart.toString() + ","+position.toString());
		req.setAttribute("bar", barChart);
		req.setAttribute("pie", pieChart);
		req.setAttribute("exam", exam);
		req.setAttribute("position", position);
		req.setAttribute("sname", sname);
		req.getRequestDispatcher("/jsp/new_teacher_check_distance_distribution.jsp").forward(req, resp);
	}
	/**
	 * teacher_check_other_answers
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void checkOtherAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String s_eid = req.getParameter("eid");
		String s_wid = req.getParameter("wid");
		String stu_id = req.getParameter("sid");
		int sid = Integer.parseInt(stu_id);
		int eid = Integer.parseInt(s_eid);
		int wid = Integer.parseInt(s_wid);
		Exam exam = examService.getExamById(eid);
		ExamWord ew = wordService.getExamWordByEidAndWid(eid, wid);
		WordStudent wordStudent = wordStudentService.getStudentAnswerModelBySidAndWid(sid, wid);
		String path = ew.getPath();
		int index = path.indexOf(File.separator + "upload");
		String p = req.getContextPath() + path.substring(index);
		ew.setPath(p);
		List<WordStudent> result =  wordStudentService.getStudentAnswerListByEidAndWid(eid, wid);
		req.setAttribute("exam", exam);
		req.setAttribute("result", result);
		req.setAttribute("ew", ew);
		req.setAttribute("wordstudent", wordStudent);
		req.getRequestDispatcher("/jsp/teacher_check_word_answer_result_list.jsp").forward(req, resp);
	}
	
	
	private IResultScoreDistributeService distanceService = new ResultScoreDistributeService();
	private void checkDistance(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int eid = Integer.parseInt(req.getParameter("eid"));
		Exam exam = examService.getExamById(eid);
		ResultScoreDistribute distanceDistribute = distanceService.getDistanceDistribute(eid);
		req.setAttribute("exam", exam);
		req.setAttribute("distribute", distanceDistribute);
		req.getRequestDispatcher("/jsp/teacher_check_distance_distribution.jsp").forward(req, resp);
	}
	
	/**
	 * teacher - add - comment
	 * 
	 * @param req
	 * @param resp
	 */
	private void addComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String c = req.getParameter("comment");
		if (c.length() < 5 || c.length() > 2000) {
			req.setAttribute("message", StringConfig.COMMENT_SIZE);
		} else {
			Comment comment = new Comment();
			comment.setUid(uid);
			comment.setComment(c);
			commentService.addComment(comment);
			req.setAttribute("message", StringConfig.COMMENT_SUCCESS);
		}
		commentPage(req, resp);
	}

	/**
	 * teacher-comment
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void commentPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Comment> comments = commentService.getAll();
		req.setAttribute("list", comments);
		req.getRequestDispatcher("/jsp/teacher_comment_manage.jsp").forward(req, resp);
	}
	/**
	 * teacher-edit-exam here...............
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void editExam(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("eid");
		int eid = Integer.parseInt(id);
		id = req.getParameter("lid");
		int lid = Integer.parseInt(id);
		Lesson l = lessonService.getLessonByLid(lid);
		Exam e = examService.getExamById(eid);
		req.setAttribute("exam", e);
		req.setAttribute("lesson", l);
		req.getRequestDispatcher("/jsp/teacher_edit_exams.jsp").forward(req, resp);
	}
	/**
	 * teacher-update-exam
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateExam(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("eid");
		int eid = Integer.parseInt(id);
		id = req.getParameter("lid");
		int lid = Integer.parseInt(id);
		Exam e = examService.getExamById(eid);
		String edue = req.getParameter("edue");
		String isPrac = req.getParameter("isprac");
		String etype = req.getParameter("etype");
		String name = req.getParameter("ename");
		String desc = req.getParameter("edesc");
		if(StringUtils.isNullOrEmpty(edue) || StringUtils.isNullOrEmpty(isPrac) || StringUtils.isNullOrEmpty(etype) 
				|| StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(desc) || StringUtils.isNullOrEmpty(edue)){
			String message = "Please input all the informations";
			req.setAttribute("message", message);
			req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
		} 
		else {
			String d = edue.substring(0, 10) + " " + edue.substring(11, edue.length());
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			java.util.Date date = null;
			try {
				date = f.parse(d);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			examService.updateDueDateAndTypeByEid(date.getTime(), Integer.parseInt(isPrac), eid, Integer.parseInt(etype), name, desc);
			req.setAttribute("lid", lid);
			req.setAttribute("eid", eid);
			examList(req, resp);
		}
	}
	
	/**
	 * teacher-new-word-delete-video
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteWordVideo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("wid");
		int wid = Integer.parseInt(id);
		id = req.getParameter("eid");
		int eid = Integer.parseInt(id);
		id = req.getParameter("lid");
		int lid = Integer.parseInt(id);
		id = req.getParameter("vid");
		int vid = Integer.parseInt(id);
		wordVideoService.delVideoById(vid);
		wordDetails(req, resp);
	}
	
	/**
	 * teacher-new-word-details
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void wordDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("wid");
		int wid = Integer.parseInt(id);
		id = req.getParameter("eid");
		int eid = Integer.parseInt(id);
		id = req.getParameter("lid");
		int lid = Integer.parseInt(id);
		Exam exam = examService.getExamById(eid);
		Lesson lesson = lessonService.getLessonByLid(lid);
		ExamWord word = wordService.getExamWordByFid(wid);
		List<WordVideo> videos = wordVideoService.getAllVideosByWid(wid);
		req.setAttribute("exam", exam);
		req.setAttribute("lesson", lesson);
		req.setAttribute("word", word);
		req.setAttribute("videos", videos);
		req.getRequestDispatcher("/jsp/new_teacher_exam_word_details.jsp").forward(req, resp);
	}
	
	
	/**
	 * teacher-new-word-add-videos
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void wordAddVideo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String id = req.getParameter("lid");
		int lid = Integer.parseInt(id);
		id = req.getParameter("eid");
		int eid = Integer.parseInt(id);
		id = req.getParameter("wid");
		int wid = Integer.parseInt(id);
		Lesson lesson = lessonService.getLessonByLid(lid);
		Exam exam = examService.getExamById(eid);
		ExamWord word = wordService.getExamWordByFid(wid);
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		long size = 1024 * 1024 * 1024;
		upload.setFileSizeMax(size);
		upload.setSizeMax(2 * size);
		upload.setHeaderEncoding("UTF-8");
		WordVideo video = new WordVideo();
		if (upload.isMultipartContent(req)) {
			try {
				List<FileItem> list = upload.parseRequest(req);
				for (FileItem item : list) {
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString();
						if (name.equals("fdesc")) {
							video.setDesc(value);
						}
					} else {
						String name = System.currentTimeMillis() + "_" + item.getName().replaceAll(" ", "_");// file
						int begin = name.lastIndexOf(".");
						String ext = name.substring(begin + 1, name.length());// ext
						String type = getType(ext);
						if (!type.equals(LessonFileType.MP4.toString())) {
							// file illegal
							String message = StringConfig.FILENOTSUPPORTED;
							req.setAttribute("message", message);
							// forward
						} else {
							String basePath = getServletContext().getRealPath(File.separator+"upload"+File.separator + lesson.getName().replaceAll(" ", "_") + File.separator + exam.getName().replaceAll(" ", "_") + File.separator + word.getFid() + File.separator);
							File dir = new File(basePath);
							if (!dir.exists() && !dir.isDirectory()) {
								dir.mkdirs();
							}
							File file = new File(basePath, name);
							item.write(file);
							item.delete();
							video.setName(name);
							video.setEid(eid);
							video.setWid(wid);
							video.setPath(basePath + File.separator + name);
							wordVideoService.addVideo(video);
						}
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		wordDetails(req, resp);
		
		
		
	}
	
	/**
	 * teacher-check-student-exam-details
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void checkStudentExamResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("eid");
		int eid = Integer.parseInt(id);
		id = req.getParameter("uid");
		int uid = Integer.parseInt(id);
		EduUser user = userService.getUserById(uid);
		Exam exam = examService.getExamById(eid);
		ExamResult examResult = resultService.getResultByUidAndEid(uid, eid);
		
		List<DisplayStudentExamResult> ds = new ArrayList<>();
		List<WordStudent> wordStudents = wordStudentService.getStudentAnswerListBySidAndEid(uid, eid);
		float totalPercentage = 0;
		for(int i = 0; i < wordStudents.size(); i++){
			WordStudent ws = wordStudents.get(i);
			DisplayStudentExamResult studentResult = new DisplayStudentExamResult();
			studentResult.setWid(ws.getWid());
			studentResult.setSid(uid);
			studentResult.setScore(ws.getScore());
			studentResult.setsAnswer(ws.getAnswer());
			ExamWord ew = wordService.getExamWordByFid(ws.getWid());
			studentResult.settAnswer(ew.getPron());
			String percentage = CalculateScore.getPercentage(ws.getAnswer(), ew.getPron(), ws.getScore());
			studentResult.setPercentage(percentage);
			
			totalPercentage += Float.parseFloat(percentage.substring(0, percentage.length() - 1));
			
			ds.add(studentResult);
		}
		
		float aveScore = examResult.getTotal() / wordStudents.size();
		aveScore = (float) Math.round(aveScore * 100) / 100;
		req.setAttribute("ave", aveScore);
		req.setAttribute("result", ds);
		
		totalPercentage = (float) Math.round(totalPercentage * 100000) / 100000;
		req.setAttribute("totalp", totalPercentage + "%");
		float temp = totalPercentage / ds.size();
		float aveP = (float) Math.round(temp * 1000) / 1000;
		req.setAttribute("averagep", aveP + "%");
		req.setAttribute("sid", uid);
		
		req.setAttribute("total", examResult.getTotal()+"");
		req.setAttribute("exam", exam);
		req.setAttribute("uname", user.getName());
		req.getRequestDispatcher("/jsp/teacher_check_student_exam_details.jsp").forward(req, resp);
	}
	
	/**
	 * teacher-check-exam-result
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void examResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("eid");
		int eid = Integer.parseInt(id);
		Exam exam = examService.getExamById(eid);
		
		
		List<ExamResult> teacherCheckResultList = resultService.teacherCheckResultByEid(eid);
		List<ExamTeacherResult> result = new ArrayList<>();
		for(int i = 0; i<teacherCheckResultList.size(); i++){
			ExamResult er = teacherCheckResultList.get(i);
			int uid = er.getUid();
			EduUser user = userService.getUserById(uid);
			String name = user.getName();
			float score = er.getTotal();
			ExamTeacherResult tr = new ExamTeacherResult();
			tr.setEid(eid);
			tr.setSid(uid);
			tr.setScore(score);
			tr.setStuName(name);
			result.add(tr);
		}
		req.setAttribute("result", result);
		req.setAttribute("exam", exam);
		req.getRequestDispatcher("/jsp/teacher_exam_result.jsp").forward(req, resp);
	}
	
	/**
	 * teacher-download-lesson-file
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void teacherDownLoad(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("fid");
		int fid = Integer.parseInt(id);
		LessonFile file = fileService.getFileByFid(fid);
		String name = file.getName();
		String path = file.getPath();
		DownloadUtils utils = new DownloadUtils();
		utils.down(path,name, req, resp);
	}
	/**
	 * teacher-add-word-file
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addWord(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// req.setCharacterEncoding("utf-8");
		String lessonid = req.getParameter("lid");
		int lid = Integer.parseInt(lessonid);
		String examId = req.getParameter("eid");
		int eid = Integer.parseInt(examId);
		Lesson lesson = lessonService.getLessonByLid(lid);
		Exam exam = examService.getExamById(eid);
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		long size = 1024 * 1024 * 1024;
		upload.setFileSizeMax(size);
		upload.setSizeMax(2 * size);
		upload.setHeaderEncoding("UTF-8");
		ExamWord word = new ExamWord();
		if (upload.isMultipartContent(req)) {
			try {
				List<FileItem> list = upload.parseRequest(req);
				for (FileItem item : list) {
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = new String(item.get(), "utf-8");
						if (name.equals("ipa")) {
							word.setPron(value);
						}
					} else {
						String name = System.currentTimeMillis() + "_" + item.getName().replaceAll(" ", "_");// file
						int begin = name.lastIndexOf(".");
						String ext = name.substring(begin + 1, name.length());// ext
						String type = getType(ext);
						if (!type.equals(LessonFileType.WAV.toString())) {
							// file illegal
							String message = StringConfig.FILENOTSUPPORTED;
							req.setAttribute("message", message);
							// forward
						} else {
							String basePath = getServletContext().getRealPath(File.separator+"upload"+File.separator + lesson.getName().replaceAll(" ", "_") + File.separator + exam.getName().replaceAll(" ", "_") + File.separator);
							File dir = new File(basePath);
							if (!dir.exists() && !dir.isDirectory()) {
								dir.mkdirs();
							}
							File file = new File(basePath, name);
							item.write(file);
							item.delete();
							word.setName(name);
							word.setEid(eid);
							word.setPath(basePath + File.separator + name);
							word.setType(type);
							wordService.addWord(word);
						}
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		examDetails(req, resp);
	}

	/**
	 * teacher-delete-exam-word
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteWord(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("vid");
		int fid = Integer.parseInt(id);
		wordService.delWordById(fid);
		examDetails(req, resp);
	}

	/**
	 * teacher-delete-exam-video
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteVideo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("vid");
		int vid = Integer.parseInt(id);
		videoService.delVideoById(vid);
		examDetails(req, resp);
	}

	/**
	 * teacher-add-exam-video
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addVideo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String lessonid = req.getParameter("lid");
		int lid = Integer.parseInt(lessonid);
		String examId = req.getParameter("eid");
		int eid = Integer.parseInt(examId);
		Lesson lesson = lessonService.getLessonByLid(lid);
		Exam exam = examService.getExamById(eid);
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		long size = 1024 * 1024 * 1024;
		upload.setFileSizeMax(size);
		upload.setSizeMax(2 * size);
		upload.setHeaderEncoding("UTF-8");
		ExamVideo video = new ExamVideo();
		if (upload.isMultipartContent(req)) {
			try {
				List<FileItem> list = upload.parseRequest(req);
				for (FileItem item : list) {
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString();
						if (name.equals("fdesc")) {
							video.setDesc(value);
						}
					} else {
						String name = System.currentTimeMillis() + "_" + item.getName().replaceAll(" ", "_");// file
						int begin = name.lastIndexOf(".");
						String ext = name.substring(begin + 1, name.length());// ext
						String type = getType(ext);
						if (!type.equals(LessonFileType.MP4.toString())) {
							// file illegal
							String message = StringConfig.FILENOTSUPPORTED;
							req.setAttribute("message", message);
							// forward
						} else {
							String basePath = getServletContext().getRealPath(File.separator+"upload"+File.separator + lesson.getName() + File.separator + exam.getName() + File.separator);
							File dir = new File(basePath);
							if (!dir.exists() && !dir.isDirectory()) {
								dir.mkdirs();
							}
							File file = new File(basePath, name);
							item.write(file);
							item.delete();
							video.setName(name);
							video.setEid(eid);
							video.setPath(basePath + File.separator + name);
							video.setType(type);
							videoService.addVideo(video);
						}
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		examDetails(req, resp);
	}

	
	
	
	
	
	/**
	 * teacher-exam-details
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */

	private void examDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// req.setCharacterEncoding("UTF-8");
		// resp.setCharacterEncoding("UTF-8");
		String id = req.getParameter("eid");
		int eid = Integer.parseInt(id);
		id = req.getParameter("lid");
		int lid = Integer.parseInt(id);
		Lesson l = lessonService.getLessonByLid(lid);
		Exam e = examService.getExamById(eid);
		//List<ExamVideo> videos = videoService.getAllVideosByEid(eid);
		List<ExamWord> words = wordService.getAllWordsByEid(eid);
		req.setAttribute("words", words);
		//req.setAttribute("videos", videos);
		req.setAttribute("lesson", l);
		req.setAttribute("exam", e);
		req.getRequestDispatcher("/jsp/new_teacher_exam_details.jsp").forward(req, resp);
	}

	/**
	 * teacher-delete-exam
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delExam(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("eid");
		int eid = Integer.parseInt(id);
		examService.delExamById(eid);
		// resp.getWriter().write("delete exam");
		examList(req, resp);
	}

	/**
	 * teacher-add-exam
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addExam(HttpServletRequest req, HttpServletResponse resp, String user)
			throws ServletException, IOException {
		String id = req.getParameter("lid");
		int lid = Integer.parseInt(id);
		req.setAttribute("lid", lid);
		Lesson lesson = lessonService.getLessonByLid(lid);
		String ename = req.getParameter("ename");
		String edesc = req.getParameter("edesc");
		String type = req.getParameter("etype");
		String edue = req.getParameter("edue");
		String isPrac = req.getParameter("isprac");
		if (!StringUtils.isNullOrEmpty(ename) && !StringUtils.isNullOrEmpty(edesc)
				&& !StringUtils.isNullOrEmpty(edue)) {
			Exam e = new Exam();
			e.setName(ename);
			String d = edue.substring(0, 10) + " " + edue.substring(11, edue.length());
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			java.util.Date date;
			try {
				date = f.parse(d);
				e.setEdue(new Date(date.getTime()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.setLid(lid);
			e.setLname(lesson.getName());
			e.setEdesc(edesc);
			e.setEtype(Integer.parseInt(type));
			e.setIfPractice(Integer.parseInt(isPrac));
			e.setUid(uid);
			e.setUname(user);
			examService.addExam(e);
		}
		examList(req, resp);
	}

	/**
	 * teacher-exam-list
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void examList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("lid");
		int lid = Integer.parseInt(id);
		Lesson lesson = lessonService.getLessonByLid(lid);
		req.setAttribute("lid", lid);
		req.setAttribute("lesson", lesson);
		List<Exam> list = examService.getExamsByLid(lid);
		
		req.setAttribute("list", list);
		req.getRequestDispatcher("/jsp/teacher_manage_exams.jsp").forward(req, resp);
	}

	/**
	 * teacher-lesson-file: delete
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("lid");
		int lid = Integer.parseInt(id);
		id = req.getParameter("fid");
		int fid = Integer.parseInt(id);
		fileService.delFileById(fid);
		req.setAttribute("lid", lid);
		req.getRequestDispatcher("/teacher?method=details&lid=" + lid).forward(req, resp);
	}

	/**
	 * teacher-lesson-student: update to TA
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateRoleTa(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sid = req.getParameter("sid");
		String lid = req.getParameter("lid");
		studentService.updateToTaByLidAndSid(Integer.parseInt(lid), Integer.parseInt(sid));
		manageStudent(req, resp);
	}

	/**
	 * teacher-lesson-student: update to student
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateRoleStu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sid = req.getParameter("sid");
		String lid = req.getParameter("lid");
		studentService.updateToStuByLidAndSid(Integer.parseInt(lid), Integer.parseInt(sid));
		manageStudent(req, resp);
	}

	
	private void searchStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String lid = req.getParameter("lid");
		List<EduUser> list = new ArrayList<>();
		EduUser user = userService.getUserByEmail(email);
		if(user.getType() == 4)	{
			list.add(user);
		}
		req.setAttribute("list", list);
		req.setAttribute("lid", lid);
		manageStudent(req, resp);
	}
	
	
	private void delStudentFromLesson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("lid");
		int lid = Integer.parseInt(id);
		id = req.getParameter("sid");
		int sid = Integer.parseInt(id);
		studentService.delLessonStudent(lid, sid);
		req.setAttribute("lid", lid);
		manageStudent(req, resp);
	}
	private void addStudentToLesson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("lid");
		int lid = Integer.parseInt(id);
		id = req.getParameter("sid");
		int sid = Integer.parseInt(id);
		studentService.addLessonStudent(lid, sid);
		req.setAttribute("lid", lid);
		manageStudent(req, resp);
	}
	
	/**
	 * teacher-lesson-student: page
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void manageStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String lid = req.getParameter("lid");
		int id = Integer.parseInt(lid);
		Lesson lesson = lessonService.getLessonByLid(id);
		req.setAttribute("lesson", lesson);
		List<LessonStudent> list = studentService.getLSByLid(id);
		req.setAttribute("students", list);
		req.getRequestDispatcher("/jsp/lesson_user.jsp").forward(req, resp);
	}

	/**
	 * teacher-delete-lesson
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String lid = req.getParameter("lid");
		lessonService.delLessonById(Integer.parseInt(lid));
	}

	/**
	 * teacher-lesson-details-page
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String lid = req.getParameter("lid");
		int id = Integer.parseInt(lid);
		Lesson lesson = lessonService.getLessonByLid(id);
		req.setAttribute("lesson", lesson);
		List<LessonFile> list = fileService.getAllFileByLid(id);
		req.setAttribute("list", list);
		req.getRequestDispatcher("/jsp/lesson_detail.jsp").forward(req, resp);
	}

	/**
	 * teacher-lesson-file-upload
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lessonid = request.getParameter("lid");
		int lid = Integer.parseInt(lessonid);
		Lesson lesson = lessonService.getLessonByLid(lid);
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		long size = 300 * 1024 * 1024;
		upload.setFileSizeMax(size);
		upload.setSizeMax(5 * size);
		upload.setHeaderEncoding("UTF-8");
		LessonFile lessonFile = new LessonFile();
		if (upload.isMultipartContent(request)) {
			try {
				List<FileItem> list = upload.parseRequest(request);
				for (FileItem item : list) {
					if (item.isFormField()) {
						String name = item.getFieldName();
						String value = item.getString();
						if (name.equals("fdesc")) {
							lessonFile.setFdesc(value);
						}
					} else {
						String name = System.currentTimeMillis() + "_" + item.getName().replaceAll(" ", "_");// file
																												// name
						int begin = name.lastIndexOf(".");
						String ext = name.substring(begin + 1, name.length());// ext
																				// name
						String type = getType(ext);
						if (type.equals(LessonFileType.NON.toString())) {
							// file illegal
							String message = StringConfig.FILENOTSUPPORTED;
							request.setAttribute("message", message);
							// forward
						} else {
							String basePath = getServletContext().getRealPath(File.separator+"upload"+File.separator + lesson.getName() + File.separator);
							File dir = new File(basePath);
							if (!dir.exists() && !dir.isDirectory()) {
								dir.mkdirs();
							}
							File file = new File(basePath, name);
							item.write(file);
							item.delete();
							lessonFile.setName(name);
							lessonFile.setFtype(type);
							lessonFile.setLid(lid);
							lessonFile.setPath(basePath + File.separator + name);
							fileService.addFile(lessonFile);
						}
					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		request.getRequestDispatcher("/teacher?method=details&lid=" + lid).forward(request, response);
	}

	/**
	 * calculate file type by file extend name
	 * 
	 * @param ext
	 * @return
	 */
	private String getType(String ext) {
		ext = ext.toUpperCase();
		if (ext.equals(LessonFileType.TXT.toString())) {
			return "TXT";
		} else if (ext.equals(LessonFileType.PDF.toString())) {
			return "PDF";
		} else if (ext.equals(LessonFileType.MP4.toString())) {
			return "MP4";
		} else if (ext.equals(LessonFileType.WAV.toString())) {
			return "WAV";
		} else if (ext.equals(LessonFileType.DOC.toString())) {
			return "DOC";
		} else if (ext.equals(LessonFileType.DOCX.toString())) {
			return "DOCX";
		} else {
			return LessonFileType.NON.toString();
		}
	}

	/**
	 * teacher-lesson-add
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addLesson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String lname = req.getParameter("lname");
		String ldesc = req.getParameter("ldesc");
		String type = req.getParameter("ltype");
		if (!StringUtils.isNullOrEmpty(lname) && !StringUtils.isNullOrEmpty(ldesc)) {
			Lesson l = new Lesson();
			l.setName(lname);
			l.setDate(new Date(System.currentTimeMillis()));
			l.setDesc(ldesc);
			l.setType(Integer.parseInt(type));
			l.setUid(uid);
			lessonService.addLesson(l);
		}
	}

	/**
	 * teacher-lesson-show_lesson_list
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showlessons(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String by = req.getParameter("orderby");
		List<Lesson> list;
		int semesterid = semesterServic.getDefaultSemester().getId();
		if(null == by || "".equals(by.trim())){
			list = lessonService.getLessonsByUid(uid, semesterid);
		}else{
			list = lessonService.getAllLessonsOrderByName(uid, semesterid);
		}
		
		req.setAttribute("list", list);
		req.getRequestDispatcher("/jsp/manage_lesson.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
