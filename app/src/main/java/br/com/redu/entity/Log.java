package br.com.redu.entity;

import java.util.Date;
import java.util.List;


public final class Log extends Status {

	private List<String> links;

	public Log(Date createdAt, Date updatedAt, int id, String text,
               User user,List<String> links) {
		super(createdAt, updatedAt, id, text, user);
		this.links = links;
	}

	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}
}
