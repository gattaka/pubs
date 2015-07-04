package cz.gattserver.pubs.ui.coffee;

import cz.gattserver.pubs.ui.DispatcherPage;
import cz.gattserver.pubs.ui.MenuLayoutPage;
import cz.gattserver.web.common.WebRequest;

public class CoffeeLayoutPage extends MenuLayoutPage {

	private static final long serialVersionUID = -8530529024734782401L;

	public static final String PATH = "coffee";

	public CoffeeLayoutPage(WebRequest request, DispatcherPage dispatcherPage) {
		super(request, dispatcherPage);
	}

	@Override
	protected String getPlacesCaption() {
		return "Kavárny";
	}

	@Override
	protected String getItemsCaption() {
		return "Kávy";
	}

	@Override
	protected void insertPlacesContent() {
		// musí být první, aby se dal přepsat konkrétní položkou
		webRequest.updateURL(CoffeeLayoutPage.PATH, CoffeePubsContent.PATH);
		setContent(new CoffeePubsContent(CoffeeLayoutPage.this));
		selectButton(placesBtn);
	}

	@Override
	protected void insertItemsContent() {
		// musí být první, aby se dal přepsat konkrétní položkou
		webRequest.updateURL(CoffeeLayoutPage.PATH, CoffeeItemsContent.PATH);
		setContent(new CoffeeItemsContent(CoffeeLayoutPage.this));
		selectButton(itemsBtn);
	}

	@Override
	protected void navigate(WebRequest webRequest) {
		// Navigace přes bookmarkable URL
		String path = webRequest.getAnalyzer().getNextPathToken();
		String switchablePath = path == null ? "" : path;
		switch (switchablePath) {
		case CoffeeItemsContent.PATH:
			insertItemsContent();
			break;
		case DispatcherPage.PATH:
			insertHomeContent();
			break;
		case CoffeePubsContent.PATH:
		default:
			insertPlacesContent();
			break;
		}
	}

}
