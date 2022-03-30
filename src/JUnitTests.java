import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnitTests {

    @Test //straight vs 3 of a kind
    public void test1() {
		Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) < 0, "Straight beats Three of a kind.");
    }

    @Test //4 of a kind vs flush
	public void test2() {
		Cardable[] cards1 = {new Card(11, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.HEART), new Card(11, Cardable.Suit.DIAMOND), new Card(4, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.SPADE)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//four of a kind

		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//flush

		assertTrue(th1.compareTo(th2) > 0, "Four of a kind beats flush.");
	}

	@Test//straight flush vs straight
	public void test3() {
		Cardable[] cards1 = {new Card(12, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.DIAMOND), new Card(10, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.SPADE), new Card(8, Cardable.Suit.CLUB)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//straight

		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.HEART)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//straight flush

		assertTrue(th1.compareTo(th2) < 0, "Straight Flush beats Straight.");
	}

	@Test//two pairs vs pair
	public void test4() {
		Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.HEART), new Card(4, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.CLUB), new Card(12, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//pair

		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(3, Cardable.Suit.SPADE), new Card(4, Cardable.Suit.CLUB), new Card(7, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//twopairs

		assertTrue(th1.compareTo(th2) < 0, "Two pairs beats Pair.");
	}

	@Test//four of a kind tie breaking
	public void test5() {
		Cardable[] cards1 = {
				new Card(6, Cardable.Suit.CLUB),
				new Card(6, Cardable.Suit.HEART),
				new Card(6, Cardable.Suit.DIAMOND),
				new Card(6, Cardable.Suit.SPADE),
				new Card(2, Cardable.Suit.DIAMOND)
		};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//6-2 four of a kind

		Cardable[] cards2 = {
				new Card(1, Cardable.Suit.SPADE),
				new Card(1, Cardable.Suit.HEART),
				new Card(1, Cardable.Suit.DIAMOND),
				new Card(1, Cardable.Suit.CLUB),
				new Card(7, Cardable.Suit.DIAMOND)
		};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//A-7 four of a kind

		assertTrue(th1.compareTo(th2) < 0, "A-7 beats 6-2");
	}

	@Test//full house tie breaking
	public void test6() {
		Cardable[] cards1 = {
				new Card(6, Cardable.Suit.CLUB),
				new Card(6, Cardable.Suit.HEART),
				new Card(6, Cardable.Suit.DIAMOND),
				new Card(1, Cardable.Suit.SPADE),
				new Card(1, Cardable.Suit.DIAMOND)
		};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//6-A full house

		Cardable[] cards2 = {
				new Card(7, Cardable.Suit.SPADE),
				new Card(7, Cardable.Suit.HEART),
				new Card(7, Cardable.Suit.DIAMOND),
				new Card(3, Cardable.Suit.CLUB),
				new Card(3, Cardable.Suit.DIAMOND)
		};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//7-3 full house

		assertTrue(th1.compareTo(th2) < 0, "7-3 full house beats 6-A full house");
	}

	@Test//flush tie breaking
	public void test7() {
		Cardable[] cards1 = {
				new Card(6, Cardable.Suit.CLUB),
				new Card(10, Cardable.Suit.CLUB),
				new Card(3, Cardable.Suit.CLUB),
				new Card(5, Cardable.Suit.CLUB),
				new Card(12, Cardable.Suit.CLUB)
		};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//Flush, Q high

		Cardable[] cards2 = {
				new Card(13, Cardable.Suit.SPADE),
				new Card(12, Cardable.Suit.SPADE),
				new Card(10, Cardable.Suit.SPADE),
				new Card(4, Cardable.Suit.SPADE),
				new Card(7, Cardable.Suit.SPADE)
		};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//flush, K high

		assertTrue(th1.compareTo(th2) < 0, "K high flush beats Q high");
	}

	@Test//straight tie breaking
	public void test8() {
		Cardable[] cards1 = {
				new Card(1, Cardable.Suit.CLUB),
				new Card(13, Cardable.Suit.DIAMOND),
				new Card(12, Cardable.Suit.SPADE),
				new Card(11, Cardable.Suit.CLUB),
				new Card(10, Cardable.Suit.HEART)
		};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//Straight, A high

		Cardable[] cards2 = {
				new Card(1, Cardable.Suit.SPADE),
				new Card(2, Cardable.Suit.DIAMOND),
				new Card(3, Cardable.Suit.SPADE),
				new Card(4, Cardable.Suit.CLUB),
				new Card(5, Cardable.Suit.HEART)
		};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//Straight, 5 high

		assertTrue(th1.compareTo(th2) > 0, "A high flush beats 5 high");
	}

	@Test//Three of a kind tie breaking
	public void test9() {
		Cardable[] cards1 = {
				new Card(1, Cardable.Suit.CLUB),
				new Card(12, Cardable.Suit.DIAMOND),
				new Card(12, Cardable.Suit.SPADE),
				new Card(12, Cardable.Suit.CLUB),
				new Card(10, Cardable.Suit.HEART)
		};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//Three of a kind, QQQ-A-10

		Cardable[] cards2 = {
				new Card(10, Cardable.Suit.SPADE),
				new Card(10, Cardable.Suit.DIAMOND),
				new Card(10, Cardable.Suit.SPADE),
				new Card(4, Cardable.Suit.CLUB),
				new Card(1, Cardable.Suit.HEART)
		};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//Three of a kind, 101010-A-4

		assertTrue(th1.compareTo(th2) > 0, "QQQ-A-10 beats 101010-A-4");
	}

	@Test//Two pairs tie breaking
	public void test10() {
		Cardable[] cards1 = {
				new Card(2, Cardable.Suit.CLUB),
				new Card(2, Cardable.Suit.DIAMOND),
				new Card(12, Cardable.Suit.SPADE),
				new Card(12, Cardable.Suit.CLUB),
				new Card(10, Cardable.Suit.HEART)
		};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//Two pairs, QQ-22-10

		Cardable[] cards2 = {
				new Card(4, Cardable.Suit.SPADE),
				new Card(12, Cardable.Suit.DIAMOND),
				new Card(12, Cardable.Suit.HEART),
				new Card(4, Cardable.Suit.CLUB),
				new Card(2, Cardable.Suit.HEART)
		};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//Two pairs, QQ-44-2

		assertEquals(th1.evaluateHand(),"Two Pairs,Q,2", "checking two pairs syntax for th1");
		assertEquals(th2.evaluateHand(),"Two Pairs,Q,4", "checking two pairs syntax for th2");
		assertTrue(th1.compareTo(th2)<0, "QQ442 beats QQ2210");
	}

	@Test//pair tie breaking
	public void test11() {
		Cardable[] cards1 = {
				new Card(2, Cardable.Suit.CLUB),
				new Card(2, Cardable.Suit.DIAMOND),
				new Card(10, Cardable.Suit.SPADE),
				new Card(4, Cardable.Suit.CLUB),
				new Card(8, Cardable.Suit.HEART)
		};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//pair, 22-10-8-4

		Cardable[] cards2 = {
				new Card(8, Cardable.Suit.SPADE),
				new Card(2, Cardable.Suit.DIAMOND),
				new Card(10, Cardable.Suit.HEART),
				new Card(6, Cardable.Suit.CLUB),
				new Card(2, Cardable.Suit.HEART)
		};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//Two pairs, 22-10-8-6

		assertEquals(th1.evaluateHand(),"Pair,2,10,8,4", "checking two pairs syntax for th1");
		assertEquals(th2.evaluateHand(),"Pair,2,10,8,6", "checking two pairs syntax for th2");
		assertTrue(th1.compareTo(th2)<0, "22-10-8-6 beats 22-10-8-4");
	}

	@Test//high card tie breaking
	public void test12() {
		Cardable[] cards1 = {
				new Card(2, Cardable.Suit.CLUB),
				new Card(1, Cardable.Suit.DIAMOND),
				new Card(10, Cardable.Suit.SPADE),
				new Card(4, Cardable.Suit.CLUB),
				new Card(8, Cardable.Suit.HEART)
		};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//A10842

		Cardable[] cards2 = {
				new Card(8, Cardable.Suit.SPADE),
				new Card(2, Cardable.Suit.DIAMOND),
				new Card(10, Cardable.Suit.HEART),
				new Card(6, Cardable.Suit.CLUB),
				new Card(1, Cardable.Suit.HEART)
		};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//A10862

		assertEquals(th1.evaluateHand(),"High Card,A,10,8,4,2", "checking two pairs syntax for th1");
		assertEquals(th2.evaluateHand(),"High Card,A,10,8,6,2", "checking two pairs syntax for th2");
		assertTrue(th1.compareTo(th2)<0, "A10862 beats A10842");
	}

	@Test//Royal Flush Testing
	public void test13(){
		Cardable[] cards1 = {
				new Card(1, Cardable.Suit.CLUB),
				new Card(13, Cardable.Suit.CLUB),
				new Card(12, Cardable.Suit.CLUB),
				new Card(11, Cardable.Suit.CLUB),
				new Card(10, Cardable.Suit.CLUB)
		};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//royal flush of club

		assertEquals("Royal Flush",th1.evaluateHand());
	}

	@Test//Royal Flush vs straight flush
	public void test14(){
		Cardable[] cards1 = {
				new Card(1, Cardable.Suit.CLUB),
				new Card(13, Cardable.Suit.CLUB),
				new Card(12, Cardable.Suit.CLUB),
				new Card(11, Cardable.Suit.CLUB),
				new Card(10, Cardable.Suit.CLUB)
		};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//royal flush of club

		Cardable[] cards2 = {
				new Card(9, Cardable.Suit.HEART),
				new Card(13, Cardable.Suit.HEART),
				new Card(12, Cardable.Suit.HEART),
				new Card(11, Cardable.Suit.HEART),
				new Card(10, Cardable.Suit.HEART)
		};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//straight flush, K high

		assertEquals("Royal Flush",th1.evaluateHand());
		assertEquals("Straight Flush,K", th2.evaluateHand());
		assertTrue(th1.compareTo(th2) > 0, "Royal Flush beats Straight Flush");
	}


}
