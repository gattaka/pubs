package cz.gattserver.pubs.ui.wine;

import cz.gattserver.pubs.ui.DispatcherPage;
import cz.gattserver.pubs.ui.MenuLayoutPage;
import cz.gattserver.web.common.WebRequest;

public class WineLayoutPage extends MenuLayoutPage {

	private static final long serialVersionUID = -8530529024734782401L;

	public static final String PATH = "wine";

	public WineLayoutPage(WebRequest request, DispatcherPage dispatcherPage) {
		super(request, dispatcherPage);
	}

	@Override
	protected String getPlacesCaption() {
		return "Vinárny";
	}

	@Override
	protected String getItemsCaption() {
		return "Vína";
	}

	@Override
	protected void insertPlacesContent() {
		// musí být první, aby se dal přepsat konkrétní položkou
		webRequest.updateURL(WineLayoutPage.PATH, WinePubsContent.PATH);
		setContent(new WinePubsContent(WineLayoutPage.this));
		selectButton(placesBtn);
	}

	@Override
	protected void insertItemsContent() {
		// musí být první, aby se dal přepsat konkrétní položkou
		webRequest.updateURL(WineLayoutPage.PATH, WineItemsContent.PATH);
		setContent(new WineItemsContent(WineLayoutPage.this));
		selectButton(itemsBtn);
	}

	@Override
	protected void navigate(WebRequest webRequest) {
		// Navigace přes bookmarkable URL
		String path = webRequest.getAnalyzer().getNextPathToken();
		String switchablePath = path == null ? "" : path;
		switch (switchablePath) {
		case WineItemsContent.PATH:
			insertItemsContent();
			break;
		case DispatcherPage.PATH:
			insertHomeContent();
			break;
		case WinePubsContent.PATH:
		default:
			insertPlacesContent();
			break;
		}
	}

}
