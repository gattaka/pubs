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
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import cz.gattserver.pubs.facades.CommentFacade;
import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.facades.SecurityFacade;
import cz.gattserver.pubs.model.dto.CommentDTO;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.subwindows.CreatePubCommentWindow;
import cz.gattserver.pubs.subwindows.CreatePubWindow;
import cz.gattserver.pubs.util.StringToDateConverter;
import cz.gattserver.web.common.URLPathAnalyzer;

public class PubsContent extends Content {

	private static final long serialVersionUID = -2446097146634308270L;

	private static final String MAPS_QUERY_PREFIX = "http://maps.google.com/?q=";
	public static final String CONTENT_PATH = "pubs";

	@Autowired
	private SecurityFacade securityFacade;

	@Autowired
	private PubFacade pubFacade;

	@Autowired
	private CommentFacade commentFacade;

	private final FilterTable table = new FilterTable();
	private BeanContainer<Long, PubDTO> container;

	private VerticalLayout pubAndCommentsLayout;
	private VerticalLayout commentsLayout;

	private Button editBtn;

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

		Link addressLink = new Link(p.getAddress(), new ExternalResource(MAPS_QUERY_PREFIX + p.getAddress()));
		addressLink.setTargetName("_blank");
		pubDetails.addComponent(addressLink);

		Component webAddressLink = createWebAddressLink(p);
		pubDetails.addComponent(webAddressLink);

		// Tohle asi nakonec nemá moc víznam, protože když se zapomene ten sraz zrušit a pak se půjde někam jinam
		// apod... je to takové zbytečné -- dá se to zjistit z přehledu
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		// Label lastVisitLabel = new Label(p.getLastVisit() == null ? "Zatím nenavštíveno" : dateFormat.format(p
		// .getLastVisit()));
		// lastVisitLabel.setCaption("Poslední návštěva");
		// pubDetails.addComponent(lastVisitLabel);

		Label detailsLabel = new Label(p.getDescription());
		detailsLabel.setCaption("Popis hospody");
		pubDetails.addComponent(detailsLabel);

