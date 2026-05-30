package r3123004163;

public class LoginBean {
	// 用户名
	private String user;
	// 口令
	private String password;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//判断用户合法,在实际情况下，这里应该查询数据库
	public boolean isValidUser()
	{
		return "3123004163".equalsIgnoreCase(this.user) &&
				"1234".equals(this.password);
	}
}
