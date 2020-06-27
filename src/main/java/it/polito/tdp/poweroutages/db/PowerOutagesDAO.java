package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Collegamento;
import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutagesDAO {
	
	public List<Nerc> loadAllNercs() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<String> prendiNerc(){
		String sql = "SELECT DISTINCT n.value val" + 
				" from nerc n" + 
				" ORDER BY n.value";
		List<String> lista = new ArrayList<>();
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				String sigla = res.getString("val");
				
				lista.add(sigla);
			}
			
			con.close();
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return lista;
	}
	
	public List<Collegamento> prendiCollegamenti(Map<Integer, Nerc> mappa){
		String sql = "SELECT n1.nerc_one nerc1, n1.nerc_two nerc2, p1.date_event_began datain, p2.date_event_began datafi, COUNT(DISTINCT(MONTH(p1.date_event_began))) peso" + 
				" FROM nercrelations n1, poweroutages p1, poweroutages p2" + 
				" WHERE n1.nerc_one = p1.nerc_id AND n1.nerc_two = p2.nerc_id" + 
				"			AND year(p1.date_event_began) = YEAR(p2.date_event_began)" + 
				"			AND MONTH(p1.date_event_began) = MONTH(p2.date_event_began)" + 
				" GROUP BY n1.nerc_one, n1.nerc_two" + 
				" ORDER BY p1.date_event_began";
		List<Collegamento> lista = new ArrayList<>();
		
		try {
			Connection con = DBConnect.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				if(mappa.containsKey(res.getInt("nerc1")) && mappa.containsKey(res.getInt("nerc2"))) {
					Nerc n1 = mappa.get(res.getInt("nerc1"));
					Nerc n2 = mappa.get(res.getInt("nerc2"));
					Integer peso = res.getInt("peso");
					
					Collegamento col = new Collegamento(n1, n2, peso);
					
					lista.add(col);
				}
			}
			
			con.close();
			
		}catch (SQLException e) {
			throw new RuntimeException("ERRORE DB: impossibile prendere i dati relativi ai due nerc passati.",e);
		}

		return lista;
		
	}
}
