package petProject.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Breed {

	private String primary;
	private String secondary;
	private Boolean mixed;
	private Boolean unknown;
	/**
	 * @return the primary
	 */
	public String getPrimary() {
		return primary;
	}
	/**
	 * @param primary the primary to set
	 */
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	/**
	 * @return the secondary
	 */
	public String getSecondary() {
		return secondary;
	}
	/**
	 * @param secondary the secondary to set
	 */
	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}
	/**
	 * @return the mixed
	 */
	public Boolean getMixed() {
		return mixed;
	}
	/**
	 * @param mixed the mixed to set
	 */
	public void setMixed(Boolean mixed) {
		this.mixed = mixed;
	}
	/**
	 * @return the unknown
	 */
	public Boolean getUnknown() {
		return unknown;
	}
	/**
	 * @param unknown the unknown to set
	 */
	public void setUnknown(Boolean unknown) {
		this.unknown = unknown;
	}
}
