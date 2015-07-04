package cz.gattserver.pubs.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.model.dto.PubTagDTO;
import cz.gattserver.pubs.ui.Content;
import cz.gattserver.pubs.ui.MenuLayoutPage;

public abstract class ItemsContent extends Content {

	private static final long serialVersionUID = -2446097146634308270L;

	public static final String PATH = "items";

	@Autowired
	private PubFacade pubFacade;

	private VerticalLayout pubsLayout;

	protected abstract String getContentPath();

	protected abstract String getSectionPath();

	protected abstract String getTableCaption();

	protected abstract String getCountCaption();

	protected abstract List<PubTagDTO> getTags();

	private ExternalResource createPubLinkResource(PubDTO pubDTO) {
		return new ExternalResource(layoutPage.getWebRequest().getPageURL(getSectionPath(), getContentPath(),
				pubDTO.getName()));
	}

	public ItemsContent(MenuLayoutPage layoutPage) {
		super(layoutPage);

		Table table = new Table(getTableCaption(), new BeanItemContainer<PubTagDTO>(PubTagDTO.class, getTags()));
		table.setWidth("100%");
		table.setImmediate(true);
		table.setVisibleColumns(new Object[] { "name", "pubsCount" });
		table.setColumnHeaders(new String[] { "Název", getCountCaption() });
		table.setSelectable(true);
		addComponent(table);

		table.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 3959085792973025880L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				pubsLayout.removeAllComponents();
				if (table.getValue() != null) {
					@SuppressWarnings("unchecked")
					BeanItem<PubTagDTO> item = (BeanItem<PubTagDTO>) table.getItem(table.getValue());

					for (PubDTO pub : item.getBean().getPubs()) {
						pubsLayout.addComponent(new Link(pub.getName(), createPubLinkResource(pub)));
					}
				}
			}
		});

		pubsLayout = new VerticalLayout();
		pubsLayout.setSpacing(true);
		addComponent(pubsLayout);

	}
}
