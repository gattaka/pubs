package cz.gattserver.pubs.ui;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.themes.Reindeer;

import cz.gattserver.pubs.ui.beer.BeerLayoutPage;
import cz.gattserver.pubs.ui.coffee.CoffeeLayoutPage;
import cz.gattserver.pubs.ui.wine.WineLayoutPage;
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
		case WineLayoutPage.PATH:
			insertWineLayout();
			break;
		case CoffeeLayoutPage.PATH:
			insertCoffeeLayout();
			break;
		case RegisterContent.PATH:
			insertRegister();
			break;
		case LoginContent.PATH:
			insertLogin();
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
		beerPubsBtn.setIcon(new ThemeResource("img/beer_w.png"));
		beerPubsBtn.setStyleName(Reindeer.BUTTON_LINK);
		beerPubsBtn.addStyleName("beer-button");

		winePubsBtn = new Button(null, new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				insertWineLayout();
			}
		});
		dispatcherLayout.addComponent(winePubsBtn);
		winePubsBtn.setIcon(new ThemeResource("img/wine_w.png"));
		winePubsBtn.setStyleName(Reindeer.BUTTON_LINK);
		winePubsBtn.addStyleName("wine-button");

		coffeeBtn = new Button(null, new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				insertCoffeeLayout();
			}
		});
		dispatcherLayout.addComponent(coffeeBtn);
		coffeeBtn.setIcon(new ThemeResource("img/coffee_w.png"));
		coffeeBtn.setStyleName(Reindeer.BUTTON_LINK);
		coffeeBtn.addStyleName("coffee-button");
	}

	private void insertRegister() {
		Layout layout = new RegisterContent() {
			private static final long serialVersionUID = 7509703069950261132L;

			@Override
			protected void onRegister() {
				insertDispatcher();
			}
		};
		setContent(layout);
		setComponentAlignment(layout, Alignment.TOP_CENTER);
	}

	private void insertLogin() {
		Layout layout = new LoginContent();
		setContent(layout);
		setComponentAlignment(layout, Alignment.TOP_CENTER);
	}

	private void insertBeerLayout() {
		Layout layout = new BeerLayoutPage(webRequest, DispatcherPage.this);
		setContent(layout);
		setComponentAlignment(layout, Alignment.TOP_CENTER);
	}

	private void insertWineLayout() {
		Layout layout = new WineLayoutPage(webRequest, DispatcherPage.this);
		setContent(layout);
		setComponentAlignment(layout, Alignment.TOP_CENTER);
	}

	private void insertCoffeeLayout() {
		Layout layout = new CoffeeLayoutPage(webRequest, DispatcherPage.this);
		setContent(layout);
		setComponentAlignment(layout, Alignment.TOP_CENTER);
	}

}
