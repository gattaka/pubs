package cz.gattserver.pubs.ui;

import com.vaadin.ui.Label;

import cz.gattserver.pubs.model.dto.UserDTO;

public class HomeContent extends Content {

	private static final long serialVersionUID = -2446097146634308270L;

	public HomeContent(LayoutPage layoutPage) {
		super(layoutPage);
		UserDTO user = getPubsUI().getUser();
		addComponent(new Label("Home" + (user == null ? " anon " : user.getName())));
	}

}
