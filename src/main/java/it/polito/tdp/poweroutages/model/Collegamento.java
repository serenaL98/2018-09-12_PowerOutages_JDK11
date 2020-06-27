package it.polito.tdp.poweroutages.model;

public class Collegamento implements Comparable<Collegamento>{
	
	private Nerc nerc1;
	private Nerc nerc2;
	private Integer peso;
	
	
	public Collegamento(Nerc nerc1, Nerc nerc2, Integer peso) {
		super();
		this.nerc1 = nerc1;
		this.nerc2 = nerc2;
		this.peso = peso;
	}
	
	
	public Nerc getNerc1() {
		return nerc1;
	}
	public void setNerc1(Nerc nerc1) {
		this.nerc1 = nerc1;
	}
	public Nerc getNerc2() {
		return nerc2;
	}
	public void setNerc2(Nerc nerc2) {
		this.nerc2 = nerc2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}


	@Override
	public int compareTo(Collegamento o) {
		return -(this.peso.compareTo(o.getPeso()));
	}


	@Override
	public String toString() {
		return nerc1 + ", " + nerc2 + ", peso:" + peso;
	}
	
	

}
