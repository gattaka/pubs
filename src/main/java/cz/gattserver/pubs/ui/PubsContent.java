package cz.gattserver.pubs.ui;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.ColumnGenerator;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

import cz.gattserver.pubs.facades.CommentFacade;
import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.facades.SecurityFacade;
import cz.gattserver.pubs.model.dto.CommentDTO;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.subwindows.CreatePubCommentWindow;
import cz.gattserver.pubs.subwindows.CreatePubWindow;
import cz.gattserver.pubs.util.StringToDateConverter;

public class PubsContent extends Content {

	private static final long serialVersionUID = -2446097146634308270L;

	@Autowired
	private SecurityFacade securityFacade;

	@Autowired
	private PubFacade pubFacade;
	
	@Autowired
	private CommentFacade commentFacade;

	private final FilterTable table = new FilterTable();
	private BeanContainer<Long, PubDTO> container;

	private VerticalLayout pubAndCommentsLayout;

	private void populateContainer() {
		container.removeAllItems();
		container.addAll(pubFacade.findAllPubs());
		sortTable();
	}

	private void sortTable() {
		table.sort(new Object[] { "name" }, new boolean[] { true });
	}

	private void showPubDetail(PubDTO p) {
		pubAndCommentsLayout.removeAllComponents();

		HorizontalLayout pubLayout = new HorizontalLayout();
		pubLayout.setSpacing(true);
		pubAndCommentsLayout.addComponent(pubLayout);

		// TODO z DB
		Embedded pubImage = new Embedded(null, new ThemeResource("img/no_foto.png"));
		pubImage.setWidth("256px");
		pubLayout.addComponent(pubImage);

		VerticalLayout pubDetails = new VerticalLayout();
		pubDetails.setSpacing(true);
		pubDetails.setWidth("100%");
		pubLayout.addComponent(pubDetails);
		pubLayout.setExpandRatio(pubDetails, 1);

		HorizontalLayout rankLayout = new HorizontalLayout();
		rankLayout.setSpacing(true);
		pubDetails.addComponent(rankLayout);

		Label nameLabel = new Label(p.getName());
		nameLabel.setStyleName("pub-name");
		nameLabel.setHeight("25px");
		rankLayout.addComponent(nameLabel);

		for (int i = 0; i < p.getRank(); i++) {
			Embedded rankStar = new Embedded(null, new ThemeResource("img/unetice_small.png"));
			rankLayout.addComponent(rankStar);
		}

		Label addressLabel = new Label(p.getAddress());
		addressLabel.setCaption("Adresa");
		pubDetails.addComponent(addressLabel);

		Component webAddressLink = createWebAddressLink(p);
		webAddressLink.setCaption("Webové stránky");
		pubDetails.addComponent(webAddressLink);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		Label lastVisitLabel = new Label(p.getLastVisit() == null ? "Zatím nenavštíveno" : dateFormat.format(p
				.getLastVisit()));
		lastVisitLabel.setCaption("Poslední návštěva");
		pubDetails.addComponent(lastVisitLabel);

		Label detailsLabel = new Label(p.getAddress());
		detailsLabel.setCaption("Popis hospody");
		pubDetails.addComponent(detailsLabel);

		// HorizontalLayout comments = new HorizontalLayout();
		// comments.setSpacing(true);

		if (securityFacade.getCurrentUser() != null) { 
			Button createBtn = new Button("Přidat komentář", new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					getUI().addWindow(new CreatePubCommentWindow(p) {
						@Override
						protected void onCreation(Long id) {
							CommentDTO commentDTO = commentFacade.findById(id);
						}
					});
				}
			});
			createBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/tags/plus_16.png"));
			pubAndCommentsLayout.addComponent(createBtn);
		}

		for (CommentDTO c : p.getComments()) {
			Label comment = new Label(c.getText());
			comment.setWidth(null);
			comment.setCaption(c.getAuthor().getName() + " " + "(" + dateFormat.format(c.getCreationDate()) + ")");
			pubAndCommentsLayout.addComponent(comment);
		}

	}

	private Component createWebAddressLink(PubDTO p) {
		if (p.getWebAddress() != null) {
			return new Link(p.getWebAddress(), new ExternalResource(p.getWebAddress()));
		} else {
			return new Label("Nemá webové stránky");
		}
	}

	public PubsContent(LayoutPage layoutPage) {
		super(layoutPage);

		/**
		 * Tabulka HW
		 */
		table.setSelectable(true);
		table.setImmediate(true);
		container = new BeanContainer<Long, PubDTO>(PubDTO.class);
		container.setBeanIdProperty("id");
		populateContainer();
		table.setContainerDataSource(container);

		table.setConverter("lastVisit", new StringToDateConverter());

		table.addGeneratedColumn("link", new ColumnGenerator() {
			private static final long serialVersionUID = -7036829480797154987L;

			@Override
			public Object generateCell(CustomTable source, Object itemId, Object columnId) {
				@SuppressWarnings("unchecked")
				BeanItem<PubDTO> item = (BeanItem<PubDTO>) table.getItem(itemId);
				return createWebAddressLink(item.getBean());
			}
		});

		table.addGeneratedColumn("rankIlustrated", new ColumnGenerator() {
			private static final long serialVersionUID = -7036829480797154987L;

			@Override
			public Object generateCell(CustomTable source, Object itemId, Object columnId) {

				@SuppressWarnings("unchecked")
				BeanItem<PubDTO> item = (BeanItem<PubDTO>) table.getItem(itemId);
				PubDTO p = item.getBean();

				HorizontalLayout rankLayout = new HorizontalLayout();
				rankLayout.setSpacing(true);

				for (int i = 0; i < p.getRank(); i++) {
					Embedded rankStar = new Embedded(null, new ThemeResource("img/unetice_micro.png"));
					rankLayout.addComponent(rankStar);
				}

				return rankLayout;
			}
		});

		table.setColumnHeader("rankIlustrated", "Hodnocení");
		table.setColumnHeader("name", "Název");
		table.setColumnHeader("address", "Adresa");
		table.setColumnHeader("lastVisit", "Poslední návštěva");
		table.setColumnHeader("link", "Webové stránky");

		table.setColumnWidth("rankIlustrated", 70);

		table.setVisibleColumns(new Object[] { "rankIlustrated", "name", "address", "lastVisit", "link" });
		table.setWidth("100%");

		table.setFilterBarVisible(true);
		// table.setFilterDecorator(new GrassFilterDecorator());
		table.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1187013531601185692L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (table.getValue() != null) {
					@SuppressWarnings("unchecked")
					BeanItem<PubDTO> item = (BeanItem<PubDTO>) table.getItem(table.getValue());
					showPubDetail(item.getBean());
				}
			}
		});

		addComponent(table);

		HorizontalLayout menu = new HorizontalLayout();
		menu.setSpacing(true);
		addComponent(menu);
		setComponentAlignment(menu, Alignment.TOP_CENTER);
		menu.setWidth("100%");

		if (securityFacade.getCurrentUser() != null) {
			Button createBtn = new Button("Založit novou hospodu", new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					getUI().addWindow(new CreatePubWindow());
				}
			});
			createBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/tags/plus_16.png"));
			menu.addComponent(createBtn);
		}

		// separator
		addComponent(new Label());

		pubAndCommentsLayout = new VerticalLayout();
		pubAndCommentsLayout.setSpacing(true);
		addComponent(pubAndCommentsLayout);
		pubAndCommentsLayout.setWidth("100%");

	}
}
