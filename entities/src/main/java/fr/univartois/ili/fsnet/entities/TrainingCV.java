package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class TrainingCV implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	private String name;
	private String speciality;

	@OneToMany (mappedBy = "training")
	private List<AssociationDateTrainingCV> myCVs = new ArrayList<AssociationDateTrainingCV>();

	// Pensez aux dates debut et fin :)

	@OneToOne
	private EstablishmentCV myEst;

	public TrainingCV() {

	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the speciality
	 */
	public String getSpeciality() {
		return speciality;
	}

	/**
	 * @param speciality
	 *            the speciality to set
	 */
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	/**
	 * @return the myEst
	 */
	public EstablishmentCV getMyEst() {
		return myEst;
	}

	/**
	 * @param myEst
	 *            the myEst to set
	 */
	public void setMyEst(EstablishmentCV myEst) {
		this.myEst = myEst;
	}


	/**
	 * @return the myCVs
	 */
	public List<AssociationDateTrainingCV> getAssociationDateTrainingCV() {
		return myCVs;
	}

	/**
	 * @param myCVs
	 *            the myCVs to set
	 */
	public void setAssociationDateTrainingCV(
			List<AssociationDateTrainingCV> myCVs) {
		this.myCVs = myCVs;
	}

}
