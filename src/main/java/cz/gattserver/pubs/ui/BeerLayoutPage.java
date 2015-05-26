package cz.gattserver.pubs.ui;

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
		webRequest.updateURL(PubsContent.PATH);
		setContent(new PubsContent(BeerLayoutPage.this));
		selectButton(placesBtn);
	}

	@Override
	protected void insertItemsContent() {
		// musí být první, aby se dal přepsat konkrétní položkou
		webRequest.updateURL(BeerLayoutPage.PATH,BeerContent.PATH);
		setContent(new BeerContent(BeerLayoutPage.this));
		selectButton(itemsBtn);
	}

	@Override
	protected void navigate(WebRequest webRequest) {
		// Navigace přes bookmarkable URL
		String path = webRequest.getAnalyzer().getNextPathToken();
		String switchablePath = path == null ? "" : path;
		switch (switchablePath) {
		case PubsContent.PATH:
			insertPlacesContent();
			break;
		case BeerContent.PATH:
			insertItemsContent();
			break;
		case RegisterContent.PATH:
			setContent(new RegisterContent(BeerLayoutPage.this));
			break;
		case DispatcherPage.PATH:
		default:
			insertHomeContent();
			break;
		}
	}

}
