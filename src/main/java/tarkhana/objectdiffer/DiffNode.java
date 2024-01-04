package tarkhana.objectdiffer;

import java.util.List;

public class DiffNode {
    private String name;

    private DiffState state;

    private Object oldValue;

    private Object newValue;

    private List<DiffNode> children;

    public String getName() {
        return name;
    }

    public DiffState getState() {
        return state;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public List<DiffNode> getChildren() {
        return children;
    }

    public static final class DiffNodeBuilder {

        private String name;

        private DiffState state;

        private Object oldValue;

        private Object newValue;

        private List<DiffNode> children;

        public DiffNode build() {
            DiffNode diffNode = new DiffNode();
            diffNode.name = name;
            diffNode.state = state;
            diffNode.oldValue = oldValue;
            diffNode.newValue = newValue;
            diffNode.children = children;
            return diffNode;
        }

        public DiffNodeBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public DiffNodeBuilder withState(DiffState state) {
            this.state = state;
            return this;
        }

        public DiffNodeBuilder withOldValue(Object oldValue) {
            this.oldValue = oldValue;
            return this;
        }

        public DiffNodeBuilder withNewValue(Object newValue) {
            this.newValue = newValue;
            return this;
        }

        public DiffNodeBuilder withChildren(List<DiffNode> children) {
            this.children = children;
            return this;
        }
    }
}
