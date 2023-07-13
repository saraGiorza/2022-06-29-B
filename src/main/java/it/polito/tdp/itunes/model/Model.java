package it.polito.tdp.itunes.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {

	private ItunesDAO dao = new ItunesDAO();
	private Graph<Album, DefaultWeightedEdge> grafo;

	public void creaGrafo(double durata) {

		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

		// vertici
		Graphs.addAllVertices(grafo, dao.getAlbumsDurata(durata));

		// archi
		for (Album a1 : grafo.vertexSet()) {
			for (Album a2 : grafo.vertexSet()) {
				if (!a1.equals(a2)) {
					double d1 = a1.getDurata();
					double d2 = a2.getDurata();
					if (d1 != d2) {
						double tot = d1 + d2;
						if (tot > 4 * durata*1000) {
							if (d1 < d2) {
								Graphs.addEdge(grafo, a1, a2, tot);
							} else {
								Graphs.addEdge(grafo, a2, a1, tot);
							}
						}
					}
				}
			}
		}
	}

	public List<Album> verticiGrafo() {
		List<Album> vertici = new ArrayList<>(grafo.vertexSet());
		Collections.sort(vertici);
		return vertici;
	}

	public String infoGrafo() {
		// se il grafo non è stato creato ritorna stringa vuota
		try {
			return "Grafo creato con " + grafo.vertexSet().size() + " vertici " + grafo.edgeSet().size() + " archi";
		} catch (NullPointerException npe) {
			return "";
		}
	}
	
	public String successoriAlbum(Album a1) {
		Set<DefaultWeightedEdge> successoriA = grafo.outgoingEdgesOf(a1);
		List<VerticeBilancio> vertici = new ArrayList<>();
		for(DefaultWeightedEdge e: successoriA) {
			Album successore = Graphs.getOppositeVertex(grafo, e, a1);
			double bilancio = 0.0;
			for(DefaultWeightedEdge e1: grafo.incomingEdgesOf(successore)) {
				bilancio+= grafo.getEdgeWeight(e1);				
			}
			for(DefaultWeightedEdge e1: grafo.outgoingEdgesOf(successore)) {
				bilancio-= grafo.getEdgeWeight(e1);				
			} 
			VerticeBilancio vb = new VerticeBilancio(successore, bilancio);
			vertici.add(vb);
		}
		
		Collections.sort(vertici);
		
		String result = "\nADIACENZE:\n";
		for(VerticeBilancio v: vertici) {
			result+= v.getA().getTitle() + "   , bilancio = " + v.getBilancio() + "\n";
		}
		
		return result;
	}

}
