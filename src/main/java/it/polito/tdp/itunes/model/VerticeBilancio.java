package it.polito.tdp.itunes.model;

import java.util.Objects;

public class VerticeBilancio implements Comparable<VerticeBilancio>{
	
	Album a;
	double bilancio;
	public VerticeBilancio(Album a, double bilancio) {
		super();
		this.a = a;
		this.bilancio = bilancio;
	}
	public Album getA() {
		return a;
	}
	public void setA(Album a) {
		this.a = a;
	}
	public double getBilancio() {
		return bilancio;
	}
	public void setBilancio(double bilancio) {
		this.bilancio = bilancio;
	}
	@Override
	public int hashCode() {
		return Objects.hash(a, bilancio);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VerticeBilancio other = (VerticeBilancio) obj;
		return Objects.equals(a, other.a)
				&& Double.doubleToLongBits(bilancio) == Double.doubleToLongBits(other.bilancio);
	}
	@Override
	public int compareTo(VerticeBilancio o) {
		if(this.getBilancio() > o.getBilancio()) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
	

}
