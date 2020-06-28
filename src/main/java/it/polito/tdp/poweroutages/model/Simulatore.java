package it.polito.tdp.poweroutages.model;

import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.poweroutages.model.Evento.EventType;

public class Simulatore {

	//INPUT
	private int k;
	private Graph<Nerc, DefaultWeightedEdge> grafo;
	private Map<Integer, Nerc> mappa;
	
	//OUTPUT
	private int catastrofi;
	private Map<Integer, Integer> bonus;	//id nerc, #bonus
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Evento> coda;

	
	//MODELLO DEL MONDO
	
	//STAMPA OUTPUT
	public int getCatastrofi() {
		return catastrofi;
	}
	
	public Map<Integer, Integer> getBonus() {
		return bonus;
	}

	//SIMULZIONE
	public void inizio(Graph<Nerc, DefaultWeightedEdge> grafo, int k) {
		this.coda = new PriorityQueue<>();
		this.grafo = grafo;
		this.k = k;
		this.catastrofi = 0;
		
		//inizializzo coda
		/*for(Poweroutage p: this.tuttipower) {
			this.coda.add(new Evento(EventType.INIZIO, p, p.getDataIn));
			this.coda.add(new Evento(EventType.FINE, p, p.getDataFine));
		}*/
		
	}
	
	public void avvia() {
		while(!coda.isEmpty()) {
			Evento e = coda.poll();
			processEvent(e);
		}
	}
	
	public void processEvent(Evento e) {
		/*
		switch(e.getTipo()) {
		
		case INIZIO:
			Nerc attuale = mappa.get(e.getPower().getId());
			
			//caso 1
			for(Nerc ne: attuale.getHoDonatoA()) {
				if(e.getData)
			}
			break;
			
		case FINE:
			break;
			
		}*/
	}
}
