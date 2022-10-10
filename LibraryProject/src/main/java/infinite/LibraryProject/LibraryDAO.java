package infinite.LibraryProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAO {
	 public List<TranBook> showBook(String user) throws ClassNotFoundException, SQLException{
		 Connection con=ConnectionHelper.getConnection();
		 String sql="select * from TransReturn where userName=?";
		 PreparedStatement ps=con.prepareStatement(sql);
		 ps.setString(1, user);
		 ResultSet rs=ps.executeQuery();
		 TranBook tranBook = null;
		 List<TranBook> tranBookList = new ArrayList<TranBook>();
		while(rs.next()) {
			tranBook = new TranBook();
			tranBook.setBookId(rs.getInt("BookId"));
			tranBook.setUserName(user);
			tranBook.setFromDate(rs.getDate("FromDate"));
			tranBook.setToDate(rs.getDate("Todate"));
			tranBookList.add(tranBook);
		}
		return tranBookList;	 
	 }
	 public TranBook searchTranBook(String user,int bookId) throws ClassNotFoundException, SQLException{
		 Connection con=ConnectionHelper.getConnection();
		 String sql="select * from TranBook where UserName=? and BookId=?";
		 PreparedStatement ps=con.prepareStatement(sql);
		 ps.setString(1, user);
		 ps.setInt(2, bookId);
		 ResultSet rs=ps.executeQuery();
		 TranBook tranBook=null;
		 if(rs.next()){
			 tranBook=new TranBook();
			 tranBook.setBookId(rs.getInt("BookId"));
			 tranBook.setUserName(user);
			 tranBook.setFromDate(rs.getDate("FromDate"));
		 }return tranBook;
	 }
	public String returnBooks(String user,int bookId) throws ClassNotFoundException, SQLException{
		Connection con=ConnectionHelper.getConnection();
		TranBook tranBook=searchTranBook(user, bookId);
		String sql="insert into TransReturn(userName,BookId,FromDate) values(?,?,?)";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, user);
		ps.setInt(2, bookId);
		ps.setDate(3, tranBook.getFromDate());
		ps.executeUpdate();
		sql="update Books set TotalBooks=TotalBooks+1 where id=?";
		ps=con.prepareStatement(sql);
		ps.setInt(1, bookId);
		ps.executeUpdate();
		sql="delete from TranBook where userName=? and BookId=?";
		ps=con.prepareStatement(sql);
		ps.setString(1, user);
		ps.setInt(2, bookId);
		ps.executeUpdate();
		return "Your Book "+bookId+" Return successfully";
	}
	public List<TranBook> issueBook(String user) throws ClassNotFoundException, SQLException{
		Connection  con=ConnectionHelper.getConnection();
		String sql="select * from TranBook where UserName=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, user);
		ResultSet rs=ps.executeQuery();
		TranBook tranBook = null;
		List<TranBook> tranBookList = new ArrayList<TranBook>();
		while(rs.next()) {
			tranBook = new TranBook();
			tranBook.setBookId(rs.getInt("BookId"));
			tranBook.setUserName(user);
			tranBook.setFromDate(rs.getDate("FromDate"));
			tranBookList.add(tranBook);
		}
		return tranBookList;
				
	}
	public String issueBook(String userName,int BookId) throws ClassNotFoundException, SQLException{
		Connection con=ConnectionHelper.getConnection();
		String sql="select count(*) cnt from TranBook where UserName=? and BookId=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, userName);
		ps.setInt(2, BookId);
		ResultSet rs=ps.executeQuery();
		rs.next();
		int count=rs.getInt("cnt");
		if(count==1){
			return "Book with id " + BookId+"already issued";
		}else{
			
		
		sql="Insert into TranBook(UserName,BookId) values(?,?)";
		 ps=con.prepareStatement(sql);
		ps.setString(1, userName);
		ps.setInt(2, BookId);
		
		ps.executeUpdate();
		sql="Update Books set TotalBooks=TotalBooks-1 where id=?";
		ps=con.prepareStatement(sql);
		ps.setInt(1, BookId);
		ps.executeUpdate();
		return "Book with Id "+BookId +" Issued Successfully";
	}}
	public List<Books> searchBooks(String searchType,String searchValue) throws ClassNotFoundException, SQLException{
		String sql;
		boolean isValid=true;
		if(searchType.equals("id")){
			sql="select * from Books where Id=?";
		}else if(searchType.equals("bookname")){
			sql="select * from Books where Name=?";
		}else if(searchType.equals("dept")){
			
			sql="select * from Books where Dept=?";
		}else if(searchType.equals("authorname")){
			sql="select * from Books where Author=?";
		}else{
			isValid=false;
			sql="select * from Books ";
		}
		
		Connection con=ConnectionHelper.getConnection();
		PreparedStatement ps=con.prepareStatement(sql);
		if(isValid==true){
			ps.setString(1, searchValue);
		}
		ResultSet rs = ps.executeQuery();
		Books books = null;
		List<Books> booksList = new ArrayList<Books>();
		while(rs.next()) {
			books = new Books();
			books.setId(rs.getInt("id"));
			books.setName(rs.getString("name"));
			books.setAuthor(rs.getString("author"));
			books.setEdition(rs.getString("edition"));
			books.setDept(rs.getString("dept"));
			books.setNoOfBooks(rs.getInt("TotalBooks"));
			booksList.add(books);
		}
		return booksList;
	}
	public int authenticate(String user,String password) throws ClassNotFoundException, SQLException{
		Connection con=ConnectionHelper.getConnection();
		String sql="select count(*) cnt from libusers where UserName=? and Password=?";
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setString(1, user);
		ps.setString(2, password);
		ResultSet rs=ps.executeQuery();
		rs.next();
		int count=rs.getInt("cnt");
		return count;
	}
	

}
