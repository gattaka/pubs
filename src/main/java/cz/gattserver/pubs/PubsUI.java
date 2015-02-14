package cz.gattserver.pubs;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import cz.gattserver.pubs.facades.SecurityFacade;
import cz.gattserver.pubs.facades.UserFacade;
import cz.gattserver.pubs.model.dto.UserDTO;
import cz.gattserver.pubs.ui.LayoutPage;
import cz.gattserver.web.common.PageState;
import cz.gattserver.web.common.WebRequest;
import cz.gattserver.web.common.WebUI;

@Title("Pubs")
@Theme("pubs")
public class PubsUI extends WebUI {

	private static final long serialVersionUID = -785347532002801786L;

	@Autowired
	private SecurityFacade securityFacade;

	@Autowired
	private UserFacade userFacade;

	/**
	 * Získá aktuálního přihlášeného uživatele jako {@link UserDTO} objekt
	 */
	public UserDTO getUser() {
		return securityFacade.getCurrentUser();
	}

	public boolean login(String username, String password) {
		return securityFacade.login(username, password);
	}

	@Override
	protected void showError(PageState state) {
		// TODO Auto-generated method stub
	}

	@Override
	protected Layout createPageByPath(String path, WebRequest webRequest) {

		VerticalLayout wrapper = new VerticalLayout();
		setContent(wrapper);
		wrapper.setSizeFull();
		wrapper.setSpacing(true);
		wrapper.setMargin(true);
		LayoutPage layoutPage = new LayoutPage(webRequest, path);
		wrapper.addComponent(layoutPage);
		wrapper.setComponentAlignment(layoutPage, Alignment.TOP_CENTER);

		return wrapper;
	}
}
