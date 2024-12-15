package com.Graph.Ques3.config;

import com.Graph.Ques3.model.Node;
import com.Graph.Ques3.model.Relationship;
import com.Graph.Ques3.service.GraphService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Configuration
public class Config {

    @Autowired
    private GraphService graphService;

    @EventListener(ContextRefreshedEvent.class)
    public void loadGraph() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Load nodes and relationships
        try (InputStream inputStream = getClass().getResourceAsStream("/graph-data.json")) {
            if (inputStream == null) {
                throw new IOException("graph-data.json not found in classpath");
            }
            Map<String, List<Map<String, Object>>> jsonData = mapper.readValue(inputStream, new TypeReference<Map<String, List<Map<String, Object>>>>() {});

            // Deserialize nodes
            List<Map<String, Object>> nodesList = jsonData.get("nodes");
            if (nodesList != null) {
                for (Map<String, Object> nodeMap : nodesList) {
                    Node node = mapper.convertValue(nodeMap, Node.class);
                    graphService.addNode(node);
                }
            }

            // Deserialize relationships
            List<Map<String, Object>> relationshipsList = jsonData.get("relationships");
            if (relationshipsList != null) {
                for (Map<String, Object> relationshipMap : relationshipsList) {
                    Relationship relationship = mapper.convertValue(relationshipMap, Relationship.class);
                    graphService.addRelationship(relationship);
                }
            }

        } catch (IOException e) {
            System.err.println("Error loading graph data from graph-data.json: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
