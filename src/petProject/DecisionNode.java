package petProject;

import java.util.List;
import java.util.UUID;

public class DecisionNode {

	private UUID nodeId;
	private List<String> acceptedValues;
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
	

}
