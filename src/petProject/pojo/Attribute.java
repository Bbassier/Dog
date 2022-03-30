package petProject.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute {
	private String spayedNeutered;
	private String houseTrained;
	private String specialNeeds;
	private String shotsCurrent;
	/**
	 * @return the spayedNeutered
	 */
	public String getSpayedNeutered() {
		return spayedNeutered;
	}
	/**
	 * @param spayedNeutered the spayedNeutered to set
	 */
	public void setSpayedNeutered(String spayedNeutered) {
		this.spayedNeutered = spayedNeutered;
	}
	/**
	 * @return the houseTrained
	 */
	public String getHouseTrained() {
		return houseTrained;
	}
	/**
	 * @param houseTrained the houseTrained to set
	 */
	public void setHouseTrained(String houseTrained) {
		this.houseTrained = houseTrained;
	}
	/**
	 * @return the specialNeeds
	 */
	public String getSpecialNeeds() {
		return specialNeeds;
	}
	/**
	 * @param specialNeeds the specialNeeds to set
	 */
	public void setSpecialNeeds(String specialNeeds) {
		this.specialNeeds = specialNeeds;
	}
	/**
	 * @return the shotsCurrent
	 */
	public String getShotsCurrent() {
		return shotsCurrent;
	}
	/**
	 * @param shotsCurrent the shotsCurrent to set
	 */
	public void setShotsCurrent(String shotsCurrent) {
		this.shotsCurrent = shotsCurrent;
	}
}
