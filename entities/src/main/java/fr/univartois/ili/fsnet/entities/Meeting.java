package fr.univartois.ili.fsnet.entities;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Temporal;

/**
 * 
 * The class Meeting.
 * 
 */
@Entity
public class Meeting extends Announcement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Embedded
	private Address address;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date startDate;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date recallDate;
	
	/**
	 * Constructor of the class Meeting.
	 */
	public Meeting() {
	}

	public Meeting(SocialEntity creator, String title, String content,
			Date endDate, boolean isPrivate, Date startDate, Address address,Date recallDate) {
		super(creator, title, content, endDate, isPrivate);
		if (startDate == null || address == null) {
			throw new IllegalArgumentException();
		}
		this.startDate = startDate;
		this.address = address;
		this.recallDate = recallDate;
	}
	
	/**
	 * Get the value of recallTime
	 * 
	 * @return the value of recallTime
	 */
	public Date getRecallDate() {
		return recallDate;
	}

	/**
	 * Set the value of recallTime
	 * 
	 * @param recallTime
	 *            new value of recallTime
	 */
	public void setRecallDate(Date recallDate) {
		this.recallDate = recallDate;
	}

	/**
	 * Get the value of address
	 * 
	 * @return the value of address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Set the value of address
	 * 
	 * @param address
	 *            new value of address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Get the value of startDate
	 * 
	 * @return the value of startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Set the value of startDate
	 * 
	 * @param startDate
	 *            new value of startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
