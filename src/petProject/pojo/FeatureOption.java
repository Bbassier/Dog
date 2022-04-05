package petProject.pojo;

public class FeatureOption {
	
	private String name;
	private Integer totalCount;
	private Double standardDev;
	private Double average;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return the standardDev
	 */
	public Double getStandardDev() {
		return standardDev;
	}
	/**
	 * @param standardDev the standardDev to set
	 */
	public void setStandardDev(Double standardDev) {
		this.standardDev = standardDev;
	}
	/**
	 * @return the average
	 */
	public Double getAverage() {
		return average;
	}
	/**
	 * @param average the average to set
	 */
	public void setAverage(Double average) {
		this.average = average;
	}
	
}
