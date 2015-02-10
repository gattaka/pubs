package cz.gattserver.pubs.subwindows;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.teemu.ratingstars.RatingStars;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.LayoutEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.web.common.window.ConfirmWindow;
import cz.gattserver.web.common.window.ErrorWindow;
import cz.gattserver.web.common.window.WebWindow;

public class CreatePubWindow extends WebWindow {

	private static final long serialVersionUID = -5681365970486437642L;

	@Autowired
	private PubFacade pubFacade;

	public CreatePubWindow() {
		this(new PubDTO());
	}

	public CreatePubWindow(PubDTO p) {
		super("Hospoda");

		BeanFieldGroup<PubDTO> beanFieldGroup = new BeanFieldGroup<PubDTO>(PubDTO.class);
		beanFieldGroup.setItemDataSource(p);

		TextField nameField = new TextField("Název hospody");
		nameField.setNullRepresentation("");
		beanFieldGroup.bind(nameField, "name");
		addComponent(nameField);

		TextField addressField = new TextField("Adresa");
		addressField.setNullRepresentation("");
		beanFieldGroup.bind(addressField, "address");
		addComponent(addressField);

		TextField webAddressField = new TextField("Webová adresa");
		webAddressField.setNullRepresentation("");
		beanFieldGroup.bind(webAddressField, "webAddress");
		addComponent(webAddressField);

		ComboBox rankBox = new ComboBox("Hodnocení (víc je líp)", Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 }));
		rankBox.setNullSelectionAllowed(false);
		beanFieldGroup.bind(rankBox, "rank");
		rankBox.setValue(3);

		// DateField lastVisitField = new DateField("Naposledy navštíveno");
		// beanFieldGroup.bind(lastVisitField, "lastVisit");

		// HorizontalLayout rankAndVisitLayout = new HorizontalLayout(rankBox, lastVisitField);
		// rankAndVisitLayout.setSpacing(true);
		// addComponent(rankAndVisitLayout);

		// final int RANK_BUTTONS_COUNT = 5;
		// HorizontalLayout rankLayout = new HorizontalLayout();
		// rankLayout.setSpacing(true);
		// Button[] rankButtons = new Button[RANK_BUTTONS_COUNT];
		//
		// for (int i = 0; i < RANK_BUTTONS_COUNT; i++) {
		// Button rankButton = new Button("", new Button.ClickListener() {
		// private static final long serialVersionUID = 2071604101486581247L;
		//
		// @Override
		// public void buttonClick(ClickEvent event) {
		// }
		// });
		// rankButton.addListener(Mouse., target, method);
		// rankLayout.addComponent(rankButton);
		// rankButton.setImmediate(true);
		// rankButton.setStyleName(Reindeer.BUTTON_LINK);
		// rankButton.addStyleName("rank-button");
		// rankButton.setIcon((com.vaadin.server.Resource) new ThemeResource("img/unetice_rank.png"));
		// rankButtons[i] = rankButton;
		// }
		// addComponent(rankLayout);

		RatingStars ratingStars = new RatingStars();
		ratingStars.setAnimated(false);
		addComponent(ratingStars);

		TextArea descriptionField = new TextArea("Popis");
		descriptionField.setWidth("400px");
		descriptionField.setHeight("100px");
		descriptionField.setNullRepresentation("");
		beanFieldGroup.bind(descriptionField, "description");
		addComponent(descriptionField);

		Button createBtn = new Button("Uložit", new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					beanFieldGroup.commit();

					if (StringUtils.isBlank(nameField.getValue())) {
						getUI().addWindow(new ErrorWindow("Název nesmí být prázdný"));
						return;
					}

					if (StringUtils.isBlank(addressField.getValue())) {
						getUI().addWindow(new ErrorWindow("Adresa nesmí být prázdná"));
						return;
					}

					Long id = pubFacade.createPub(beanFieldGroup.getItemDataSource().getBean());
					onCreation(id);
					getUI().removeWindow(CreatePubWindow.this);
				} catch (CommitException e) {
					e.printStackTrace();
				}
			}
		});
		addComponent(createBtn);
		setComponentAlignment(createBtn, Alignment.BOTTOM_RIGHT);

	}

	protected void onCreation(Long id) {
	}

	@Override
	public void close() {
		getUI().addWindow(new ConfirmWindow("Opravdu zrušit zakládání/úpravu nové hospody?") {
			private static final long serialVersionUID = -1263084559774811237L;

			@Override
			protected void onConfirm(ClickEvent event) {
				CreatePubWindow.super.close();
			}
		});
	}

}