		if (securityFacade.getCurrentUser() != null) {
			Button createBtn = new Button("Přidat komentář", new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					getUI().addWindow(new CreatePubCommentWindow(p) {
						private static final long serialVersionUID = -452606461049128450L;

						@Override
						protected void onCreation(Long id) {
							CommentDTO c = commentFacade.getById(id);
							p.getComments().add(c);
							addComment(c, p);
						}
					});
				}
			});
			createBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/tags/plus_16.png"));
			pubAndCommentsLayout.addComponent(createBtn);
		}

		commentsLayout = new VerticalLayout();
		commentsLayout.setSpacing(true);
		pubAndCommentsLayout.addComponent(commentsLayout);

		for (CommentDTO c : p.getComments()) {
			addComment(c, p);
		}

	}

	private Panel createCommentPanel(CommentDTO c) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		Label comment = new Label(c.getText());
		Panel panel = new Panel();
		String upravenoPart = c.getModificationDate() != null ? "upraveno: "
				+ dateFormat.format(c.getModificationDate()) + " " : "";
		panel.setCaption(c.getAuthor().getName() + " " + "(" + upravenoPart + "přidáno: "
				+ dateFormat.format(c.getCreationDate()) + ")");
		panel.setContent(comment);
		return panel;
	}

	private void addComment(CommentDTO c, final PubDTO p) {
		HorizontalLayout commentLineLayout = new HorizontalLayout();
		commentLineLayout.setSpacing(true);

		// musí být v panelu, aby se mohl roztahovat i dolů
		final Panel commentPanel = createCommentPanel(c);
		commentLineLayout.addComponent(commentPanel);

		// jde o můj komentář?
		if (c.getAuthor().equals(securityFacade.getCurrentUser())) {
			Button editBtn = new Button("Upravit komentář", new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					getUI().addWindow(new CreatePubCommentWindow(p, c) {
						private static final long serialVersionUID = -452606461049128450L;

						@Override
						protected void onCreation(Long id) {
							CommentDTO updatedComment = commentFacade.getById(id);
							commentLineLayout.replaceComponent(commentPanel, createCommentPanel(updatedComment));
						}
					});
				}
			});
			editBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/tags/pencil_16.png"));
			commentLineLayout.addComponent(editBtn);
			commentLineLayout.setComponentAlignment(editBtn, Alignment.BOTTOM_RIGHT);
		}

		commentLineLayout.setExpandRatio(commentPanel, 1);
		commentLineLayout.setWidth("100%");
		commentsLayout.addComponentAsFirst(commentLineLayout);
	}

	private Component createWebAddressLink(PubDTO p) {
		if (p.getWebAddress() != null) {
			Link link = new Link(p.getWebAddress(), new ExternalResource(p.getWebAddress()));
			link.setTargetName("_blank");
			return link;
		} else {
			return new Label("Nemá webové stránky");
		}
	}

	public PubsContent(LayoutPage layoutPage) {
		super(layoutPage);

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
		table.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1187013531601185692L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				boolean selected = table.getValue() != null;
				if (selected) {
					@SuppressWarnings("unchecked")
					BeanItem<PubDTO> item = (BeanItem<PubDTO>) table.getItem(table.getValue());
					showPubDetail(item.getBean());
					layoutPage.getWebRequest().updateURL(CONTENT_PATH + "/" + item.getBean().getName());
				}
				if (editBtn != null)
					editBtn.setEnabled(selected);
			}
		});

		URLPathAnalyzer analyzer = layoutPage.getWebRequest().getAnalyzer();

		addComponent(table);
		sortTable();

		HorizontalLayout menu = new HorizontalLayout();
		menu.setSpacing(true);
		addComponent(menu);
		setComponentAlignment(menu, Alignment.TOP_CENTER);
		menu.setWidth("100%");

		if (securityFacade.getCurrentUser() != null) {
			HorizontalLayout btnLayout = new HorizontalLayout();
			btnLayout.setSpacing(true);
			menu.addComponent(btnLayout);

			Button createBtn = new Button("Založit novou hospodu", new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					getUI().addWindow(new CreatePubWindow() {
						private static final long serialVersionUID = -7533537841540613862L;

						@Override
						protected void onCreation(Long id) {
							PubDTO newPub = pubFacade.getById(id);
							container.addBean(newPub);
							sortTable();
						}
					});
				}
			});
			createBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/tags/plus_16.png"));
			btnLayout.addComponent(createBtn);

			editBtn = new Button("Uprav hospodu", new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					@SuppressWarnings("unchecked")
					BeanItem<PubDTO> beanItem = (BeanItem<PubDTO>) table.getItem(table.getValue());
					PubDTO item = beanItem.getBean();
					getUI().addWindow(new CreatePubWindow(item) {
						private static final long serialVersionUID = -7533537841540613862L;

						@Override
						protected void onCreation(Long id) {
							PubDTO newPub = pubFacade.getById(id);
							container.removeItem(beanItem);
							container.addBean(newPub);
							showPubDetail(newPub);
							sortTable();
						}
					});
				}
			});
			editBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/tags/pencil_16.png"));
			editBtn.setEnabled(false);
			btnLayout.addComponent(editBtn);
		}

		// separator
		addComponent(new Label());

		pubAndCommentsLayout = new VerticalLayout();
		pubAndCommentsLayout.setSpacing(true);
		addComponent(pubAndCommentsLayout);
		pubAndCommentsLayout.setWidth("100%");

		// pokud nebyla cesta prázná, pak proveď posuv
		if (analyzer.getCurrentPathToken() != null) {
			String pubName = analyzer.getCurrentPathToken();
			PubDTO pubDTO = pubFacade.getByName(pubName);
			if (pubDTO != null)
				table.select(pubDTO.getId());
			analyzer.shift();
		}

	}
}
