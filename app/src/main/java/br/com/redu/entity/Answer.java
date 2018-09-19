package br.com.redu.entity;

import java.io.Serializable;
import java.util.Date;


public final class Answer extends Status  implements Serializable {
	private StatusAnswerable inResponseTo;

	public Answer(Date createdAt, Date updatedAt, int id, String text,
			User user, StatusAnswerable inResponseTo) {
		super(createdAt, updatedAt, id, text, user);

		this.inResponseTo = inResponseTo;
	}

	public StatusAnswerable getInResponseTo() {
		return inResponseTo;
	}

	public void setInResponseTo(StatusAnswerable inResponseTo) {
		this.inResponseTo = inResponseTo;
	}
}
