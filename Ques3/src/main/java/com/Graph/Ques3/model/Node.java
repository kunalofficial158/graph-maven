package com.Graph.Ques3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private String id;
    private String name;
    private String parentId;
    private List<Node> children;

    public Node() {
        this.children = new ArrayList<>();
    }

    public Node(String id, String name, String parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = new ArrayList<>();
    }
    public Node(String id, String name, String parentId, List<Node> children)
    {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = children;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id) && Objects.equals(name, node.name) && Objects.equals(parentId, node.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentId);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", children=" + children +
                '}';
    }
}
