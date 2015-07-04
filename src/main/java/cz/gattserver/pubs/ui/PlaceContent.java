package cz.gattserver.pubs.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.tepi.filtertable.FilterTable;
import org.vaadin.teemu.ratingstars.RatingStars;
import org.vaadin.tokenfield.TokenField;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.CustomTable.ColumnGenerator;
import com.vaadin.ui.themes.BaseTheme;
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
import cz.gattserver.pubs.model.dto.PubTagDTO;
import cz.gattserver.pubs.subwindows.CreatePubCommentWindow;
import cz.gattserver.pubs.subwindows.CreatePubWindow;
import cz.gattserver.pubs.subwindows.RankPubWindow;
import cz.gattserver.pubs.util.StringToDateConverter;
import cz.gattserver.web.common.URLPathAnalyzer;

public abstract class PlaceContent extends Content {

	private static final long serialVersionUID = -2446097146634308270L;

	private static final String MAPS_QUERY_PREFIX = "http://maps.google.com/?q=";

	@Autowired
	protected SecurityFacade securityFacade;

	@Autowired
	protected PubFacade pubFacade;

	@Autowired
	protected CommentFacade commentFacade;

	private BeanContainer<Long, PubDTO> container;

	private final FilterTable table = new FilterTable();

	private VerticalLayout pubAndCommentsLayout;
	private VerticalLayout commentsLayout;

	private Button editBtn;
	private Button rankBtn;

	protected abstract Collection<PubDTO> populateTable();

	protected abstract String getContentPath();

	protected abstract String getSectionPath();

	protected abstract String createNewItemCaption();

	protected abstract String modifyItemCaption();

	protected abstract CreatePubWindow openCreatePubWindow(PubDTO pubDTO,
			CreatePubWindow.OnCreationListener onCreationListener);

	private void populateContainer() {
		container = new BeanContainer<Long, PubDTO>(PubDTO.class);
		container.setBeanIdProperty("id");
		container.addAll(populateTable());
		table.setContainerDataSource(container);
		table.setConverter("lastVisit", new StringToDateConverter());

		table.setColumnHeader("rankIlustrated", "Hodnocení");
		table.setColumnHeader("name", "Název");
		table.setColumnHeader("address", "Adresa");
		table.setColumnHeader("link", "Webové stránky");

		table.setColumnWidth("rankIlustrated", 70);

		table.setVisibleColumns(new Object[] { "rankIlustrated", "name", "address", "link" });
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
					layoutPage.getWebRequest().updateURL(getSectionPath(), getContentPath(), item.getBean().getName());
				}
				if (editBtn != null)
					editBtn.setEnabled(selected);
				if (rankBtn != null)
					rankBtn.setEnabled(selected);
			}
		});
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

		Embedded pubImage;
		if (p.getImage() == null) {
			pubImage = new Embedded(null, new ThemeResource("img/no_foto.png"));
		} else {
			pubImage = new Embedded(null, new StreamResource(new StreamSource() {
				private static final long serialVersionUID = 414452413648278444L;

				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(p.getImage());
				}
			}, p.getName() + ".jpg"));
		}
		// pubImage.setWidth("256px");
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

		RatingStars ratingStars = new RatingStars();
		ratingStars.setAnimated(false);
		ratingStars.setEnabled(false);
		ratingStars.setValue((double) (p.getRank()));
		rankLayout.addComponent(ratingStars);

		Link addressLink = new Link(p.getAddress(), new ExternalResource(MAPS_QUERY_PREFIX + p.getAddress()));
		addressLink.setTargetName("_blank");
		pubDetails.addComponent(addressLink);

		Component webAddressLink = createWebAddressLink(p);
		pubDetails.addComponent(webAddressLink);

		HorizontalLayout tagsLayout = new HorizontalLayout();
		tagsLayout.addStyleName(TokenField.STYLE_TOKENFIELD);
		tagsLayout.setSpacing(true);
		for (PubTagDTO tag : p.getTags()) {
			Button btn = new Button(tag.getName());
			btn.setStyleName(BaseTheme.BUTTON_LINK);
			tagsLayout.addComponent(btn);
		}
		pubDetails.addComponent(tagsLayout);

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

	public PlaceContent(MenuLayoutPage layoutPage) {
		super(layoutPage);

		table.setSelectable(true);
		table.setImmediate(true);

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

				RatingStars ratingStars = new RatingStars();
				ratingStars.setAnimated(false);
				ratingStars.setStyleName("tiny");
				ratingStars.setEnabled(false);
				ratingStars.setValue((double) (p.getRank()));

				return ratingStars;
			}
		});

		populateContainer();

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

			Button createBtn = new Button(createNewItemCaption(), new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					getUI().addWindow(openCreatePubWindow(new PubDTO(), new CreatePubWindow.OnCreationListener() {
						@Override
						public void onCreation(Long id) {
							PubDTO newPub = pubFacade.getById(id);
							container.addBean(newPub);
							sortTable();
						}
					}));
				}
			});
			createBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/tags/plus_16.png"));
			btnLayout.addComponent(createBtn);

			editBtn = new Button(modifyItemCaption(), new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					@SuppressWarnings("unchecked")
					BeanItem<PubDTO> beanItem = (BeanItem<PubDTO>) table.getItem(table.getValue());
					PubDTO item = beanItem.getBean();
					getUI().addWindow(openCreatePubWindow(item, new CreatePubWindow.OnCreationListener() {
						@Override
						public void onCreation(Long id) {
							PubDTO newPub = pubFacade.getById(id);
							populateContainer();
							showPubDetail(newPub);
							sortTable();
						}
					}));
				}
			});
			editBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/tags/pencil_16.png"));
			editBtn.setEnabled(false);
			btnLayout.addComponent(editBtn);

			rankBtn = new Button("Přidat hodnocení", new Button.ClickListener() {
				private static final long serialVersionUID = 2071604101486581247L;

				@Override
				public void buttonClick(ClickEvent event) {
					@SuppressWarnings("unchecked")
					BeanItem<PubDTO> beanItem = (BeanItem<PubDTO>) table.getItem(table.getValue());
					PubDTO item = beanItem.getBean();
					getUI().addWindow(new RankPubWindow(item) {
						private static final long serialVersionUID = -7533537841540613862L;

						@Override
						protected void onCreation(Long id) {
							PubDTO newPub = pubFacade.getById(id);
							populateContainer();
							showPubDetail(newPub);
							sortTable();
						}
					});
				}
			});
			rankBtn.setIcon((com.vaadin.server.Resource) new ThemeResource("img/tags/statistics_16.png"));
			rankBtn.setEnabled(false);
			btnLayout.addComponent(rankBtn);

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
