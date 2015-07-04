package cz.gattserver.pubs.ui.beer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.model.dto.PubTagDTO;
import cz.gattserver.pubs.ui.ItemsContent;
import cz.gattserver.pubs.ui.MenuLayoutPage;

public class BeerItemsContent extends ItemsContent {

	public BeerItemsContent(MenuLayoutPage layoutPage) {
		super(layoutPage);
	}

	private static final long serialVersionUID = -2446097146634308270L;

	public static final String PATH = "items";

	@Autowired
	private PubFacade pubFacade;

	@Override
	protected String getContentPath() {
		return BeerPubsContent.PATH;
	}

	@Override
	protected String getSectionPath() {
		return BeerLayoutPage.PATH;
	}

	@Override
	protected String getTableCaption() {
		return "Pivo a pochutiny";
	}

	@Override
	protected String getCountCaption() {
		return "Počet asociovaných hospod";
	}

	@Override
	protected List<PubTagDTO> getTags() {
		return pubFacade.getBeerPubTags();
	}
}
