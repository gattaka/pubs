package cz.gattserver.pubs.subwindows;

import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.facades.UserFacade;
import cz.gattserver.pubs.facades.VisitFacade;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.model.dto.UserDTO;
import cz.gattserver.pubs.model.dto.VisitDTO;
import cz.gattserver.web.common.window.ConfirmWindow;
import cz.gattserver.web.common.window.ErrorWindow;
import cz.gattserver.web.common.window.WebWindow;

public class CreateVisitWindow extends WebWindow {

	private static final long serialVersionUID = -5681365970486437642L;

	@Autowired
	private PubFacade pubFacade;

	@Autowired
	private VisitFacade visitFacade;

	@Autowired
	private UserFacade userFacade;

	public CreateVisitWindow() {
		this(new VisitDTO());
	}

	public CreateVisitWindow(VisitDTO v) {
		super("Sraz");

		// pokud není již datum dáno, navrhni příští úterý
		if (v.getDate() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			calendar.set(Calendar.WEEK_OF_MONTH, 1);
			int currentMonth = calendar.get(Calendar.MONTH);
			if (currentMonth == Calendar.DECEMBER) {
				calendar.set(Calendar.MONTH, Calendar.JANUARY);
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
			} else {
				calendar.set(Calendar.MONTH, currentMonth + 1);
			}
			v.setDate(calendar.getTime());
		}

		BeanFieldGroup<VisitDTO> beanFieldGroup = new BeanFieldGroup<VisitDTO>(VisitDTO.class);
		beanFieldGroup.setItemDataSource(new BeanItem<VisitDTO>(v));

		DateField dateField = new DateField("Datum");
		dateField.setResolution(Resolution.MINUTE);
		dateField.setLocale(new Locale("cs", "CZ"));
		dateField.setDateFormat("dd.MM.yyyy HH:mm");
		beanFieldGroup.bind(dateField, "date");
		addComponent(dateField);

		BeanItemContainer<UserDTO> users = new BeanItemContainer<>(UserDTO.class);
		users.addAll(userFacade.getAllUsers());

		ComboBox authorComboBox = new ComboBox("Rezervuje", users);

		authorComboBox.setImmediate(true);
		beanFieldGroup.bind(authorComboBox, "author");
		authorComboBox.setNullSelectionAllowed(false);
		addComponent(authorComboBox);

		BeanItemContainer<PubDTO> pubs = new BeanItemContainer<>(PubDTO.class);
		pubs.addAll(pubFacade.getAllBeerPubs());

		ComboBox pubComboBox = new ComboBox("Hospoda", pubs);
		pubComboBox.setFilteringMode(FilteringMode.CONTAINS);
		beanFieldGroup.bind(pubComboBox, "pub");
		addComponent(pubComboBox);

		Button createBtn = new Button("Uložit", new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					beanFieldGroup.commit();

					if (dateField.getValue() == null) {
						getUI().addWindow(new ErrorWindow("Datum srazu musí být vyplněno"));
						return;
					}
					
					if (authorComboBox.getValue() == null) {
						getUI().addWindow(new ErrorWindow("Rezervující nesmí být prázdný"));
						return;
					}

					Long id = visitFacade.saveVisit(beanFieldGroup.getItemDataSource().getBean());
					onCreation(id);
					getUI().removeWindow(CreateVisitWindow.this);
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
		getUI().addWindow(new ConfirmWindow("Opravdu zrušit zakládání/úpravu srazu?") {
			private static final long serialVersionUID = -1263084559774811237L;

			@Override
			protected void onConfirm(ClickEvent event) {
				CreateVisitWindow.super.close();
			}
		});
	}

}
