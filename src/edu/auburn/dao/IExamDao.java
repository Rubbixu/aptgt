package edu.auburn.dao;

import java.util.List;

import edu.auburn.domain.Exam;

public interface IExamDao {
	boolean addExam(Exam exam);
	boolean delExamById(int id);
	List<Exam> getExamsByLid(int Lid);
	Exam getExamById(int eid);
	int getLastEid();
	boolean updateDueDateAndTypeByEid(long date, int type, int eid, int keyboard, String name, String desc);
}
