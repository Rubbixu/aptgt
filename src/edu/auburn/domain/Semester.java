package edu.auburn.domain;

public class Semester {
	private int id;
	private int semester;
	private int year;
	private int iscurrent;
	private String semesterName;
	
	public String getSemesterName() {
		switch (semester) {
		case 1:
			this.semesterName = "Spring";
			break;
		case 2:
			this.semesterName = "Summer";
			break;
		case 3:
			this.semesterName = "Fall";
			break;

		default:
			break;
		}
		return this.semesterName;
	}
	public int getIscurrent() {
		return iscurrent;
	}
	public void setIscurrent(int iscurrent) {
		this.iscurrent = iscurrent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@Override
	public String toString() {
		return "Semester [id=" + id + ", semester=" + semester + ", year=" + year + ", iscurrent=" + iscurrent
				+ ", semesterName=" + semesterName + "]";
	}
	
}
