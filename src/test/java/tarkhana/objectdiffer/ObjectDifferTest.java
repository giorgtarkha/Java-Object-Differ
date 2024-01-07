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

        @Test
        public void nested() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            NestedObject x = createNestedObject();
            DiffNode result = objectDiffer.getDiff(null, x);

            assertEquals(DiffState.CREATED, result.getState());
            assertNull(result.getOldValue());
            assertEquals(2, result.getChildren().size());
            assertEquals("test", result.getChildren().get(0).getName());
            assertEquals("testN", result.getChildren().get(1).getName());
            assertEquals(2, result.getChildren().get(1).getChildren().size());
        }

        private <T> void assertSimpleCreated(T value, DiffNode result) {
            assertEquals("root", result.getName());
            assertEquals(DiffState.CREATED, result.getState());
            assertEquals(value, result.getNewValue());
            assertNull(result.getOldValue());
            assertEquals(0, result.getChildren().size());
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

        @Test
        public void nested() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            NestedObject x = createNestedObject();
            DiffNode result = objectDiffer.getDiff(x, null);

            assertEquals(DiffState.REMOVED, result.getState());
            assertNull(result.getNewValue());
            assertEquals(2, result.getChildren().size());
            assertEquals("test", result.getChildren().get(0).getName());
            assertEquals("testN", result.getChildren().get(1).getName());
            assertEquals(2, result.getChildren().get(1).getChildren().size());
        }

        private <T> void assertSimpleRemoved(T value, DiffNode result) {
            assertEquals("root", result.getName());
            assertEquals(DiffState.REMOVED, result.getState());
            assertEquals(value, result.getOldValue());
            assertNull(result.getNewValue());
            assertEquals(0, result.getChildren().size());
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

        @Test
        public void nested() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            NestedObject x = createNestedObject();
            NestedObject y = createNestedObject();
            y.test = "test1";
            y.testN.a = -1;
            DiffNode result = objectDiffer.getDiff(x, y);

            assertEquals(DiffState.CHANGED, result.getState());
            assertEquals(2, result.getChildren().size());
            assertEquals("test", result.getChildren().get(0).getName());
            assertEquals("test", result.getChildren().get(0).getOldValue());
            assertEquals("test1", result.getChildren().get(0).getNewValue());
            assertEquals("testN", result.getChildren().get(1).getName());
            assertEquals(2, result.getChildren().get(1).getChildren().size());
        }

        @Test
        public void interfaced() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            InterfacedObject x = createInterfacedObject(true);
            InterfacedObject y = createInterfacedObject(false);
            DiffNode result = objectDiffer.getDiff(x, y);
            System.out.println(result);
        }

        private <T> void assertSimpleChanged(T oldV, T newV, DiffNode result) {
            assertEquals("root", result.getName());
            assertEquals(DiffState.CHANGED, result.getState());
            assertEquals(oldV, result.getOldValue());
            assertEquals(newV, result.getNewValue());
            assertEquals(0, result.getChildren().size());
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

        @Test
        public void nested() throws IllegalAccessException {
            ObjectDiffer objectDiffer = new ObjectDiffer.ObjectDiffBuilder()
                    .build();
            NestedObject x = createNestedObject();
            NestedObject y = createNestedObject();
            DiffNode result = objectDiffer.getDiff(x, y);

            assertEquals(DiffState.UNCHANGED, result.getState());
            assertEquals(2, result.getChildren().size());
            assertEquals("test", result.getChildren().get(0).getName());
            assertEquals("testN", result.getChildren().get(1).getName());
            assertEquals(2, result.getChildren().get(1).getChildren().size());
        }

        private <T> void assertSimpleUnchanged(T oldV, T newV, DiffNode result) {
            assertEquals("root", result.getName());
            assertEquals(DiffState.UNCHANGED, result.getState());
            assertEquals(oldV, result.getOldValue());
            assertEquals(newV, result.getNewValue());
            assertEquals(0, result.getChildren().size());
        }
    }

    private enum TestEnum {
        TEST1,
        TEST2,
    }

    private static class NestedObject {

        String test;

        NestedClass testN;

        private static class NestedClass {
            int a;
            int b;
        }
    }

    private static NestedObject createNestedObject() {
        NestedObject nestedObject = new NestedObject();
        nestedObject.test = "test";
        nestedObject.testN = new NestedObject.NestedClass();
        nestedObject.testN.a = 5;
        nestedObject.testN.b = 6;
        return nestedObject;
    }

    private static class InterfacedObject {

        IF test;

        private interface IF {}

        private static class TestImpl1 implements IF {
            String x;

            public TestImpl1(String x) { this.x = x; }
        }

        private static class TestImpl2 implements  IF {
            String y;

            public TestImpl2(String y) { this.y = y; }
        }
    }

    private static InterfacedObject createInterfacedObject(boolean b) {
        InterfacedObject result = new InterfacedObject();
        result.test = b ? new InterfacedObject.TestImpl1("testx") : new InterfacedObject.TestImpl2("testy");
        return result;
    }
}
