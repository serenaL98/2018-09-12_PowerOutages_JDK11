package it.polito.tdp.poweroutages.model;

import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulatore {

	//INPUT
	private int k;
	private Nerc scelto;
	private Graph<Nerc, DefaultWeightedEdge> grafo;
	
	//OUTPUT
	private int catastrofi;
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Evento> coda;

	
	//MODELLO DEL MONDO
	
	//STAMPA OUTPUT
	public int getCatastrofi() {
		return catastrofi;
	}
	
	//SIMULZIONE
	public void inizio(Graph<Nerc, DefaultWeightedEdge> grafo, int k, Nerc scelto) {
		this.coda = new PriorityQueue<>();
		this.grafo = grafo;
		this.k = k;
		this.scelto = scelto;
		this.catastrofi = 0;
		
		//inizializzo coda
		this.coda.add(new Evento());
		
	}
}
