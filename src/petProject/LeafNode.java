package petProject;

import java.util.UUID;

public class LeafNode {

	private UUID nodeId;
	private Double prodictedValue;
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
	 * @return the prodictedValue
	 */
	public Double getProdictedValue() {
		return prodictedValue;
	}
	/**
	 * @param prodictedValue the prodictedValue to set
	 */
	public void setProdictedValue(Double prodictedValue) {
		this.prodictedValue = prodictedValue;
	} 
	
}
