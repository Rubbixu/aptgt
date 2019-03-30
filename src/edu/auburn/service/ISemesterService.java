package edu.auburn.service;

import java.util.List;

import edu.auburn.domain.Semester;

public interface ISemesterService {
	boolean addSemester(Semester semester);
	List<Semester> getSemesterList();
	Semester getDefaultSemester();
	boolean setDefaultById(int sid);
	Semester getSemesterById(int sid);
	Semester getSemesterByYearAndSmallId(int year, int id);
}
