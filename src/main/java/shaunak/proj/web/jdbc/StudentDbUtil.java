package shaunak.proj.web.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource)
	{
		dataSource=theDataSource;
	}
	
	public List<Student> getStudents() throws Exception
	{
		List<Student> students=new ArrayList<>();
		
		Connection myCon=null;
		Statement myStmt=null;
		ResultSet myRs=null;
		
		try {
		//Get A Connection
		myCon =dataSource.getConnection();
		
		//SQl
		String sql="select * from student order by last_name";
		
		myStmt=myCon.createStatement();
		
		//Execute qry
		myRs=myStmt.executeQuery(sql);
		
		//Resultset Process
		while(myRs.next())
		{
		  int id=myRs.getInt("id");
		  
		  String firstname=myRs.getString("first_name");
		  
		  String lastname=myRs.getString("last_name");
		  
		  String email=myRs.getString("email");
		  
		  
		  Student tmpStudent = new Student(id, firstname, lastname, email);

		  students.add(tmpStudent);
		}
		return students;	
		}
		finally
		{
			close(myCon,myStmt,myRs);
		}
		
	}

	private void close(Connection myCon, Statement myStmt, ResultSet myRs) {
		try
		{
		if(myRs!=null) {
			myRs.close();
		}
		if(myStmt!=null) {
			myStmt.close();
		}
		if(myCon!=null) {
			myCon.close();
		}
		
		}
		catch(Exception e)
		{
		 e.printStackTrace();	
		}
		}

	public void addStudent(Student theStudent) throws SQLException {
		
	     Connection myCon=null;
	     PreparedStatement myStmt=null;
	     
	     try
	     {
	    	myCon=dataSource.getConnection(); 
	    	
	    	String sql="insert into student"
	    			+ "(first_name, last_name, email) "
	    			+"values (?, ?, ?)";
	    	
	    	myStmt=myCon.prepareStatement(sql);
	    	
	    	myStmt.setString(1, theStudent.getFirstName());
	    	myStmt.setString(2, theStudent.getLastName());
	    	myStmt.setString(3, theStudent.getEmail());
	    	
	    	myStmt.execute();
	    	
	     }
	     finally
	     {
	    	 close(myCon,myStmt,null);
	     }
		
	}

	public Student getStudent(String theStudentId) throws Exception {
		Student theStudent=null;
		
		Connection myCon= null;
		PreparedStatement myStmt= null;
		ResultSet myRs= null;
		int studentId;
		
		try
		{
			studentId=Integer.parseInt(theStudentId);
			
			myCon=dataSource.getConnection();
			
			String sql="select * from student where id=?";
			
			myStmt= myCon.prepareStatement(sql);
			
			myStmt.setInt(1, studentId);
			
			myRs=myStmt.executeQuery();
			
			if(myRs.next())
			{
				String firstName=myRs.getString("first_name");
				String lastName=myRs.getString("last_name");
				String email=myRs.getString("email");
				
				theStudent=new Student(studentId,firstName,lastName,email);
			}
			else
			{
				throw new Exception("Could Not Find The Id: "+ studentId);
			}
			
		return theStudent;	
		}
		finally
		{
			
		}
		
		
	}

	public void updateStudent(Student theStudent) throws Exception{
		
		Connection myCon=null;
		PreparedStatement myStmt=null;
		
		try
		{
			
		myCon=dataSource.getConnection();
		
		String sql="update student "
				+"set first_name=?, last_name=?, email=? "
				+"where id=?";
		
		myStmt=myCon.prepareStatement(sql);
		
		myStmt.setString(1, theStudent.getFirstName());
		myStmt.setString(2, theStudent.getLastName());
		myStmt.setString(3, theStudent.getEmail());
		myStmt.setInt(4, theStudent.getId());     
		
		myStmt.execute();
		}
		finally
		{
			close(myCon,myStmt,null);
		}
	}

	public void deletStudent(String theStudentId) throws Exception{
		
		Connection myCon= null;
		PreparedStatement myStmt=null;
		
		try
		{
			int studentId = Integer.parseInt(theStudentId);
			
			myCon= dataSource.getConnection();
			
			String sql = "delete from student where id=?";
			
			myStmt=myCon.prepareStatement(sql);
			
			myStmt.setInt(1, studentId);
			
			myStmt.execute();
		}
		finally
		{
			close(myCon,myStmt,null);
		}
		
		
	}
	
	
	}

