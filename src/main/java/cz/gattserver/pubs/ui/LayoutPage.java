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

public class LayoutPage extends BaseLayout {

	private static final long serialVersionUID = -2049981787052398061L;

	private Content currentContent;
	private HorizontalLayout menuLayout;

	private Button homeBtn;
	private Button pubsBtn;
	private Button beerBtn;
	private Button loginBtn;

	private WebRequest webRequest;

	public WebRequest getWebRequest() {
		return webRequest;
	}

	private void selectButton(Button btn) {
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

	public LayoutPage(WebRequest request, String path) {
		this.webRequest = request;

		addStyleName("layout-box");
		setWidth("1000px");
		// setHeight("100%");

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

		pubsBtn = new Button("Hospody", new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				insertPubsContent();
			}
		});
		menuLayout.addComponent(pubsBtn);
		pubsBtn.setImmediate(true);
		pubsBtn.setStyleName(Reindeer.BUTTON_LINK);
		pubsBtn.addStyleName("main-button");

		beerBtn = new Button("Piva", new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				insertBeerContent();
			}
		});
		menuLayout.addComponent(beerBtn);
		beerBtn.setStyleName(Reindeer.BUTTON_LINK);
		beerBtn.addStyleName("main-button");

		Label separator = new Label();
		menuLayout.addComponent(separator);
		menuLayout.setExpandRatio(separator, 1);

		UserDTO user = getPubsUI().getUser();
		if (user == null) {
			loginBtn = new Button("Přihlásit", new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					setContent(new LoginContent(LayoutPage.this));
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

		// Navigace přes bookmarkable URL
		if (path == null)
			path = "";
		switch (path) {
		case PubsContent.CONTENT_PATH:
			insertPubsContent();
			break;
		case BeerContent.CONTENT_PATH:
			insertBeerContent();
			break;
		case RegisterContent.CONTENT_PATH:
			setContent(new RegisterContent(LayoutPage.this));
			break;
		case HomeContent.CONTENT_PATH:
		default:
			insertHomeContent();
			break;
		}
	}

	private void insertPubsContent() {
		// musí být první, aby se dal přepsat konkrétní položkou
		webRequest.updateURL(PubsContent.CONTENT_PATH);
		setContent(new PubsContent(LayoutPage.this));
		selectButton(pubsBtn);
	}

	private void insertBeerContent() {
		// musí být první, aby se dal přepsat konkrétní položkou
		webRequest.updateURL(BeerContent.CONTENT_PATH);
		setContent(new BeerContent(LayoutPage.this));
		selectButton(beerBtn);
	}

	private void insertHomeContent() {
		webRequest.updateURL(HomeContent.CONTENT_PATH);
		setContent(new HomeContent(LayoutPage.this));
		selectButton(homeBtn);
	}

	public void setContent(Content content) {
		if (currentContent != null)
			removeComponent(currentContent);
		if (content != null) {
			currentContent = content;
			addComponent(currentContent);
			setComponentAlignment(currentContent, Alignment.TOP_LEFT);
			setExpandRatio(currentContent, 1);
		}
	}

}
