package br.com.casuaiscontas.model;

import br.com.casuaiscontas.service.UserService;

public enum UserStatus {

	ACTIVATE {
		
		@Override
		public void update(Long[] ids, UserService service) {
			service.updateStatus(ids, true);
		}
		
	},
	
	DEACTIVATE {
		
		@Override
		public void update(Long[] ids, UserService service) {
			service.updateStatus(ids, false);			
		}
		
	};	
	
	public abstract void update(Long[] ids, UserService service);
}
