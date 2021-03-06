package cz.gattserver.pubs.ui.beer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.subwindows.CreatePubWindow;

public class BeerCreatePubWindow extends CreatePubWindow {

	public BeerCreatePubWindow(PubDTO p, OnCreationListener onCreationListener) {
		super(p, onCreationListener);
	}

	private static final long serialVersionUID = 5056375936286329956L;

	@Autowired
	private PubFacade pubFacade;

	@Override
	protected Long savePub(PubDTO pubToSave, Collection<String> tags) {
		return pubFacade.saveBeerPub(pubToSave, tags);
	}

	@Override
	protected String getStornoCreationCaption() {
		return "Opravdu zrušit zakládání/úpravu nové hospody?";
	}

}
