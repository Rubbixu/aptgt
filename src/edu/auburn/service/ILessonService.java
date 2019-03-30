package edu.auburn.service;

import java.util.List;

import edu.auburn.domain.Lesson;

public interface ILessonService {
	boolean addLesson(Lesson lesson);
	boolean delLessonById(int id);
	List<Lesson> getLessonsByUid(int uid, int semesterid);
	Lesson getLessonByLid(int lid);
	
	List<Lesson> getLessonsBySemesterId(int semesterid);
	
	List<Lesson> getAllLessons();
	List<Lesson> getAllLessonsOrderByName(int uid, int semesterid);
	boolean updateLessonNameAndDescById(String name, String desc, int id);
	boolean copyClass(int cid, String level, String name, String desc, int uid);
}
