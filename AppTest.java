package ALDAList;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;


/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */

	// These two methods are the only places in the code that mentions the name
	// of your class.
	private static ALDAList<String> createNewList() {
		return new MyALDAList<String>();
	}

	private static ALDAList<Integer> createIntegerList() {
		return new MyALDAList<Integer>();
	}

	private ALDAList<String> list = createNewList();

	// Help method, not a test
	private void testField(java.lang.reflect.Field f) {
		assertTrue(java.lang.reflect.Modifier.isPrivate(f.getModifiers()),
				"All attributes should (probably) be private ");
		assertFalse(f.getType().isArray(), "There is no reason to use any arrays on this assignment");
		assertFalse(java.lang.reflect.Modifier.isStatic(f.getModifiers()),
				"There is (probably) not any reason to use any static attributes");
		for (Class<?> i : f.getType().getInterfaces()) {
			assertFalse(i.getName().startsWith("java.util"),
					"You should implement the functionality yourself, not use any of the list implementations already available");
		}
	}

	// How you work on this assignment is up to you, but a recommendation is to
	// uncomment the test methods below one by one in the order they are
	// presented. Remember though that the tests only are intended to cover
	// obvious errors. Even if all of them works there may still be errors in
	// your code. You are responsible for finding those. You may add as many
	// tests as you like to the test suite, but do NOT remove or change any of
	// the existing ones unless you are absolutely certain that they are wrong.
	// If we find any problems with the tests we will publish information about
	// this in Moodle, and also update the tests there.

	@Test
	public void testObviousImplementationErrors() {
		for (java.lang.reflect.Field f : list.getClass().getDeclaredFields()) {
			testField(f);
		}
	}

	@BeforeEach
	public void setUp() {
		list.add("First");
		list.add("Second");
		list.add("Third");
		list.add("Fourth");
		list.add("Fifth");
		System.out.println(list.size());
	}

	@Test
	public void testEmpty() {
		// Since setUp enters a number of items into the list
		list = createNewList();
		assertEquals(0, list.size());
		assertEquals("[]", list.toString());
	}

	@Test
	public void testGetOnEmptyList() {
		// Since setUp enters a number of items into the list
		list = createNewList();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get(0);
		});
	}

	@Test
	public void testSimpleMethodsOnDefaultList() {
		assertEquals(5, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Third", list.get(2));
		assertEquals("Fifth", list.get(4));
		assertEquals("[First, Second, Third, Fourth, Fifth]", list.toString());

		list.add("Second");
		assertEquals(6, list.size());
		assertEquals("Second", list.get(5));
	}

	@Test
	public void testIndexBelowZero() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get(-1);
		});
	}

	@Test
	public void testIndexAboveMax() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get(5);
		});
	}

	@Test
	public void addWithIndex() {
		list.add(0, "A");
		list.add(6, "B");
		list.add(2, "C");
		assertEquals(8, list.size());
		assertEquals("A", list.get(0));
		assertEquals("C", list.get(2));
		assertEquals("B", list.get(7));
		assertEquals("[A, First, C, Second, Third, Fourth, Fifth, B]", list.toString());
	}

	@Test
	public void testAddIndexBelowZero() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.add(-1, "ABC");
		});
	}

	@Test
	public void testAddIndexAboveMax() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.add(6, "ABC");
		});
	}

	@Test
	public void testClear() {
		list.clear();
		assertEquals(0, list.size());
		list.add("First");
		list.add(0, "Second");
		assertEquals(2, list.size());
		assertEquals("First", list.get(1));
		assertEquals("Second", list.get(0));
	}

	@Test
	public void testContains() {
		assertTrue(list.contains("First"));
		assertTrue(list.contains("Third"));
		assertFalse(list.contains("ABC"));
		assertFalse(list.contains(""));
	}

	@Test
	public void testIndexOf() {
		assertEquals(0, list.indexOf("First"));
		assertEquals(4, list.indexOf("Fifth"));
		assertEquals(-1, list.indexOf("ABC"));
		list.add("Second");
		assertEquals(1, list.indexOf("Second"));
	}

	@Test
	public void testRemoveWithIndex() {
		assertEquals("Third", list.remove(2));
		assertEquals(4, list.size());
		assertEquals("Second", list.get(1));
		assertEquals("Fourth", list.get(2));

		assertEquals("First", list.remove(0));
		assertEquals(3, list.size());
		assertEquals("Second", list.get(0));

		assertEquals("Fifth", list.remove(2));
		assertEquals(2, list.size());
		assertEquals("Fourth", list.get(1));
	}

	@Test
	public void testRemoveAtEnd() {
		list.remove(4);
		assertEquals(4, list.size());
		list.remove(3);
		assertEquals(3, list.size());
		assertEquals("[First, Second, Third]", list.toString());

		list.add("A");
		assertEquals(4, list.size());
		assertEquals("[First, Second, Third, A]", list.toString());
		list.add("B");
		assertEquals(5, list.size());
		assertEquals("[First, Second, Third, A, B]", list.toString());

		list.remove(4);
		assertEquals(4, list.size());
		list.remove(3);
		assertEquals(3, list.size());
		assertEquals("[First, Second, Third]", list.toString());

		list.add(3, "A");
		assertEquals(4, list.size());
		assertEquals("[First, Second, Third, A]", list.toString());
		list.add(4, "B");
		assertEquals(5, list.size());
		assertEquals("[First, Second, Third, A, B]", list.toString());
	}

	@Test
	public void testRemoveIndexBelowZero() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(-1);
		});
	}

	@Test
	public void testRemoveIndexAboveMax() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(5);
		});
	}

	@Test
	public void testRemoveOnEmptyList() {
		list.clear();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove(0);
		});
	}

	@Test
	public void testRemoveObject() {
		assertTrue(list.remove("Third"));
		assertEquals("Second", list.get(1));
		assertEquals("Fourth", list.get(2));

		list.remove("First");
		assertEquals(3, list.size());
		assertEquals("Second", list.get(0));

		list.remove("Fifth");
		assertEquals(2, list.size());
		assertEquals("Fourth", list.get(1));

		list.remove("ABC");
		assertEquals(2, list.size());
		assertEquals("Second", list.get(0));
		assertEquals("Fourth", list.get(1));
	}

	@Test
	public void testRemoveDuplicates() {
		list.add("First");
		list.add("Third");
		list.add("Fifth");
		assertEquals("[First, Second, Third, Fourth, Fifth, First, Third, Fifth]", list.toString());

		list.remove("Third");
		assertEquals("[First, Second, Fourth, Fifth, First, Third, Fifth]", list.toString());
		list.remove("Third");
		assertEquals("[First, Second, Fourth, Fifth, First, Fifth]", list.toString());
		list.remove("First");
		assertEquals("[Second, Fourth, Fifth, First, Fifth]", list.toString());
		list.remove("Second");
		assertEquals("[Fourth, Fifth, First, Fifth]", list.toString());
		list.remove("Fifth");
		assertEquals("[Fourth, First, Fifth]", list.toString());
		list.remove("Fifth");
		assertEquals("[Fourth, First]", list.toString());

		list.add(0, "A");
		list.add("B");
		assertEquals("[A, Fourth, First, B]", list.toString());
	}

	private static final java.util.Random rnd = new java.util.Random();
	private static final String[] names = { "Adam", "Bertil", "Cesar", "David", "Erik", "Filip", "Gustav", "Helge",
			"Ivar", "Johan", "Kalle", "ludvig", "Martin", "Niklas" };

	private String randomName() {
		return names[rnd.nextInt(names.length)];
	}

	private void testBeforeAndAfterRandomOperation(java.util.List<String> oracle) {
		// Here you can put test code that should be executed before and after
		// each random operation in the test below.
		assertEquals(oracle.size(), list.size());
		for (int n = 0; n < oracle.size(); n++) {
			assertEquals(oracle.get(n), list.get(n));
		}
		assertEquals(oracle.toString(), list.toString());
	}

	@Test
	public void testMix() {
		list.clear();
		java.util.List<String> oracle = new java.util.ArrayList<String>();

		for (int n = 0; n < 1000; n++) {
			String name = randomName();

			// Random insert
			switch (rnd.nextInt(5)) {
			case 0:
				testBeforeAndAfterRandomOperation(oracle);
				list.add(name);
				oracle.add(name);
				testBeforeAndAfterRandomOperation(oracle);
				break;
			case 1:
				testBeforeAndAfterRandomOperation(oracle);
				list.add(0, name);
				oracle.add(0, name);
				testBeforeAndAfterRandomOperation(oracle);
				break;
			case 2:
				testBeforeAndAfterRandomOperation(oracle);
				list.add(list.size(), name);
				oracle.add(oracle.size(), name);
				testBeforeAndAfterRandomOperation(oracle);
				break;
			case 3:
			case 4:
				testBeforeAndAfterRandomOperation(oracle);
				int index = list.size() == 0 ? 0 : rnd.nextInt(list.size());
				list.add(index, name);
				oracle.add(index, name);
				testBeforeAndAfterRandomOperation(oracle);
				break;
			}

			if (oracle.size() > 0) {

				// Random removal 70% of the times
				switch (rnd.nextInt(10)) {
				case 3:
					testBeforeAndAfterRandomOperation(oracle);
					list.remove(0);
					oracle.remove(0);
					testBeforeAndAfterRandomOperation(oracle);
					break;
				case 4:
					testBeforeAndAfterRandomOperation(oracle);
					list.remove(list.size() - 1);
					oracle.remove(oracle.size() - 1);
					testBeforeAndAfterRandomOperation(oracle);
					break;
				case 5:
				case 6:
					testBeforeAndAfterRandomOperation(oracle);
					int index = rnd.nextInt(list.size());
					list.remove(index);
					oracle.remove(index);
					testBeforeAndAfterRandomOperation(oracle);
					break;
				case 7:
				case 8:
					testBeforeAndAfterRandomOperation(oracle);
					name = randomName();
					list.remove(name);
					oracle.remove(name);
					testBeforeAndAfterRandomOperation(oracle);
					break;
				case 9:
					testBeforeAndAfterRandomOperation(oracle);
					if (rnd.nextInt(10) < 2) {
						list.clear();
						oracle.clear();
					}
					testBeforeAndAfterRandomOperation(oracle);
				}
			}

			if (oracle.size() == 0) {
				assertEquals(0, list.size());
			} else {
				// Random check
				switch (rnd.nextInt(10)) {
				case 0:
					assertEquals(oracle.size(), list.size());
					break;
				case 1:
					assertEquals(oracle.get(0), list.get(0));
					break;
				case 2:
					assertEquals(oracle.get(oracle.size() - 1), list.get(list.size() - 1));
					break;
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
					int index = rnd.nextInt(list.size());
					assertEquals(oracle.get(index), list.get(index));
					break;
				case 9:
					assertEquals(oracle.toString(), list.toString());
					break;
				}
			}
		}

	}

	@Test
	public void testIsItearble() {
		for (String s : list)
			// This code is not necessay but removes a warning that s isn't
			// used.
			s.length();
	}

	@Test
	public void testBasicIteration() {
		Iterator<String> i = list.iterator();
		assertTrue(i.hasNext());
		assertEquals("First", i.next());
		assertTrue(i.hasNext());
		assertEquals("Second", i.next());
		assertTrue(i.hasNext());
		assertEquals("Third", i.next());
		assertTrue(i.hasNext());
		assertEquals("Fourth", i.next());
		assertTrue(i.hasNext());
		assertEquals("Fifth", i.next());
		assertFalse(i.hasNext());
	}

	@Test
	public void testToLongIteration() {
		Iterator<String> i = list.iterator();
		for (int n = 0; n < list.size(); n++) {
			i.next();
		}
		assertThrows(NoSuchElementException.class, () -> {
			i.next();
		});
	}

	@Test
	public void testIterationOnEmptyList() {
		list.clear();
		Iterator<String> i = list.iterator();
		assertFalse(i.hasNext());
		assertThrows(NoSuchElementException.class, () -> {
			i.next();
		});
	}

	@Test
	public void testMultipleConcurrentIterators() {
		Iterator<String> i1 = list.iterator();
		assertTrue(i1.hasNext());
		assertEquals("First", i1.next());
		assertEquals("Second", i1.next());
		Iterator<String> i2 = list.iterator();
		assertTrue(i2.hasNext());
		assertEquals("First", i2.next());
		assertEquals("Third", i1.next());
		assertEquals("Second", i2.next());
		assertEquals("Fourth", i1.next());
		assertEquals("Third", i2.next());
		assertEquals("Fourth", i2.next());
		assertEquals("Fifth", i2.next());
		assertEquals("Fifth", i1.next());
		assertFalse(i1.hasNext());
		assertFalse(i2.hasNext());
	}

	@Test
	public void testRemoveOnIterator() {
		Iterator<String> i = list.iterator();
		assertEquals("First", i.next());
		i.remove();
		assertEquals(4, list.size());
		assertEquals("Second", list.get(0));
		assertEquals("Second", i.next());
		assertEquals("Third", i.next());
		i.remove();
		assertEquals(3, list.size());
		assertEquals("Second", list.get(0));
		assertEquals("Fourth", list.get(1));
		assertEquals("Fourth", i.next());
		assertEquals("Fifth", i.next());
		i.remove();
		assertEquals(2, list.size());
		assertEquals("Second", list.get(0));
		assertEquals("Fourth", list.get(1));
	}

	@Test
	public void testRemoveOnIteratorWithoutNext() {
		Iterator<String> i = list.iterator();
		assertThrows(IllegalStateException.class, () -> {
			i.remove();
		});
	}

	@Test
	public void testRemoveOnIteratorTwice() {
		Iterator<String> i = list.iterator();
		i.next();
		i.remove();
		assertThrows(IllegalStateException.class, () -> {
			i.remove();
		});
	}

	@Test
	public void testOtherTypesOfData() {
		ALDAList<Integer> l = createIntegerList();
		l.add(5);
		l.add(Integer.valueOf(10));
		assertEquals(Integer.valueOf(5), l.get(0));
		assertEquals(Integer.valueOf(10), l.get(1));
	}
}


