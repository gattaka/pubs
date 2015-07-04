package cz.gattserver.pubs.ui.beer;

import cz.gattserver.pubs.ui.DispatcherPage;
import cz.gattserver.pubs.ui.MenuLayoutPage;
import cz.gattserver.web.common.WebRequest;

public class BeerLayoutPage extends MenuLayoutPage {

	private static final long serialVersionUID = -8530529024734782401L;

	public static final String PATH = "beer";

	public BeerLayoutPage(WebRequest request, DispatcherPage dispatcherPage) {
		super(request, dispatcherPage);
	}

	@Override
	protected String getPlacesCaption() {
		return "Hospody";
	}

	@Override
	protected String getItemsCaption() {
		return "Piva";
	}

	@Override
	protected void insertPlacesContent() {
		// musí být první, aby se dal přepsat konkrétní položkou
		webRequest.updateURL(BeerLayoutPage.PATH, BeerPubsContent.PATH);
		setContent(new BeerPubsContent(BeerLayoutPage.this));
		selectButton(placesBtn);
	}

	@Override
	protected void insertItemsContent() {
		// musí být první, aby se dal přepsat konkrétní položkou
		webRequest.updateURL(BeerLayoutPage.PATH, BeerItemsContent.PATH);
		setContent(new BeerItemsContent(BeerLayoutPage.this));
		selectButton(itemsBtn);
	}

	@Override
	protected void navigate(WebRequest webRequest) {
		// Navigace přes bookmarkable URL
		String path = webRequest.getAnalyzer().getNextPathToken();
		String switchablePath = path == null ? "" : path;
		switch (switchablePath) {
		case BeerItemsContent.PATH:
			insertItemsContent();
			break;
		case DispatcherPage.PATH:
			insertHomeContent();
			break;
		case "j_spring_security_check":
			break;
		case BeerPubsContent.PATH:
		default:
			insertPlacesContent();
			break;
		}
	}
}
