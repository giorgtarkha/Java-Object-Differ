package tarkhana.objectdiffer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ObjectDifferTest {

    @Nested
    class CreatedObject {

        @Test
        public void primitive() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            int x = 5;
            DiffNode result = objectDiffer.getDiff(null, x);

            assertSimpleCreated(x, result);
        }

        @Test
        public void string() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            String x = "test";
            DiffNode result = objectDiffer.getDiff(null, x);

            assertSimpleCreated(x, result);
        }

        @Test
        public void enumt() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            TestEnum x = TestEnum.TEST1;
            DiffNode result = objectDiffer.getDiff(null, x);

            assertSimpleCreated(x, result);
        }

        private <T> void assertSimpleCreated(T value, DiffNode result) {
            assertEquals(result.getName(), "root");
            assertEquals(result.getState(), DiffState.CREATED);
            assertEquals(result.getNewValue(), value);
            assertNull(result.getOldValue());
            assertEquals(result.getChildren().size(), 0);
        }
    }

    @Nested
    class RemovedObject {

        @Test
        public void primitive() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            int x = 5;
            DiffNode result = objectDiffer.getDiff(x, null);

            assertSimpleRemoved(x, result);
        }

        @Test
        public void string() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            String x = "test";
            DiffNode result = objectDiffer.getDiff(x, null);

            assertSimpleRemoved(x, result);
        }

        @Test
        public void enumt() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            TestEnum x = TestEnum.TEST1;
            DiffNode result = objectDiffer.getDiff(x, null);

            assertSimpleRemoved(x, result);
        }

        private <T> void assertSimpleRemoved(T value, DiffNode result) {
            assertEquals(result.getName(), "root");
            assertEquals(result.getState(), DiffState.REMOVED);
            assertEquals(result.getOldValue(), value);
            assertNull(result.getNewValue());
            assertEquals(result.getChildren().size(), 0);
        }
    }

    @Nested
    class ChangedObject {

        @Test
        public void primitive() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            int x = 5;
            int y = 7;
            DiffNode result = objectDiffer.getDiff(x, y);

            assertSimpleChanged(x, y, result);
        }

        @Test
        public void string() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            String x = "test";
            String y = "testn";
            DiffNode result = objectDiffer.getDiff(x, y);

            assertSimpleChanged(x, y, result);
        }

        @Test
        public void enumt() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            TestEnum x = TestEnum.TEST1;
            TestEnum y = TestEnum.TEST2;
            DiffNode result = objectDiffer.getDiff(x, y);

            assertSimpleChanged(x, y, result);
        }

        private <T> void assertSimpleChanged(T oldV, T newV, DiffNode result) {
            assertEquals(result.getName(), "root");
            assertEquals(result.getState(), DiffState.CHANGED);
            assertEquals(result.getOldValue(), oldV);
            assertEquals(result.getNewValue(), newV);
            assertEquals(result.getChildren().size(), 0);
        }
    }

    @Nested
    class UnchangedObject {
        @Test
        public void primitive() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            int x = 5;
            int y = 5;
            DiffNode result = objectDiffer.getDiff(x, y);

            assertSimpleUnchanged(x, y, result);
        }

        @Test
        public void string() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            String x = "test";
            String y = "test";
            DiffNode result = objectDiffer.getDiff(x, y);

            assertSimpleUnchanged(x, y, result);
        }

        @Test
        public void enumt() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            TestEnum x = TestEnum.TEST1;
            TestEnum y = TestEnum.TEST1;
            DiffNode result = objectDiffer.getDiff(x, y);

            assertSimpleUnchanged(x, y, result);
        }

        private <T> void assertSimpleUnchanged(T oldV, T newV, DiffNode result) {
            assertEquals(result.getName(), "root");
            assertEquals(result.getState(), DiffState.UNCHANGED);
            assertEquals(result.getOldValue(), oldV);
            assertEquals(result.getNewValue(), newV);
            assertEquals(result.getChildren().size(), 0);
        }
    }

    private enum TestEnum {
        TEST1,
        TEST2,
    }
}
