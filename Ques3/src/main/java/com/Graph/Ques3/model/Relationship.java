package com.Graph.Ques3.model;

import java.util.Objects;

public class Relationship {
    private String parentId;
    private String childId;

    public Relationship() {
    }

    public Relationship(String parentId, String childId) {
        this.parentId = parentId;
        this.childId = childId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return Objects.equals(parentId, that.parentId) && Objects.equals(childId, that.childId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentId, childId);
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "parentId='" + parentId + '\'' +
                ", childId='" + childId + '\'' +
                '}';
    }
}
