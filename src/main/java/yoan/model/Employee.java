package yoan.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="employee_tbl")
public class Employee {	 
	@Id
	private int employeeId;
		
	@Size(min = 3, max = 14,message="Min 3 characters required")
	private String firstName,lastName,designation;
	private String password;	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
}