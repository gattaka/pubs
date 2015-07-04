package cz.gattserver.pubs.ui.coffee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.model.dto.PubTagDTO;
import cz.gattserver.pubs.ui.ItemsContent;
import cz.gattserver.pubs.ui.MenuLayoutPage;

public class CoffeeItemsContent extends ItemsContent {

	private static final long serialVersionUID = -2446097146634308270L;

	public static final String PATH = "items";

	@Autowired
	private PubFacade pubFacade;

	public CoffeeItemsContent(MenuLayoutPage layoutPage) {
		super(layoutPage);
	}

	@Override
	protected String getContentPath() {
		return CoffeePubsContent.PATH;
	}

	@Override
	protected String getSectionPath() {
		return CoffeeLayoutPage.PATH;
	}

	@Override
	protected String getTableCaption() {
		return "Káva a pochutiny";
	}

	@Override
	protected String getCountCaption() {
		return "Počet asociovaných kaváren";
	}

	@Override
	protected List<PubTagDTO> getTags() {
		return pubFacade.getCoffeePubTags();
	}
}
