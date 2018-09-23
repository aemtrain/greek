package org.aemtrain.sampletraining.core.components;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;

public class MultiFieldUse extends WCMUsePojo {

	private Map<String , Map<String,String>> filterSelected;
	
	private static final String FILTER_SELECTED = "filterSelected";
	private static final String FILTER_TITLE = "filterTitleLabel";
	private static final String FILTER_RESET = "filterResetLabel";
	private static final String FILTER_MORE = "filterMoreLabel";
	
	private static final Logger LOG = LoggerFactory.getLogger(MultiFieldUse.class);
	
	@Override
	public void activate() throws Exception {
		
		filterSelected = new LinkedHashMap<>();

		Node filterCompNode = getResource().adaptTo(Node.class);
		
		if (filterCompNode.hasNode("filterSelected")) {
			Node node = filterCompNode.getNode("filterSelected");
			LOG.info("Component Mltifield node name : {} ",node.getName());
			NodeIterator nodes = node.getNodes();
			LOG.info("Nodes child name : {} ",nodes.hasNext());
			while (nodes.hasNext()) {
				Map<String,String> filterProperties = new HashMap<>();
				Node child = (Node) nodes.nextNode();
				LOG.info("Child node name : {}",child.getName());
				String filterSelectedName = child.getProperty(FILTER_SELECTED).getString();
				String filterTitle = child.getProperty(FILTER_TITLE).getString();
				String filterReset = child.getProperty(FILTER_RESET).getString();
				String filterMore = child.getProperty(FILTER_MORE).getString();
				filterProperties.put(FILTER_SELECTED, filterSelectedName);
				filterProperties.put(FILTER_TITLE, filterTitle);
				filterProperties.put(FILTER_RESET, filterReset);
				filterProperties.put(FILTER_MORE, filterMore);
				LOG.info("Node properties map : {} ",filterProperties);
				filterSelected.put(filterSelectedName, filterProperties);
			}
			LOG.info("Final Map : {} ",filterSelected);
			setFilterSelected(filterSelected);
		}

	}

	public Map<String, Map<String, String>> getFilterSelected() {
		return filterSelected;
	}

	public void setFilterSelected(Map<String, Map<String, String>> filterSelected) {
		this.filterSelected = filterSelected;
	}
	
	

}
