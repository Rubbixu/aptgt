package edu.auburn.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import edu.auburn.dao.ISemester;
import edu.auburn.domain.Semester;
import edu.auburn.utils.JDBCUtil;

public class SemesterDao implements ISemester{

	@Override
	public boolean addSemester(Semester semester) {
		String sql = "insert into semester (year, semester) values (?,?)";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setInt(1, semester.getYear());
			ps.setInt(2, semester.getSemester());
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
	public List<Semester> getSemesterList() {
		String sql = "select * from semester";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Semester> result = new ArrayList<>();
			Semester s = null;
			while (rs.next()) {
				s = new Semester();
				s.setId(rs.getInt("id"));
				s.setYear(rs.getInt("year"));
				s.setSemester(rs.getInt("semester"));
				s.setIscurrent(rs.getInt("iscurrent"));
				result.add(s);
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
	public boolean setDefault(int id) {
		String sql = "update semester set iscurrent = 0";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
		String sql2 = "update semester set iscurrent = 1 where id = ?";
		Connection connection2 = JDBCUtil.getConnection();
		PreparedStatement ps2 = null;
		try {
			ps2 = (PreparedStatement) connection2.prepareStatement(sql2);
			ps2.setInt(1, id);
			int result2 = ps2.executeUpdate();
			return result2 > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
		
	}

	@Override
	public Semester getDefaultSemester() {
		String sql = "select * from semester where iscurrent = 1";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Semester s = null;
			if (rs.next()) {
				s = new Semester();
				s.setId(rs.getInt("id"));
				s.setYear(rs.getInt("year"));
				s.setSemester(rs.getInt("semester"));
				s.setIscurrent(rs.getInt("iscurrent"));
			}
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}

	@Override
	public Semester getSemesterById(int sid) {
		String sql = "select * from semester where id = ?";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setInt(1, sid);
			ResultSet rs = ps.executeQuery();
			Semester s = null;
			if (rs.next()) {
				s = new Semester();
				s.setId(rs.getInt("id"));
				s.setYear(rs.getInt("year"));
				s.setSemester(rs.getInt("semester"));
				s.setIscurrent(rs.getInt("iscurrent"));
			}
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}

	@Override
	public Semester getSemesterByYearAndSmallSemesterId(int year, int smallSemesterId) {
		String sql = "select * from semester where year = ? and semester = ?";
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = (PreparedStatement) connection.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, smallSemesterId);
			ResultSet rs = ps.executeQuery();
			Semester s = null;
			if (rs.next()) {
				s = new Semester();
				s.setId(rs.getInt("id"));
				s.setYear(rs.getInt("year"));
				s.setSemester(rs.getInt("semester"));
				s.setIscurrent(rs.getInt("iscurrent"));
			}
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JDBCUtil.close(connection, ps);
		}
	}

}
