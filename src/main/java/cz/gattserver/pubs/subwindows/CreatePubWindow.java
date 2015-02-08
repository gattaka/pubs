package cz.gattserver.pubs.subwindows;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.subwindows.common.BaseWindow;
import cz.gattserver.pubs.subwindows.common.ConfirmWindow;

public class CreatePubWindow extends BaseWindow {

	private static final long serialVersionUID = -5681365970486437642L;

	@Autowired
	private PubFacade pubFacade;

	public CreatePubWindow() {
		super("Založit novou hospodu");

		BeanFieldGroup<PubDTO> beanFieldGroup = new BeanFieldGroup<PubDTO>(PubDTO.class);
		beanFieldGroup.setItemDataSource(new PubDTO());

		TextField nameField = new TextField("Název hospody");
		beanFieldGroup.bind(nameField, "name");
		addComponent(nameField);

		TextField addressField = new TextField("Adresa");
		beanFieldGroup.bind(addressField, "address");
		addComponent(addressField);

		ComboBox rankBox = new ComboBox("Hodnocení (víc je líp)", Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 }));
		rankBox.setNullSelectionAllowed(false);
		rankBox.setValue(3);
		beanFieldGroup.bind(rankBox, "rank");
		addComponent(rankBox);

		TextArea descriptionField = new TextArea("Popis");
		descriptionField.setWidth("400px");
		descriptionField.setHeight("100px");
		beanFieldGroup.bind(descriptionField, "description");
		addComponent(descriptionField);

		Button createBtn = new Button("Založit", new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					beanFieldGroup.commit();
					pubFacade.createPub(beanFieldGroup.getItemDataSource().getBean());
					getUI().removeWindow(CreatePubWindow.this);
				} catch (CommitException e) {
					e.printStackTrace();
				}
			}
		});
		addComponent(createBtn);
		setComponentAlignment(createBtn, Alignment.BOTTOM_RIGHT);

	}

	@Override
	public void close() {
		getUI().addWindow(new ConfirmWindow("Opravdu zrušit zakládání nové hospody?") {
			private static final long serialVersionUID = -1263084559774811237L;

			@Override
			protected void onConfirm(ClickEvent event) {
				CreatePubWindow.super.close();
			}
		});
	}

}
