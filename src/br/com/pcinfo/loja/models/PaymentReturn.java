package br.com.pcinfo.loja.models;


public class PaymentReturn {

	private String link;
	
	public PaymentReturn(String link){
		this.link = link;
	}
	
	private String getLink() {
		return link;
	}
}
