package chat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatDAO {
	private Connection conn;
	
	public ChatDAO() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/anonymouschat","root","990212");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Chat> getChatList(String nowTime){
		ArrayList<Chat> chatList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from chat where chatTime > ? order by chatTime";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nowTime);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<Chat>();
			while(rs.next()) {
				Chat chat = new Chat();
				chat.setChatName(rs.getString("chatName"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ","&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11,13));
				String timeType = "오전";
				if(chatTime > 12) {
					timeType = "오후";
					chatTime -= 12;
				}else if(chatTime == 12) {
					timeType = "오후";
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {rs.close();};
				if(pstmt != null) {pstmt.close();};
				if(conn != null) {conn.close();};
			} catch (SQLException e) {
				e.printStackTrace();
			};
		}
		return chatList;
	}
	
	public ArrayList<Chat> getChatListByRecent(int number){
		ArrayList<Chat> chatList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from chat where no > (select max(no) - ? from chat) order by chatTime";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			rs = pstmt.executeQuery();
			chatList = new ArrayList<Chat>();
			while(rs.next()) {
				Chat chat = new Chat();
				chat.setNo(rs.getInt("no"));
				chat.setChatName(rs.getString("chatName"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ","&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11,13));
				String timeType = "오전";
				if(chatTime > 12) {
					timeType = "오후";
					chatTime -= 12;
				}else if(chatTime == 12) {
					timeType = "오후";
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {rs.close();};
				if(pstmt != null) {pstmt.close();};
				if(conn != null) {conn.close();};
			} catch (SQLException e) {
				e.printStackTrace();
			};
		}
		return chatList;
	}
	
	public ArrayList<Chat> getChatListByRecent(String no){
		ArrayList<Chat> chatList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from chat where no > ? order by chatTime";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(no));
			rs = pstmt.executeQuery();
			chatList = new ArrayList<Chat>();
			while(rs.next()) {
				Chat chat = new Chat();
				chat.setNo(rs.getInt("no"));
				chat.setChatName(rs.getString("chatName"));
				chat.setChatContent(rs.getString("chatContent").replaceAll(" ","&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11,13));
				String timeType = "오전";
				if(chatTime > 12) {
					timeType = "오후";
					chatTime -= 12;
				}else if(chatTime == 12) {
					timeType = "오후";
				}
				chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
				chatList.add(chat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {rs.close();};
				if(pstmt != null) {pstmt.close();};
				if(conn != null) {conn.close();};
			} catch (SQLException e) {
				e.printStackTrace();
			};
		}
		return chatList;
	}
	
	public int submit(String chatName, String chatContent) {
		PreparedStatement pstmt = null;
		String sql = "insert into chat (chatName, chatContent, chatTime) values (?, ?, now())";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chatName);
			pstmt.setString(2, chatContent);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) {pstmt.close();};
				if(conn != null) {conn.close();};
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
}
