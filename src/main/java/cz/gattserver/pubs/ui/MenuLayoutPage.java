package cz.gattserver.pubs.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

import cz.gattserver.pubs.model.dto.UserDTO;
import cz.gattserver.web.common.WebRequest;

public abstract class MenuLayoutPage extends LayoutPage {

	private static final long serialVersionUID = -2049981787052398061L;

	private DispatcherPage dispatcherPage;

	private HorizontalLayout menuLayout;

	private Button homeBtn;
	protected Button placesBtn;
	protected Button itemsBtn;
	private Button loginBtn;

	protected abstract String getPlacesCaption();

	protected abstract String getItemsCaption();

	protected void selectButton(Button btn) {
		for (int i = 0; i < menuLayout.getComponentCount(); i++) {
			Component c = menuLayout.getComponent(i);
			if (c instanceof Button) {
				if (c.equals(btn)) {
					c.addStyleName("main-button-selected");
					c.removeStyleName("main-button");
				} else {
					c.addStyleName("main-button");
					c.removeStyleName("main-button-selected");
				}
			}
		}
	}

	public MenuLayoutPage(final WebRequest request, DispatcherPage dispatcherPage) {
		super(request);
		this.dispatcherPage = dispatcherPage;

		addStyleName("layout-box");
		setWidth("1000px");

		menuLayout = new HorizontalLayout();
		menuLayout.setSpacing(true);
		menuLayout.setMargin(true);
		addComponent(menuLayout);
		setComponentAlignment(menuLayout, Alignment.TOP_CENTER);
		menuLayout.setWidth("100%");
		menuLayout.addStyleName("main-menu");

		homeBtn = new Button("Domů", new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				insertHomeContent();
			}
		});
		menuLayout.addComponent(homeBtn);
		homeBtn.setImmediate(true);
		homeBtn.setStyleName(Reindeer.BUTTON_LINK);
		homeBtn.addStyleName("main-button");

		placesBtn = new Button(getPlacesCaption(), new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				insertPlacesContent();
			}
		});
		menuLayout.addComponent(placesBtn);
		placesBtn.setImmediate(true);
		placesBtn.setStyleName(Reindeer.BUTTON_LINK);
		placesBtn.addStyleName("main-button");

		itemsBtn = new Button(getItemsCaption(), new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				insertItemsContent();
			}
		});
		menuLayout.addComponent(itemsBtn);
		itemsBtn.setStyleName(Reindeer.BUTTON_LINK);
		itemsBtn.addStyleName("main-button");

		Label separator = new Label();
		menuLayout.addComponent(separator);
		menuLayout.setExpandRatio(separator, 1);

		UserDTO user = getPubsUI().getUser();
		if (user == null) {
			loginBtn = new Button("Přihlásit", new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					setContent(new LoginContent(MenuLayoutPage.this));
					selectButton(loginBtn);
				}
			});
			menuLayout.addComponent(loginBtn);
			loginBtn.setStyleName(Reindeer.BUTTON_LINK);
			loginBtn.addStyleName("main-button");
		} else {
			Button logOffButton = new Button("Odhlásit uživatele " + user.getName(), new Button.ClickListener() {
				private static final long serialVersionUID = 5161534666150825952L;

				@Override
				public void buttonClick(ClickEvent event) {
					webRequest.redirectToPage("j_spring_security_logout");
				}

			});
			menuLayout.addComponent(logOffButton);
			logOffButton.setStyleName(Reindeer.BUTTON_LINK);
			logOffButton.addStyleName("main-button");
		}

		navigate(webRequest);
	}

	protected abstract void navigate(WebRequest webRequest);

	protected abstract void insertPlacesContent();

	protected abstract void insertItemsContent();

	protected void insertHomeContent() {
		webRequest.updateURL(DispatcherPage.PATH);
		dispatcherPage.insertDispatcher();
	}

}
