package cz.gattserver.pubs.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

public class LayoutPage extends VerticalLayout {

	private static final long serialVersionUID = -2049981787052398061L;

	private Content currentContent;
	private HorizontalLayout menuLayout;

	private Button homeBtn;
	private Button hospodyBtn;
	private Button pivaBtn;
	private Button loginBtn;

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

	public LayoutPage() {

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
