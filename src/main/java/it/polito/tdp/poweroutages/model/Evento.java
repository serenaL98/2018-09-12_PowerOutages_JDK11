package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Evento {

	//private Nerc nerc;
	private EventType tipo;
	//private Poweroutage power;
	private LocalDateTime data;
	
	public enum EventType{
		INIZIO, FINE
	}
	
	
}
