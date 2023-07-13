package bean;

public class G_userRegister implements java.io.Serializable {
	// フィールド「inputState」は、
	//		→1は新規作成(ID登録なし)	：userRegister.jspのボタン「登録/更新」がアクティブ
	//		→2は更新(ID既に登録済み)	：userRegister.jspのボタン「登録/更新」がアクティブ
	//		→3は更新もしくは削除				：userRegister.jspのボタン「登録/更新」「削除」がアクティブ
	private String userID;
	private String userName;
	private String password;
	private String passwordForCheck;
	private String etc;
	private String inputState;
	private String toAction;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	public String getInputState() {
		return inputState;
	}

	public void setInputState(String inputState) {
		this.inputState = inputState;
	}

	public String getToAction() {
		return toAction;
	}

	public void setToAction(String toAction) {
		this.toAction = toAction;
	}
}
