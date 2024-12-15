package com.Graph.Ques3.exception;

public class GraphException extends RuntimeException {
    public GraphException(String message) {
        super(message);
    }

    public static class NodeNotFoundException extends GraphException {
        public NodeNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidNodeException extends GraphException {
        public InvalidNodeException(String message) {
            super(message);
        }
    }

    public static class RelationshipNotFoundException extends GraphException {
        public RelationshipNotFoundException(String message) {
            super(message);
        }
    }

    // Add more custom exceptions as needed
}
