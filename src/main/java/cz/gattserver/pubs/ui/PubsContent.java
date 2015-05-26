package cz.gattserver.pubs.ui;

import java.util.Collection;

import cz.gattserver.pubs.model.dto.PubDTO;

public class PubsContent extends PlaceContent {

	private static final long serialVersionUID = -2446097146634308270L;

	public static final String PATH = "pubs";
	public static final String NO_PHOTO_PATH = "img/no_foto.png";

	public PubsContent(MenuLayoutPage layoutPage) {
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
	protected String getNoPhotoPath() {
		return NO_PHOTO_PATH;
	}

	@Override
	protected String getRankPhotoPath() {
		return "img/unetice_small.png";
	}

	@Override
	protected String getRankSmallPhotoPath() {
		return "img/unetice_micro.png";
	}

	@Override
	protected String createNewItemCaption() {
		return "Založit novou hospodu";
	}

	@Override
	protected String modifyItemCaption() {
		return "Uprav hospodu";
	}

}
