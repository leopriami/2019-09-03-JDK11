package it.polito.tdp.food.model;

public class Edge {
	
	private String v1;
	private String v2;
	private int weight;
	
	public Edge(String v1, String v2, int weight) {
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
	}

	public String getV1() {
		return v1;
	}

	public void setV1(String v1) {
		this.v1 = v1;
	}

	public String getV2() {
		return v2;
	}

	public void setV2(String v2) {
		this.v2 = v2;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
