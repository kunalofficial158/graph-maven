package com.Graph.Ques3.controller;

import com.Graph.Ques3.model.Relationship;
import com.Graph.Ques3.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relationships")
public class RelationshipController {

    @Autowired
    private GraphService graphService;

    @PostMapping
    public void addRelationship(@RequestBody Relationship relationship) {
        graphService.addRelationship(relationship);
    }
}
