package cz.gattserver.pubs.ui;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table.ColumnGenerator;

import cz.gattserver.pubs.facades.SecurityFacade;
import cz.gattserver.pubs.facades.VisitFacade;
import cz.gattserver.pubs.model.dto.VisitDTO;
import cz.gattserver.pubs.subwindows.CreateVisitWindow;
import cz.gattserver.pubs.ui.beer.BeerPubsContent;
import cz.gattserver.pubs.util.StringToDateConverter;

public class VisitsContent extends Content {

	private static final long serialVersionUID = -2446097146634308270L;

	public static final String CONTENT_PATH = "visits";
	private static final String VISIT_DATE_FORMAT = "dd.MM.yyyy HH:mm";

	@Autowired
	private VisitFacade visitFacade;

	@Autowired
	private SecurityFacade securityFacade;

	private Label nextVisitDate;
	private Link nextVisitPub;
	private Label nextVisitAuthor;
	private Table table;
	private Button editBtn;

	private VisitDTO nextVisit;

	private ExternalResource createPubLinkResource(VisitDTO visitDTO) {
		return new ExternalResource(layoutPage.getWebRequest().getPageURL(
				BeerPubsContent.PATH + "/" + visitDTO.getPub().getName()));
	}

	private void refreshVisitsOverview() {
		List<VisitDTO> visitDTOs = visitFacade.getAllVisits();
		nextVisit = visitDTOs.size() >= 1 ? visitDTOs.remove(0) : null;

		SimpleDateFormat dateFormat = new SimpleDateFormat(VISIT_DATE_FORMAT);

		table.setContainerDataSource(new BeanItemContainer<VisitDTO>(VisitDTO.class, visitDTOs));
		table.setVisibleColumns(new Object[] { "date", "link", "author" });
		table.setColumnHeaders("Kdy", "Kde", "Rezervováno");

		if (nextVisit != null) {
			nextVisitDate.setValue(nextVisit.getDate() == null ? "Datum nebylo zatím stanoveno" : dateFormat
					.format(nextVisit.getDate()));
			nextVisitPub.setResource(createPubLinkResource(nextVisit));
			nextVisitPub.setCaption(nextVisit.getPub().getName());
			nextVisitAuthor.setValue(nextVisit.getAuthor().getName());

			if (editBtn != null)
				editBtn.setEnabled(true);
		}
	}

	public VisitsContent(MenuLayoutPage layoutPage) {
		super(layoutPage);
		addComponent(new Label("<h1>Ahoj!</h1>", ContentMode.HTML));
		addComponent(new Label(
				"Je to trochu overkill, ale \"hec\" je \"hec\" ;) -- tady máte vaší <strong>Znalostní bázi hospod</strong>",
				ContentMode.HTML));

		addComponent(new Label("<h1>Příští sraz</h1>", ContentMode.HTML));

		nextVisitDate = new Label();
		nextVisitPub = new Link();
		nextVisitAuthor = new Label();

		nextVisitDate.setImmediate(true);
		nextVisitPub.setImmediate(true);
		nextVisitAuthor.setImmediate(true);

		nextVisitDate.setWidth("100%");
		nextVisitPub.setWidth("100%");
		nextVisitAuthor.setWidth("100%");

		GridLayout nextVisitLayout = new GridLayout(2, 3);
		nextVisitLayout.setSpacing(true);

		nextVisitLayout.addComponent(new Label("<strong>Kdy:</strong> ", ContentMode.HTML));
		nextVisitLayout.addComponent(nextVisitDate);
		nextVisitLayout.addComponent(new Label("<strong>Místo:</strong> ", ContentMode.HTML));
		nextVisitLayout.addComponent(nextVisitPub);
		nextVisitLayout.addComponent(new Label("<strong>Zařizuje:</strong> ", ContentMode.HTML));
		nextVisitLayout.addComponent(nextVisitAuthor);

		addComponent(nextVisitLayout);

		if (securityFacade.getCurrentUser() != null) {
			HorizontalLayout btnLayout = new HorizontalLayout();
			btnLayout.setSpacing(true);
			addComponent(btnLayout);

			Button createBtn = new Button("Naplánovat příští sraz", new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					getUI().addWindow(new CreateVisitWindow() {
						private static final long serialVersionUID = -7533537841540613862L;

						@Override
						protected void onCreation(Long id) {
							refreshVisitsOverview();
						}
					});
				}
			});
			createBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/tags/plus_16.png"));
			btnLayout.addComponent(createBtn);

			editBtn = new Button("Upravit sraz", new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					getUI().addWindow(new CreateVisitWindow(nextVisit) {
						private static final long serialVersionUID = -7533537841540613862L;

						@Override
						protected void onCreation(Long id) {
							refreshVisitsOverview();
						}
					});
				}
			});
			editBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/tags/pencil_16.png"));
			editBtn.setEnabled(false);
			btnLayout.addComponent(editBtn);
		}

		addComponent(new Label("<h1>Historie srazů</h1>", ContentMode.HTML));
		table = new Table(null, new BeanItemContainer<VisitDTO>(VisitDTO.class));
		table.setImmediate(true);
		table.addGeneratedColumn("link", new ColumnGenerator() {
			private static final long serialVersionUID = -7036829480797154987L;

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				@SuppressWarnings("unchecked")
				VisitDTO item = ((BeanItem<VisitDTO>) table.getItem(itemId)).getBean();
				return new Link(item.getPub().getName(), createPubLinkResource(item));
			}
		});

		table.setWidth("100%");
		table.setConverter("date", new StringToDateConverter(VISIT_DATE_FORMAT));

		addComponent(table);

		refreshVisitsOverview();

	}

}
