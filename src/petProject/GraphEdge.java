package petProject;

import java.util.UUID;

public class GraphEdge {
	private String value;
	private UUID parentNode;
	private UUID childNode;
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the parentNode
	 */
	public UUID getParentNode() {
		return parentNode;
	}

	/**
	 * @return the childNode
	 */
	public UUID getChildNode() {
		return childNode;
	}

	public GraphEdge(String value, UUID parentNode, UUID childNode) {
		super();
		this.value = value;
		this.parentNode = parentNode;
		this.childNode = childNode;
	} 
	
	


}
