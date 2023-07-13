package bean;

public class G_UserMaster {
	private String userId;
	private String name;
	private String password;
	private String passwordForCheck;
	private String dept;
	private String etc;
	private String hireDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordForCheck() {
		return passwordForCheck;
	}

	public void setPasswordForCheck(String passwordForCheck) {
		this.passwordForCheck = passwordForCheck;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
}