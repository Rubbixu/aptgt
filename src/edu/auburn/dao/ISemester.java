package edu.auburn.dao;

import java.util.List;

import edu.auburn.domain.Semester;

public interface ISemester {
	Semester getSemesterById(int sid);
	boolean addSemester(Semester semester);
	List<Semester> getSemesterList();
	boolean setDefault(int id);
	Semester getDefaultSemester();
	Semester getSemesterByYearAndSmallSemesterId(int year, int smallSemesterId);
}
