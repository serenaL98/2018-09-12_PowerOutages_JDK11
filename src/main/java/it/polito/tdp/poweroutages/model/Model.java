package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	
	private PowerOutagesDAO dao;
	private List<String> nerc;
	
	//grafo semplice, pesato, non orientato
	private Graph<Nerc, DefaultWeightedEdge>grafo;
	private List<Nerc> allNerc;
	private Map<Integer, Nerc> mappa;
	private List<Collegamento> collegamenti;
	private List<Collegamento> relazioni;
	
	public Model() {
		this.dao = new PowerOutagesDAO();
		this.nerc = this.dao.prendiNerc();
		this.allNerc = this.dao.loadAllNercs();
		
		this.mappa = new HashMap<>();
		for(Nerc c : this.allNerc) {
			mappa.put(c.getId(), c);
		}
	}
	
	public List<String> elencoNerc(){
		return this.nerc;
	}
	
	public void creaGrafo() {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//VERTICI
		Graphs.addAllVertices(this.grafo, this.allNerc);
		
		//ARCHI
		this.collegamenti = new ArrayList<>(this.dao.prendiCollegamenti(this.mappa));
		this.relazioni = new ArrayList<>(this.dao.prendiRelazioni(mappa));
		
		for(Collegamento c: this.collegamenti) {
			Graphs.addEdge(this.grafo, c.getNerc1(), c.getNerc2(), c.getPeso());
		}
		for(Collegamento c: this.relazioni) {
			if(!this.grafo.containsEdge(c.getNerc1(), c.getNerc2())) {
				Graphs.addEdge(this.grafo, c.getNerc1(), c.getNerc2(), c.getPeso());
			}
		}
		
	}
	
	public int numeroVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int numeroArch() {
		return this.grafo.edgeSet().size();
	}

	public String stampaVicini(String nerc) {
		
		List<Nerc> vicini = new ArrayList<>();
		
		for(Nerc c: Graphs.neighborListOf(this.grafo, this.cercaNerc(nerc))) {
			vicini.add(c);
		}
		
		List<Collegamento> col = new ArrayList<>();
		for(Collegamento c: this.collegamenti) {
			for(Nerc n: vicini) {
				if( (c.getNerc1().getValue().equals(nerc) && c.getNerc2().equals(n)) ||  (c.getNerc2().getValue().equals(nerc) && c.getNerc1().equals(n)) ) {
					col.add(c);
				}
			}
		}
		Collections.sort(col);
		
		String stampa = "";
		for(Collegamento c: col) {
			if(c.getNerc1().getValue().equals(nerc)) {
				stampa += c.getNerc2()+", peso: "+c.getPeso()+" \n";
			}else if(c.getNerc2().getValue().equals(nerc))
				stampa += c.getNerc1()+", peso: "+c.getPeso()+" \n";
			
		}
		return stampa;
	}
	
	public Nerc cercaNerc(String nerc) {
		
		for(Nerc n : this.grafo.vertexSet()) {
			if(n.getValue().equals(nerc))
				return n;
		}
		return null;
	}
}
