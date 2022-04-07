package petProject;

import java.util.UUID;

public class LeafNode {

	private UUID nodeId;
	private Double predictedValue;
	private String attributeValue;
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
	 * @return the predictedValue
	 */
	public Double getPredictedValue() {
		return predictedValue;
	}
	/**
	 * @param predictedValue the predictedValue to set
	 */
	public void setPredictedValue(Double predictedValue) {
		this.predictedValue = predictedValue;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	} 
	
}
