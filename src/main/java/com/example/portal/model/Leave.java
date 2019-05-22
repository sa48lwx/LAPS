package com.example.portal.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Leave_App")
public class Leave {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String Id;
	private long EmployeeId;
	@Column(name = "overseas_contact_details")
	private int overseasContactDetails;
	private String managerComment;
	private double granularity;
	private int leaveEntitlement;
	private String status;
	@NotEmpty(message = "*Please provide your reason")
	private	String reason;
	@NotNull
	private Date fromDate;
	@NotNull
	private Date toDate;
	private String leave_type;
	private String Description;
	
	public Leave(String id, int overseasContactDetails, String managerComment, double granularity, int leaveEntitlement,
			String status, Date fromDate, Date toDate, String reason, String leave_type, String description) {
		super();
		Id = id;
		this.overseasContactDetails = overseasContactDetails;
		this.managerComment = managerComment;
		this.granularity = granularity;
		this.leaveEntitlement = leaveEntitlement;
		this.status = status;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.reason = reason;
		this.leave_type = leave_type;
		Description = description;
	}
	

	public int getOverseasContactDetails() {
		return overseasContactDetails;
	}

	public void setOverseasContactDetails(int overseasContactDetails) {
		this.overseasContactDetails = overseasContactDetails;
	}

	public String getManagerComment() {
		return managerComment;
	}

	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}

	public double getGranularity() {
		return granularity;
	}

	public void setGranularity(double granularity) {
		this.granularity = granularity;
	}

	public int getLeaveEntitlement() {
		return leaveEntitlement;
	}

	public void setLeaveEntitlement(int leaveEntitlement) {
		this.leaveEntitlement = leaveEntitlement;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}


public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

public String getLeave_type() {
	return leave_type;
}
public void setLeave_type(String leave_type) {
	this.leave_type = leave_type;
}

public Leave() {
	super();
	// TODO Auto-generated constructor stub
}


public Leave(String id, String leave_type, String description) {
	super();
	Id = id;
	this.leave_type = leave_type;
	Description = description;
}
public Leave(String leaveType, String description) {
	super();
	this.leave_type = leaveType;
	Description = description;
}
@Override
public String toString() {
	return "Leave [leave_type=" + leave_type + ", Description=" + Description + "]";
}
public String getLeaveType() {
	return leave_type;
}
public void setLeaveType(String leaveType) {
	this.leave_type = leaveType;
}
public String getDescription() {
	return Description;
}
public void setDescription(String description) {
	Description = description;
}


public long getEmployeeId() {
	return EmployeeId;
}


public void setEmployeeId(long employeeId) {
	EmployeeId = employeeId;
}
}
