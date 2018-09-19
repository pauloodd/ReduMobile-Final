package br.com.redu.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public final class Activity extends StatusAnswerable implements Serializable{
	public Activity(Date createdAt, Date updatedAt, int id, String text,
			User user, ArrayList<Answer> answers, ReduEntityWithWall statusable, int answersCount) {
		super(createdAt, updatedAt, id, text, user, answers, statusable, answersCount);
	}
}