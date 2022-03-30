package petProject.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Animal {
	private Integer id;
	private String species;
	private String status;
	private String name;
	private String age;
	private String gender;
	private String size;
	private String coat;
	private Breed breeds;
	private Color colors;
	private Attribute attributes;
	private Environment environment;
	private String publishedAt;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the species
	 */
	public String getSpecies() {
		return species;
	}

	/**
	 * @param species the species to set
	 */
	public void setSpecies(String species) {
		this.species = species;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

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
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the coat
	 */
	public String getCoat() {
		return coat;
	}

	/**
	 * @param coat the coat to set
	 */
	public void setCoat(String coat) {
		this.coat = coat;
	}

	/**
	 * @return the breeds
	 */
	public Breed getBreeds() {
		return breeds;
	}

	/**
	 * @param breeds the breeds to set
	 */
	public void setBreeds(Breed breeds) {
		this.breeds = breeds;
	}

	/**
	 * @return the colors
	 */
	public Color getColors() {
		return colors;
	}

	/**
	 * @param colors the colors to set
	 */
	public void setColors(Color colors) {
		this.colors = colors;
	}

	/**
	 * @return the attributes
	 */
	public Attribute getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Attribute attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	/**
	 * @return the publishedAt
	 */
	public String getPublishedAt() {
		return publishedAt;
	}

	/**
	 * @param publishedAt the publishedAt to set
	 */
	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	@Override
	public String toString() {
		String returnString = "id=" + id;
		returnString = returnString + ",species=" + species + ",status=" + status + ",name=" + name + ",age=" + age
				+ ",gender=" + gender + ",size=" + size + ",coat=" + coat +  ",publishedAt=" + publishedAt;
		if (breeds != null) {
			returnString = returnString + ",breedPrimary=" + breeds.getPrimary() + ",breedSecondary="
					+ breeds.getSecondary() + ",breedMixed=" + breeds.getMixed() + ",breedUnknown="
					+ breeds.getUnknown();
		} else {
			returnString = returnString + ",breedPrimary=null,breedSecondary=null,breedMixed=null,breedUnknown=null";
		}
		if (colors != null) {
			returnString = returnString + ",colors=" + colors.getPrimary();
		} else {
			returnString = returnString + ",colors=null";
		}
		if (attributes != null) {
			returnString = returnString + ",houseTrained=" + attributes.getHouseTrained() + ",shotCurrent=" + attributes.getShotsCurrent()
			+ ",spayedNeutered=" + attributes.getSpayedNeutered() + ",specialNeeds=" + attributes.getSpecialNeeds();
		} else {
			returnString = returnString + ",houseTrained=null,shotCurrent=null,spayedNeutered=null,specialNeeds=null";
		}
		return returnString;
	}

}
