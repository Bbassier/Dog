package petProject.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Color {
	private String primary;
	private String secondary;
	private String tertiary;
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
	 * @return the tertiary
	 */
	public String getTertiary() {
		return tertiary;
	}
	/**
	 * @param tertiary the tertiary to set
	 */
	public void setTertiary(String tertiary) {
		this.tertiary = tertiary;
	}
}
