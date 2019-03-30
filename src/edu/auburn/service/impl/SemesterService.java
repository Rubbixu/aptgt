package edu.auburn.service.impl;

import java.util.List;

import edu.auburn.dao.ISemester;
import edu.auburn.dao.impl.SemesterDao;
import edu.auburn.domain.Semester;
import edu.auburn.service.ISemesterService;

public class SemesterService implements ISemesterService{
	private ISemester semesterDao = new SemesterDao();
	@Override
	public boolean addSemester(Semester semester) {
		// TODO Auto-generated method stub
		return semesterDao.addSemester(semester);
	}

	@Override
	public List<Semester> getSemesterList() {
		// TODO Auto-generated method stub
		return semesterDao.getSemesterList();
	}

	@Override
	public Semester getDefaultSemester() {
		// TODO Auto-generated method stub
		return semesterDao.getDefaultSemester();
	}

	@Override
	public boolean setDefaultById(int sid) {
		// TODO Auto-generated method stub
		return semesterDao.setDefault(sid);
	}

	@Override
	public Semester getSemesterById(int sid) {
		// TODO Auto-generated method stub
		return semesterDao.getSemesterById(sid);
	}

	@Override
	public Semester getSemesterByYearAndSmallId(int year, int id) {
		// TODO Auto-generated method stub
		return semesterDao.getSemesterByYearAndSmallSemesterId(year, id);
	}

}
