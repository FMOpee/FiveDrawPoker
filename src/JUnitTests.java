import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnitTests {

    @Test //straight vs 3ofakind
    public void test1() {
		Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.DIAMOND)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);

		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.DIAMOND)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);

		assertTrue(th1.compareTo(th2) < 0, "Straight beats Three of a kind.");
    }

    @Test //4ofakind vs flush
	public void test2() {
		Cardable[] cards1 = {new Card(11, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.HEART), new Card(11, Cardable.Suit.DIAMOND), new Card(4, Cardable.Suit.CLUB), new Card(11, Cardable.Suit.SPADE)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//four of a kind

		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//flush

		assertTrue(th1.compareTo(th2) > 0, "Four of a kind beats flush.");
	}

	@Test//straightflush vs straight
	public void test3() {
		Cardable[] cards1 = {new Card(12, Cardable.Suit.SPADE), new Card(11, Cardable.Suit.DIAMOND), new Card(10, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.SPADE), new Card(8, Cardable.Suit.CLUB)};
		TestableHand th1 = new Hand();
		th1.addCards(cards1);//straight

		Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.HEART)};
		TestableHand th2 = new Hand();
		th2.addCards(cards2);//straight flush

		assertTrue(th1.compareTo(th2) < 0, "Straight Flush beats Straight.");
	}

	@Test//twopairs vs pair
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

//	@Test//straight tie breaking
//	public void test9() {
//		Cardable[] cards1 = {
//				new Card(1, Cardable.Suit.CLUB),
//				new Card(13, Cardable.Suit.DIAMOND),
//				new Card(12, Cardable.Suit.SPADE),
//				new Card(11, Cardable.Suit.CLUB),
//				new Card(10, Cardable.Suit.HEART)
//		};
//		TestableHand th1 = new Hand();
//		th1.addCards(cards1);//Straight, A high
//
//		Cardable[] cards2 = {
//				new Card(1, Cardable.Suit.SPADE),
//				new Card(2, Cardable.Suit.DIAMOND),
//				new Card(3, Cardable.Suit.SPADE),
//				new Card(4, Cardable.Suit.CLUB),
//				new Card(5, Cardable.Suit.HEART)
//		};
//		TestableHand th2 = new Hand();
//		th2.addCards(cards2);//Straight, 5 high
//
//		assertTrue(th1.compareTo(th2) > 0, "A high flush beats 5 high");
//	}
}
