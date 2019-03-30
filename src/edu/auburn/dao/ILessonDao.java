package edu.auburn.dao;

import java.util.List;

import edu.auburn.domain.Lesson;

public interface ILessonDao {
	boolean addLesson(Lesson lesson);
	boolean delLessonById(int id);
	List<Lesson> getLessonsByUid(int uid, int semesterid);
	Lesson getLessonById(int lid);
	List<Lesson> getAllLessons();
	List<Lesson> getLessonsBySemesterId(int semesterid);
	List<Lesson> getAllLessonsOrderedByName(int uid, int semesterid);
	boolean updateLessonNameAndDescById(String name, String desc, int id);
	int getLastLid();
}
