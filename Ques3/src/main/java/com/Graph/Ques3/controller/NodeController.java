package com.Graph.Ques3.controller;

import com.Graph.Ques3.model.Node;
import com.Graph.Ques3.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nodes")
public class NodeController {

    @Autowired
    private GraphService graphService;

    @PostMapping("/add")
    public void addNode(@RequestBody Node node) {
        graphService.addNode(node);
    }

    @GetMapping("/{id}")
    public Node getNode(@PathVariable String id) {
        return graphService.getNode(id);
    }

    @GetMapping("/count")
    public int getTotalNodes() {
        return graphService.getTotalNodes();
    }

    @GetMapping("/{id}/siblings")
    public List<Node> getSiblings(@PathVariable String id) {
        return graphService.getSiblings(id);
    }

    @GetMapping
    public List<Node> getAllNodes() {
        return graphService.getAllNodes();
    }

    @PutMapping("/{id}")
    public void updateNode(@PathVariable String id, @RequestBody Node updatedNode) {
        graphService.updateNode(id, updatedNode);
    }

    @DeleteMapping("/{id}")
    public void deleteNode(@PathVariable String id) {
        graphService.deleteNode(id);
    }

    @GetMapping("/path")
    public List<String> findPath(@RequestParam String startNodeId, @RequestParam String endNodeId) {
        return graphService.findPath(startNodeId, endNodeId);
    }

    @GetMapping("/{id}/depth")
    public int calculateDepth(@PathVariable String id) {
        return graphService.calculateDepth(id);
    }

    @GetMapping("/common-ancestor")
    public Node findCommonAncestor(@RequestParam String nodeId1, @RequestParam String nodeId2) {
        return graphService.findCommonAncestor(nodeId1, nodeId2);
    }
}
