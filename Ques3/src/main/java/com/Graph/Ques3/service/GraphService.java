package com.Graph.Ques3.service;

import com.Graph.Ques3.model.Node;
import com.Graph.Ques3.model.Relationship;
import com.Graph.Ques3.exception.GraphException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphService {
    private final Map<String, Node> nodes = new LinkedHashMap<>();

    // Add a new node to the graph
    public void addNode(Node node) {
        if (node == null || node.getId() == null || node.getName() == null) {
            throw new GraphException.InvalidNodeException("Invalid node data");
        }
        if (nodes.containsKey(node.getId())) {
            return; // Node already exists, no need to add again
        }
        nodes.put(node.getId(), node);
        if (node.getParentId() != null && nodes.containsKey(node.getParentId())) {
            Node parent = nodes.get(node.getParentId());
            if (parent.getChildren().stream().noneMatch(child -> child.getId().equals(node.getId()))) {
                parent.getChildren().add(node);
            }
        }
    }

    // Add a relationship between two nodes (parent-child relationship)
    public void addRelationship(Relationship relationship) {
        Node parent = getNode(relationship.getParentId());
        Node child = getNode(relationship.getChildId());
        if (parent != null && child != null) {
            if (!parent.getChildren().contains(child)) {
                parent.getChildren().add(child);
                child.setParentId(parent.getId()); // Update child's parent ID
            }
        } else {
            throw new GraphException.RelationshipNotFoundException("Invalid parent or child for relationship");
        }
    }

    // Retrieve a node by its ID
    public Node getNode(String id) {
        Node node = nodes.get(id);
        if (node == null) {
            throw new GraphException.NodeNotFoundException("Node with id " + id + " not found");
        }
        return node;
    }

    // Get the total number of nodes in the graph
    public int getTotalNodes() {
        return nodes.size();
    }

    // Get all siblings of a given node (nodes with the same parent)
    public List<Node> getSiblings(String nodeId) {
        Node node = getNode(nodeId);
        if (node.getParentId() == null) {
            return Collections.emptyList();
        }

        Node parent = getNode(node.getParentId());
        List<Node> siblings = new ArrayList<>(parent.getChildren());
        siblings.removeIf(n -> n.getId().equals(nodeId));
        return siblings;
    }

//     Get all nodes in the graph
    public List<Node> getAllNodes() {
        return new ArrayList<>((nodes.values()));
    }


    // Update an existing node in the graph
    public void updateNode(String id, Node updatedNode) {
        Node existingNode = getNode(id);

        if (updatedNode.getParentId() != null && !updatedNode.getParentId().equals(existingNode.getParentId())) {
            // Remove the node from the old parent's children list
            if (existingNode.getParentId() != null) {
                Node oldParent = getNode(existingNode.getParentId());
                oldParent.getChildren().remove(existingNode);
            }

            // Add the node to the new parent's children list
            if (nodes.containsKey(updatedNode.getParentId())) {
                Node newParent = getNode(updatedNode.getParentId());
                newParent.getChildren().add(existingNode);
            } else {
                throw new GraphException.NodeNotFoundException("New parent node with id " + updatedNode.getParentId() + " not found");
            }
        }

        // Update node details
        existingNode.setName(updatedNode.getName());
        existingNode.setParentId(updatedNode.getParentId());
    }

    // Delete a node and remove it from its parent's children list
    public void deleteNode(String id) {
        Node node = getNode(id);
        nodes.remove(id);
        if (node.getParentId() != null) {
            Node parent = getNode(node.getParentId());
            parent.getChildren().remove(node);  // Remove from parent's children list
        }
        // Optionally: Handle removal of children if needed
    }

    // Find a path from a start node to an end node (Breadth-First Search)
    public List<Node> findPath(String startNodeId, String endNodeId) {
        List<Node> path = new ArrayList<>();
        if (!nodes.containsKey(startNodeId) || !nodes.containsKey(endNodeId)) {
            return path;
        }

        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(startNodeId);
        parentMap.put(startNodeId, null); // Starting node has no parent

        while (!queue.isEmpty()) {
            String currentNodeId = queue.poll();
            Node currentNode = getNode(currentNodeId);

            if (currentNodeId.equals(endNodeId)) {
                // Path found, reconstruct the path
                while (currentNodeId != null) {
                    Node node = getNode(currentNodeId);
                    path.add(0, new Node(currentNodeId, node.getName(), node.getParentId(), new ArrayList<>())); // Adding node to path
                    currentNodeId = parentMap.get(currentNodeId);
                }
                return path;
            }

            for (Node child : currentNode.getChildren()) {
                if (!parentMap.containsKey(child.getId())) {
                    queue.add(child.getId());
                    parentMap.put(child.getId(), currentNodeId);
                }
            }
        }

        return path; // No path found
    }

    // Calculate the depth of a node in the graph
    public int calculateDepth(String id) {
        Node node = getNode(id);
        int depth = 0;
        while (node.getParentId() != null) {
            node = getNode(node.getParentId());
            depth++;
        }
        return depth;
    }

    // Find the common ancestor of two nodes
    public Node findCommonAncestor(String nodeId1, String nodeId2) {
        if (!nodes.containsKey(nodeId1) || !nodes.containsKey(nodeId2)) {
            return null;
        }

        // Collect ancestors of nodeId1
        Set<String> ancestors1 = new HashSet<>();
        String currentNodeId = nodeId1;

        while (currentNodeId != null) {
            ancestors1.add(currentNodeId);
            currentNodeId = getNode(currentNodeId).getParentId();
        }

        // Find the first common ancestor with nodeId2
        currentNodeId = nodeId2;

        while (currentNodeId != null) {
            if (ancestors1.contains(currentNodeId)) {
                Node commonAncestor = getNode(currentNodeId);
                // Create a new Node object without children
                return new Node(commonAncestor.getId(), commonAncestor.getName(), commonAncestor.getParentId(), new ArrayList<>());
            }
            currentNodeId = getNode(currentNodeId).getParentId();
        }

        return null; // No common ancestor found
    }
}
