package cn.cstm.domain;

public class Customer {
/*对应数据库表
 * cid		CHAR(32) PRIMARY KEY,
	cname		VARCHAR(40) NOT NULL,
	gender		VARCHAR(6) NOT NULL,
	birthday	CHAR(10),
	cellphone	VARCHAR(15) NOT NULL,
	email		VARCHAR(40),		
	description	VARCHAR(500)
 */
	private String cid;
	private String cname;
	private String gender;
	private String birthday;
	private String cellphone;
	private String email;
	private String description;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Customer [cid=" + cid + ", cname=" + cname + ", gender="
				+ gender + ", birthday=" + birthday + ", cellphone="
				+ cellphone + ", email=" + email + ", description="
				+ description + "]";
	}
}
