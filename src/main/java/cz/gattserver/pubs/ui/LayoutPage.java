package cz.gattserver.pubs.ui;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

import cz.gattserver.pubs.model.dto.UserDTO;

public class LayoutPage extends BaseLayout {

	private static final long serialVersionUID = -2049981787052398061L;

	private Content currentContent;
	private HorizontalLayout menuLayout;

	private Button homeBtn;
	private Button hospodyBtn;
	private Button pivaBtn;
	private Button loginBtn;

	private VaadinRequest request;

	/**
	 * Získá URL stránky. Kořen webu + suffix
	 */
	public String getPageURL(String suffix) {
		return request.getContextPath() + "/" + suffix;
	}

	/**
	 * Přejde na stránku
	 */
	public void redirect(String uri) {
		Page.getCurrent().setLocation(uri);
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

	public LayoutPage(VaadinRequest request) {
		this.request = request;

		addStyleName("layout-box");
		setWidth("1000px");
		setHeight("100%");
		
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
				setContent(new HomeContent(LayoutPage.this));
				selectButton(homeBtn);
			}
		});
		menuLayout.addComponent(homeBtn);
		homeBtn.setImmediate(true);
		homeBtn.setStyleName(Reindeer.BUTTON_LINK);
		homeBtn.addStyleName("main-button");

		hospodyBtn = new Button("Hospody", new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				setContent(new PubsContent(LayoutPage.this));
				selectButton(hospodyBtn);
			}
		});
		menuLayout.addComponent(hospodyBtn);
		hospodyBtn.setImmediate(true);
		hospodyBtn.setStyleName(Reindeer.BUTTON_LINK);
		hospodyBtn.addStyleName("main-button");

		pivaBtn = new Button("Piva", new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				setContent(new HomeContent(LayoutPage.this));
				selectButton(pivaBtn);
			}
		});
		menuLayout.addComponent(pivaBtn);
		pivaBtn.setStyleName(Reindeer.BUTTON_LINK);
		pivaBtn.addStyleName("main-button");

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
					redirect(getPageURL("j_spring_security_logout"));
				}

			});
			menuLayout.addComponent(logOffButton);
			logOffButton.setStyleName(Reindeer.BUTTON_LINK);
			logOffButton.addStyleName("main-button");
		}

		setContent(new HomeContent(this));
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
