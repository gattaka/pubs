package cz.gattserver.pubs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.gattserver.pubs.exception.ApplicationErrorHandler;
import cz.gattserver.pubs.facades.SecurityFacade;
import cz.gattserver.pubs.facades.UserFacade;
import cz.gattserver.pubs.model.dto.UserDTO;
import cz.gattserver.pubs.ui.LayoutPage;

@Title("Pubs")
@Theme("pubs")
public class PubsUI extends UI {

	private static final long serialVersionUID = -785347532002801786L;
	private static Logger logger = LoggerFactory.getLogger(PubsUI.class);

	@Autowired
	private SecurityFacade securityFacade;

	@Autowired
	private UserFacade userFacade;

	public PubsUI() {
		SpringContextHelper.inject(this);
	}

	/**
	 * Získá aktuálního přihlášeného uživatele jako {@link UserDTO} objekt
	 */
	public UserDTO getUser() {
		return securityFacade.getCurrentUser();
	}

	public boolean login(String username, String password) {
		return securityFacade.login(username, password);
	}

	public void init(VaadinRequest request) {

		VaadinSession.getCurrent().setErrorHandler(new ApplicationErrorHandler());

		VerticalLayout wrapper = new VerticalLayout();
		setContent(wrapper);
		wrapper.setSizeFull();
		wrapper.setSpacing(true);
		wrapper.setMargin(true);
		LayoutPage layoutPage = new LayoutPage(request);
		wrapper.addComponent(layoutPage);
		wrapper.setComponentAlignment(layoutPage, Alignment.MIDDLE_CENTER);

		// userFacade.registrateNewUser("gattakahynca@seznam.cz", "gattaka", "pubsAdmin1234");
	}
}
