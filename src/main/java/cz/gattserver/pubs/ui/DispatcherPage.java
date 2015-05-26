package cz.gattserver.pubs.ui;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.themes.Reindeer;

import cz.gattserver.web.common.WebRequest;

public class DispatcherPage extends LayoutPage {

	private static final long serialVersionUID = -2446097146634308270L;

	public static final String PATH = "home";

	private Button beerPubsBtn;
	private Button winePubsBtn;
	private Button coffeeBtn;

	public DispatcherPage(WebRequest request) {
		super(request);

		setHeight("100%");

		// Navigace přes bookmarkable URL
		String path = webRequest.getAnalyzer().getNextPathToken();
		String switchablePath = path == null ? "" : path;
		switch (switchablePath) {
		case BeerLayoutPage.PATH:
			insertBeerLayout();
			break;
		case DispatcherPage.PATH:
		default:
			insertDispatcher();
			break;
		}
	}

	public void insertDispatcher() {
		HorizontalLayout dispatcherLayout = new HorizontalLayout();
		dispatcherLayout.setSpacing(true);
		setContent(dispatcherLayout);
		setComponentAlignment(dispatcherLayout, Alignment.MIDDLE_CENTER);

		beerPubsBtn = new Button(null, new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				insertBeerLayout();
			}
		});
		dispatcherLayout.addComponent(beerPubsBtn);
		beerPubsBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/beer.png"));
		beerPubsBtn.setStyleName(Reindeer.BUTTON_LINK);

		winePubsBtn = new Button(null, new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				// insertPlacesContent();
			}
		});
		dispatcherLayout.addComponent(winePubsBtn);
		winePubsBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/wine.png"));
		winePubsBtn.setStyleName(Reindeer.BUTTON_LINK);

		coffeeBtn = new Button(null, new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				// insertItemsContent();
			}
		});
		dispatcherLayout.addComponent(coffeeBtn);
		coffeeBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/coffee.png"));
		coffeeBtn.setStyleName(Reindeer.BUTTON_LINK);
	}

	private void insertBeerLayout() {
		Layout layout = new BeerLayoutPage(webRequest, DispatcherPage.this);
		setContent(layout);
		setComponentAlignment(layout, Alignment.TOP_CENTER);
	}

}
