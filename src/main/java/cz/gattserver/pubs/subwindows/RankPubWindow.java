package cz.gattserver.pubs.subwindows;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.web.common.window.WebWindow;

public class RankPubWindow extends WebWindow {

	private static final long serialVersionUID = -5681365970486437642L;

	@Autowired
	private PubFacade pubFacade;

	public RankPubWindow(PubDTO p) {
		super("Hodnocení");

		RatingStars ratingStars = new RatingStars();
		ratingStars.setAnimated(false);
		ratingStars.setValue((double) (p.getRank()));
		ratingStars.setCaption("Hodnocení");
		addComponent(ratingStars);

		Button createBtn = new Button("Uložit", new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				pubFacade.updatePubRank(p.getId(), ratingStars.getValue().intValue());
				onCreation(p.getId());
				getUI().removeWindow(RankPubWindow.this);
			}
		});
		addComponent(createBtn);
		setComponentAlignment(createBtn, Alignment.BOTTOM_RIGHT);

	}

	protected void onCreation(Long id) {
	}

}
