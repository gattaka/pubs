package cz.gattserver.pubs.ui.beer;

import java.util.Collection;

import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.subwindows.CreatePubWindow;
import cz.gattserver.pubs.subwindows.CreatePubWindow.OnCreationListener;
import cz.gattserver.pubs.ui.MenuLayoutPage;
import cz.gattserver.pubs.ui.PlaceContent;

public class BeerPubsContent extends PlaceContent {

	private static final long serialVersionUID = -2446097146634308270L;

	public static final String PATH = "pubs";

	public BeerPubsContent(MenuLayoutPage layoutPage) {
		super(layoutPage);
	}

	@Override
	protected Collection<PubDTO> populateTable() {
		return pubFacade.getAllBeerPubs();
	}

	@Override
	protected String getContentPath() {
		return PATH;
	}

	@Override
	protected String createNewItemCaption() {
		return "Založit novou hospodu";
	}

	@Override
	protected String modifyItemCaption() {
		return "Uprav hospodu";
	}

	@Override
	protected CreatePubWindow openCreatePubWindow(PubDTO pubDTO, OnCreationListener onCreationListener) {
		return new BeerCreatePubWindow(pubDTO, onCreationListener);
	}

	@Override
	protected String getSectionPath() {
		return BeerLayoutPage.PATH;
	}

}
