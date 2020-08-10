package br.com.casuaiscontas.service.event.user;

import br.com.casuaiscontas.model.User;

public class SaveUserEvent {
	
	private User user;

	public SaveUserEvent(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
}
