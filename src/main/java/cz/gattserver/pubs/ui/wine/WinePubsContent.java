package cz.gattserver.pubs.ui.wine;

import java.util.Collection;

import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.subwindows.CreatePubWindow;
import cz.gattserver.pubs.subwindows.CreatePubWindow.OnCreationListener;
import cz.gattserver.pubs.ui.MenuLayoutPage;
import cz.gattserver.pubs.ui.PlaceContent;

public class WinePubsContent extends PlaceContent {

	private static final long serialVersionUID = -2446097146634308270L;

	public static final String PATH = "pubs";

	public WinePubsContent(MenuLayoutPage layoutPage) {
		super(layoutPage);
	}

	@Override
	protected Collection<PubDTO> populateTable() {
		return pubFacade.getAllWinePubs();
	}

	@Override
	protected String getContentPath() {
		return PATH;
	}

	@Override
	protected String createNewItemCaption() {
		return "Založit novou vinárnu";
	}

	@Override
	protected String modifyItemCaption() {
		return "Uprav vinárnu";
	}

	@Override
	protected CreatePubWindow openCreatePubWindow(PubDTO pubDTO, OnCreationListener onCreationListener) {
		return new WineCreatePubWindow(pubDTO, onCreationListener);
	}

	@Override
	protected String getSectionPath() {
		return WineLayoutPage.PATH;
	}

}
