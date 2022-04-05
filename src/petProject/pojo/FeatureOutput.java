package petProject.pojo;

import java.util.List;

public class FeatureOutput {

	private String featureName; 
	private List<FeatureOption> listOfFeautreOptions;
	private Integer totalCount;
	private Double standardDev;
	private Double average;
	/**
	 * @return the featureName
	 */
	public String getFeatureName() {
		return featureName;
	}
	/**
	 * @param featureName the featureName to set
	 */
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	/**
	 * @return the listOfFeautreOptions
	 */
	public List<FeatureOption> getListOfFeautreOptions() {
		return listOfFeautreOptions;
	}
	/**
	 * @param listOfFeautreOptions the listOfFeautreOptions to set
	 */
	public void setListOfFeautreOptions(List<FeatureOption> listOfFeautreOptions) {
		this.listOfFeautreOptions = listOfFeautreOptions;
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
