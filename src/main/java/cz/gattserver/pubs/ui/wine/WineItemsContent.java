package cz.gattserver.pubs.ui.wine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.model.dto.PubTagDTO;
import cz.gattserver.pubs.ui.ItemsContent;
import cz.gattserver.pubs.ui.MenuLayoutPage;

public class WineItemsContent extends ItemsContent {

	private static final long serialVersionUID = -2446097146634308270L;

	public static final String PATH = "items";

	@Autowired
	private PubFacade pubFacade;

	public WineItemsContent(MenuLayoutPage layoutPage) {
		super(layoutPage);
	}

	@Override
	protected String getContentPath() {
		return WinePubsContent.PATH;
	}

	@Override
	protected String getSectionPath() {
		return WineLayoutPage.PATH;
	}

	@Override
	protected String getTableCaption() {
		return "Víno a pochutiny";
	}

	@Override
	protected String getCountCaption() {
		return "Počet asociovaných vináren";
	}

	@Override
	protected List<PubTagDTO> getTags() {
		return pubFacade.getWinePubTags();
	}
}
