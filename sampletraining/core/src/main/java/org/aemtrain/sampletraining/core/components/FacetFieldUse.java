package org.aemtrain.sampletraining.core.components;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.google.gson.Gson;

public class FacetFieldUse extends WCMUsePojo {

	private List<String> filterSelected;
	private List<String> filterActionSelected;
	private static String searchFilterJsonMap;
	private static String filterActionJsonMap;

	public static String getFilterActionJsonMap() {
		return filterActionJsonMap;
	}

	public static void setFilterActionJsonMap(String filterActionJsonMap) {
		FacetFieldUse.filterActionJsonMap = filterActionJsonMap;
	}

	public String getSearchFilterJsonMap() {
		return searchFilterJsonMap;
	}

	@SuppressWarnings("static-access")
	public void setSearchFilterJsonMap(String searchFilterJsonMap) {
		this.searchFilterJsonMap = searchFilterJsonMap;
	}

	private static final String FILTER_SELECTED_ID = "filterSelectedId";
	private static final String FILTER_TITLE = "filterTitleLabel";
	private static final String FILTER_RESET = "filterResetLabel";
	private static final String FILTER_MORE = "filterMoreLabel";
	private static final String FILTER_CLEAR_ALL = "filterClearallLabel";

	private static final Logger LOG = LoggerFactory.getLogger(FacetFieldUse.class);

	@Override
	public void activate() throws Exception {

		filterSelected = new LinkedList<>();
		filterActionSelected  = new LinkedList<>();

		Node filterCompNode = getResource().adaptTo(Node.class);

		if (filterCompNode.hasNode("filterSelected")) {
			Node node = filterCompNode.getNode("filterSelected");
			LOG.debug("Component Mltifield node name : {} ", node.getName());
			NodeIterator nodes = node.getNodes();
			Gson json = new Gson();
			LOG.debug("Nodes child name : {} ", nodes.hasNext());
			while (nodes.hasNext()) {
				Map<String, String> filterData = new HashMap<>();
				Node child = (Node) nodes.nextNode();
				LOG.debug("Child node name : {}", child.getName());
				String filterSelectedName = child.getProperty(FILTER_SELECTED_ID).getString();
				String filterTitle = child.getProperty(FILTER_TITLE).getString();
				filterData.put("facet-id", filterSelectedName);
				filterData.put("facet-title", filterTitle);
				String jsonStr = json.toJson(filterData);
				LOG.debug("Node properties map : {} ", jsonStr);
				filterSelected.add(jsonStr);
			}
			Map<String, String> filterAction = new HashMap<>();
			filterAction.put("ResetLabel", filterCompNode.getProperty(FILTER_RESET).getString());
			filterAction.put("MoreLabel", filterCompNode.getProperty(FILTER_MORE).getString());
			filterAction.put("ClearAllLabel", filterCompNode.getProperty(FILTER_CLEAR_ALL).getString());
			String jsonStr2 = json.toJson(filterAction);
			LOG.debug("Node action map : {} ", jsonStr2);
			filterActionSelected.add(jsonStr2);
			LOG.debug("Final Map : {} ", filterSelected);
			LOG.debug("Actions Map : {} ", filterActionSelected);
			setFilterSelected(filterSelected);
			setFilterActionJsonMap(filterActionSelected.toString());
			setSearchFilterJsonMap(filterSelected.toString());
		}

	}

	public List<String> getFilterSelected() {
		return filterSelected;
	}

	public void setFilterSelected(List<String> filterSelected) {
		this.filterSelected = filterSelected;
	}

}