package petProject;

import java.util.List;
import java.util.UUID;

public class DecisionNode {

	private UUID nodeId;
	private String attributeName;
	private List<String> acceptedValues;
	private List<LeafNode> children;
	/**
	 * @return the children
	 */
	public List<LeafNode> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<LeafNode> children) {
		this.children = children;
	}
	/**
	 * @return the nodeId
	 */
	public UUID getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(UUID nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the acceptedValues
	 */
	public List<String> getAcceptedValues() {
		return acceptedValues;
	}
	/**
	 * @param acceptedValues the acceptedValues to set
	 */
	public void setAcceptedValues(List<String> acceptedValues) {
		this.acceptedValues = acceptedValues;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	

}
