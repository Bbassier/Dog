package petProject.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Environment {
	private Boolean children;
	private Boolean dogs;
	private Boolean cats;
	/**
	 * @return the children
	 */
	public Boolean getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(Boolean children) {
		this.children = children;
	}
	/**
	 * @return the dogs
	 */
	public Boolean getDogs() {
		return dogs;
	}
	/**
	 * @param dogs the dogs to set
	 */
	public void setDogs(Boolean dogs) {
		this.dogs = dogs;
	}
	/**
	 * @return the cats
	 */
	public Boolean getCats() {
		return cats;
	}
	/**
	 * @param cats the cats to set
	 */
	public void setCats(Boolean cats) {
		this.cats = cats;
	}


}
