package edu.auburn.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.util.List;

import edu.auburn.dao.ILessonDao;
import edu.auburn.dao.impl.LessonDao;
import edu.auburn.domain.Exam;
import edu.auburn.domain.ExamWord;
import edu.auburn.domain.Lesson;
import edu.auburn.domain.LessonFile;
import edu.auburn.domain.WordVideo;
import edu.auburn.service.IExamService;
import edu.auburn.service.ILessonService;
import edu.auburn.service.ISemesterService;
import edu.auburn.service.IWordVideoService;

public class LessonService implements ILessonService{
	private ILessonDao dao = new LessonDao();
	@Override
	public boolean addLesson(Lesson lesson) {
		// TODO Auto-generated method stub
		return dao.addLesson(lesson);
	}

	@Override
	public boolean delLessonById(int id) {
		// TODO Auto-generated method stub
		return dao.delLessonById(id);
	}

	@Override
	public List<Lesson> getLessonsByUid(int uid, int semesterid) {
		// TODO Auto-generated method stub
		return dao.getLessonsByUid(uid, semesterid);
	}

	@Override
	public Lesson getLessonByLid(int lid) {
		// TODO Auto-generated method stub
		return dao.getLessonById(lid);
	}

	@Override
	public List<Lesson> getAllLessons() {
		// TODO Auto-generated method stub
		return dao.getAllLessons();
	}

	@Override
	public List<Lesson> getAllLessonsOrderByName(int uid, int semesterid) {
		// TODO Auto-generated method stub
		return dao.getAllLessonsOrderedByName(uid, semesterid);
	}

	@Override
	public boolean updateLessonNameAndDescById(String name, String desc, int id) {
		// TODO Auto-generated method stub
		return dao.updateLessonNameAndDescById(name, desc, id);
	}

	@Override
	public List<Lesson> getLessonsBySemesterId(int semesterid) {
		// TODO Auto-generated method stub
		return dao.getLessonsBySemesterId(semesterid);
	}

	
	ISemesterService semesterDao = new SemesterService();
	LessonFileService lessonFileService = new LessonFileService();
	IExamService examService = new ExamService();
	ExamWordService wordService = new ExamWordService();
	IWordVideoService videoSerivce = new WordVideoService();
	@Override
	public boolean copyClass(int cid, String level, String name, String desc, int uid) {
		boolean booAddLesson, boo2, boo3;
		// 1. add lesson to db. 
		Lesson lesson = new Lesson();
		lesson.setDesc(desc);
		lesson.setName(name);
		lesson.setDate(new Date(System.currentTimeMillis()));
		lesson.setSemesterid(semesterDao.getDefaultSemester().getId());
		lesson.setType(Integer.parseInt(level));
		lesson.setUid(uid);
		booAddLesson  = dao.addLesson(lesson);
		int lid = dao.getLastLid();
		// 2. copy files 
		List<LessonFile> list = lessonFileService.getAllFileByLid(cid);
		for (LessonFile file : list){
			LessonFile newFile = new LessonFile();
			newFile.setName(file.getName());
			newFile.setFdesc(file.getFdesc());
			newFile.setFtype(file.getFtype());
			newFile.setLid(lid);
			String oldPath = file.getPath();
			int start = oldPath.indexOf("upload");
			String off = oldPath.substring(0, start + 7);
			String basePath = off + File.separator + name + File.separator;
			File dir = new File(basePath);
			if (!dir.exists() && !dir.isDirectory()) {
				dir.mkdirs();
			}
			File copyNewFile = new File(basePath, file.getName());
			try {
				Files.copy(new File(oldPath).toPath(), copyNewFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			newFile.setPath(basePath + File.separator + file.getName());
			lessonFileService.addFile(newFile);
		}
		
		// 3. find all exams from cid
		List<Exam> exams = examService.getExamsByLid(cid);
		// 4. copy these exams to new lessons.
		for (Exam e: exams) {
			Exam newExam = new Exam();
			newExam.setName(e.getName());
			newExam.setIfPractice(e.getIfPractice());
			newExam.setEdesc(e.getEdesc());
			newExam.setEtype(e.getEtype());
			newExam.setLid(lid);
			newExam.setUid(uid);
			newExam.setEdue(e.getEdue());
			int oldEid = e.getEid();
			examService.addExam(newExam);
			int eid = examService.getLastEid();
			List<ExamWord> words = wordService.getAllWordsByEid(oldEid);
			for (ExamWord w: words) {
				ExamWord newWord = new ExamWord();
				newWord.setDesc(w.getDesc());
				newWord.setEid(eid);
				newWord.setName(w.getName());
				newWord.setType(w.getType());
				newWord.setPron(w.getPron());
				//////
				String oldPath = w.getPath();
				int start = oldPath.indexOf("upload");
				String off = oldPath.substring(0, start + 7);
				String basePath = off + File.separator 
						+ name + File.separator 
						+ newExam.getName() + File.separator;
				File dir = new File(basePath);
				if (!dir.exists() && !dir.isDirectory()) {
					dir.mkdirs();
				}
				File copyNewFile = new File(basePath, newWord.getName());
				try {
					Files.copy(new File(oldPath).toPath(), copyNewFile.toPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				//////
				newWord.setPath(basePath + File.separator + newWord.getName());
				wordService.addWord(newWord);
				int newWid = wordService.getLastWid();
				int oldWid = w.getFid();
				List<WordVideo> videos = videoSerivce.getAllVideosByWid(oldWid);
				for (WordVideo wv: videos) {
					WordVideo newwv = new WordVideo();
					newwv.setDesc(wv.getDesc());
					newwv.setEid(eid);
					newwv.setName(wv.getName());
					newwv.setWid(newWid);
					//////
					String oldVideoPath = wv.getPath();
					int s = oldVideoPath.indexOf("upload");
					String offSet = oldVideoPath.substring(0, s + 7);
					String base = offSet + File.separator 
							+ name + File.separator 
							+ newExam.getName() + File.separator
							+ newWid + File.separator;
					File dirF = new File(base);
					if (!dirF.exists() && !dirF.isDirectory()) {
						dirF.mkdirs();
					}
					File copyNewVideo = new File(base, newwv.getName());
					try {
						Files.copy(new File(wv.getPath()).toPath(), copyNewVideo.toPath());
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					//////
					newwv.setPath(base + File.separator + newwv.getName());
					videoSerivce.addVideo(newwv);
				}
			}
		}
		return true;
	}
}
