package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	private List<String> nodi;
	private List<Edge> archi;
	private FoodDao dao;
	private List<String> camminoOttimo;
	private double pesoOttimo;
	
	public Model() {
		this.dao = new FoodDao();
	}

	public void creaGrafo(Integer C) {
		this.nodi = new ArrayList<>(dao.listAllPortions(C));
		this.archi = new ArrayList<>(dao.listAllFoods(C));
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(String v : nodi) grafo.addVertex(v);
		for(Edge e : archi) {
			Graphs.addEdge(grafo, e.getV1(), e.getV2(), e.getWeight());
		}
	}

	public SimpleWeightedGraph<String, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public List<String> cercaOttimo(String v1, int N) {
		List<String> parziale = new ArrayList<>();
		parziale.add(v1);
		camminoOttimo = new ArrayList<>();
		pesoOttimo = 0;
		cercaCamminoMassimoPeso1Vertice(parziale, 0, N);
		return camminoOttimo;
	}
	
	private void cercaCamminoMassimoPeso1Vertice(List<String> parziale, double peso, int N) {
		if(peso > pesoOttimo && parziale.size()-1 == N) {
			camminoOttimo = new ArrayList<>(parziale);
			pesoOttimo = peso;
		}
		else if(parziale.size()-1 >= N) {
			return;
		}
		for(String v : Graphs.neighborSetOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(v)) {
				double pesoUltimoAggiunto = grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), v));
				peso = peso + pesoUltimoAggiunto;
				parziale.add(v);
				cercaCamminoMassimoPeso1Vertice(parziale, peso, N);
				parziale.remove(parziale.size()-1);
				peso = peso - pesoUltimoAggiunto;
			}
		}
	}

	public List<String> getCamminoOttimo() {
		return camminoOttimo;
	}

	public double getPesoOttimo() {
		return pesoOttimo;
	}
	
}
