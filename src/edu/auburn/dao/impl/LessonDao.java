package edu.auburn.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.StringUtils;

import edu.auburn.dao.ILessonDao;
import edu.auburn.dao.ISemester;
import edu.auburn.dao.IUserDao;
import edu.auburn.domain.EduUser;
import edu.auburn.domain.Lesson;
import edu.auburn.utils.JDBCUtil;

public class LessonDao implements ILessonDao {
	IUserDao udao = new UserDao();
	ISemester semesterDao = new SemesterDao();
	@Override
	public boolean addLesson(Lesson lesson) {
		// lid integer auto_increment primary key,
		// name varchar(50),
		// ldesc varchar(200),
		// udate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
		// uid integer,
		// ltype integer
		String sql = "insert into lesson (name, ldesc, udate, uid, ltype, semesterid) values (?,?,?,?,?,?)";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setString(1, lesson.getName());
			ps.setString(2, lesson.getDesc());
			ps.setDate(3, lesson.getDate());
			ps.setInt(4, lesson.getUid());
			ps.setInt(5, lesson.getType());
			ps.setInt(6, semesterDao.getDefaultSemester().getId());
			int result = ps.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}
	

	@Override
	public boolean delLessonById(int lid) {
		String sql = "delete from lesson where lid = ?";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setInt(1, lid);
			int result = ps.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}

	@Override
	public List<Lesson> getLessonsByUid(int uid, int semesterid) {
		String sql = "select * from lesson where uid = ? and semesterid = ? order by lid";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setInt(1, uid);
			ps.setInt(2, semesterid);
			ResultSet rs = ps.executeQuery();
			Lesson l = null;
			List<Lesson> result = new ArrayList<>();
			
			String uname = udao.getUserById(uid).getName();
			if (StringUtils.isNullOrEmpty(uname))
				uname = "";
			while (rs.next()) {
				l = new Lesson();
				// String sql = "insert into lesson (name, ldesc, udate, uid,
				// ltype) values (?,?,?,?,?)";
				l.setLid(rs.getInt("lid"));
				l.setName(rs.getString("name"));
				l.setDesc(rs.getString("ldesc"));
				l.setDate(rs.getDate("udate"));
				l.setUid(rs.getInt("uid"));
				l.setType(rs.getInt("ltype"));
				l.setUname(uname);
				result.add(l);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}

	@Override
	public Lesson getLessonById(int lid) {
		String sql = "select * from lesson where lid = ?";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setInt(1, lid);
			ResultSet rs = ps.executeQuery();
			Lesson l = null;
			IUserDao udao = new UserDao();
			if (rs.next()) {
				l = new Lesson();
				// String sql = "insert into lesson (name, ldesc, udate, uid,
				// ltype) values (?,?,?,?,?)";
				l.setLid(rs.getInt("lid"));
				l.setName(rs.getString("name"));
				l.setDesc(rs.getString("ldesc"));
				l.setDate(rs.getDate("udate"));
				int uid = rs.getInt("uid");
				l.setUid(uid);
				String uname = udao.getUserById(uid).getName();
				if (StringUtils.isNullOrEmpty(uname))
					uname = "";
				l.setUname(uname);
				l.setType(rs.getInt("ltype"));
				l.setUname(uname);
				
			}
			return l;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}

	@Override
	public List<Lesson> getAllLessons() {
		String sql = "select * from lesson order by lid";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Lesson l = null;
			IUserDao udao = new UserDao();
			List<Lesson> result = new ArrayList<>();
			while (rs.next()) {
				l = new Lesson();
				// String sql = "insert into lesson (name, ldesc, udate, uid,
				// ltype) values (?,?,?,?,?)";
				l.setLid(rs.getInt("lid"));
				l.setName(rs.getString("name"));
				l.setDesc(rs.getString("ldesc"));
				l.setDate(rs.getDate("udate"));
				int uid = rs.getInt("uid");
				l.setUid(uid);
				String uname = udao.getUserById(uid).getName();
				if (StringUtils.isNullOrEmpty(uname))
					uname = "";
				l.setUname(uname);
				l.setType(rs.getInt("ltype"));
				l.setUname(uname);
				result.add(l);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}

	@Override
	public List<Lesson> getAllLessonsOrderedByName(int uid, int semesterid) {
		String sql = "select * from lesson where uid = ? and semesterid = ? order by name";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setInt(1, uid);
			ps.setInt(2, semesterid);
			ResultSet rs = ps.executeQuery();
			Lesson l = null;
			List<Lesson> result = new ArrayList<>();
			IUserDao udao = new UserDao();
			String uname = udao.getUserById(uid).getName();
			if (StringUtils.isNullOrEmpty(uname))
				uname = "";
			while (rs.next()) {
				l = new Lesson();
				// String sql = "insert into lesson (name, ldesc, udate, uid,
				// ltype) values (?,?,?,?,?)";
				l.setLid(rs.getInt("lid"));
				l.setName(rs.getString("name"));
				l.setDesc(rs.getString("ldesc"));
				l.setDate(rs.getDate("udate"));
				l.setUid(rs.getInt("uid"));
				l.setType(rs.getInt("ltype"));
				l.setUname(uname);
				result.add(l);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}

	@Override
	public boolean updateLessonNameAndDescById(String name, String desc, int id) {
		String sql = "update lesson set udate = ?, name = ?, ldesc = ? where lid = ?";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setString(2, name);
			ps.setString(3, desc);
			ps.setInt(4, id);
			int result = ps.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}

	@Override
	public List<Lesson> getLessonsBySemesterId(int semesterid) {
		String sql = "select * from lesson where semesterid = ?";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setInt(1, semesterid);
			ResultSet rs = ps.executeQuery();
			List<Lesson> list = new ArrayList<>();
			Lesson l = null;
			while (rs.next()) {
				l = new Lesson();
				l.setLid(rs.getInt("lid"));
				l.setName(rs.getString("name"));
				l.setDesc(rs.getString("ldesc"));
				l.setDate(rs.getDate("udate"));
				l.setSemesterid(rs.getInt("semesterid"));
				int uid = rs.getInt("uid");
				l.setUid(uid);
				EduUser u = udao.getUserById(uid);
				if(u != null) {
					l.setUname(u.getName());
				}
				l.setType(rs.getInt("ltype"));
				list.add(l);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}

	@Override
	public int getLastLid() {
		//	select max(lid) from lesson;
		String sql = "select max(lid) from lesson";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			int index = -1;
			if (rs.next()){
				index = rs.getInt(1);
			}
			return index;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}

}
