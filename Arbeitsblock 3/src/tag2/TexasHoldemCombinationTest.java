package tag2;

import org.junit.jupiter.api.Test;
import tag2.tools.CardDeck52;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TexasHoldemCombinationTest {

    @org.junit.jupiter.api.Test
    void combinations() {
        SignSup s = new SignSup();
        //royal flush test
        List<CardDeck52.Card> tableCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tableCards.add(new CardDeck52.Card(14-i, CardDeck52.Card.Sign.Diamonds));
        }
        tableCards.add(new CardDeck52.Card(2, CardDeck52.Card.Sign.Hearts));

        TexasHoldemHand hand = new TexasHoldemHand();
        hand.takeDeal(new CardDeck52.Card(10, CardDeck52.Card.Sign.Diamonds));
        hand.takeDeal(new CardDeck52.Card(11, CardDeck52.Card.Sign.Spades));

        TexasHoldemCombination combination = new TexasHoldemCombination(tableCards, hand);
        assertEquals(TexasHoldemCombination.CombinationType.RoyalFlush, combination.combinationType);

        //straight flush test
        tableCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tableCards.add(new CardDeck52.Card(12-i, CardDeck52.Card.Sign.Diamonds));
        }
        tableCards.add(new CardDeck52.Card(4, CardDeck52.Card.Sign.Hearts));
        hand = new TexasHoldemHand();
        hand.takeDeal(new CardDeck52.Card(8, CardDeck52.Card.Sign.Diamonds));
        hand.takeDeal(new CardDeck52.Card(13, CardDeck52.Card.Sign.Hearts));
        combination = new TexasHoldemCombination(tableCards, hand);
        assertEquals(TexasHoldemCombination.CombinationType.StraightFlush, combination.combinationType);

        //four of a kind test
        tableCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tableCards.add(new CardDeck52.Card(2, s.get()));
        }
        tableCards.add(new CardDeck52.Card(4, CardDeck52.Card.Sign.Hearts));
        tableCards.add(new CardDeck52.Card(4, CardDeck52.Card.Sign.Diamonds));
        hand = new TexasHoldemHand();
        hand.takeDeal(new CardDeck52.Card(2, s.get()));
        hand.takeDeal(new CardDeck52.Card(13, CardDeck52.Card.Sign.Hearts));

        combination = new TexasHoldemCombination(tableCards, hand);
        assertEquals(TexasHoldemCombination.CombinationType.FourOfAKind, combination.combinationType);

        //full house test
        tableCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tableCards.add(new CardDeck52.Card(4, s.get()));
        }
        tableCards.add(new CardDeck52.Card(5, s.get()));
        tableCards.add(new CardDeck52.Card(3, s.get()));
        hand = new TexasHoldemHand();
        hand.takeDeal(new CardDeck52.Card(5, s.get()));
        hand.takeDeal(new CardDeck52.Card(13, s.get()));
        combination = new TexasHoldemCombination(tableCards, hand);
        assertEquals(TexasHoldemCombination.CombinationType.FullHouse, combination.combinationType);

        //flush test
        tableCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tableCards.add(new CardDeck52.Card(12-i*2, CardDeck52.Card.Sign.Diamonds));
        }
        tableCards.add(new CardDeck52.Card(4, CardDeck52.Card.Sign.Hearts));
        hand = new TexasHoldemHand();
        hand.takeDeal(new CardDeck52.Card(8, CardDeck52.Card.Sign.Diamonds));
        hand.takeDeal(new CardDeck52.Card(13, CardDeck52.Card.Sign.Hearts));
        combination = new TexasHoldemCombination(tableCards, hand);
        assertEquals(TexasHoldemCombination.CombinationType.Flush, combination.combinationType);

        //straight test
        tableCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tableCards.add(new CardDeck52.Card(12-i, s.get()));
        }
        tableCards.add(new CardDeck52.Card(4, s.get()));
        tableCards.add(new CardDeck52.Card(4, s.get()));
        hand = new TexasHoldemHand();
        hand.takeDeal(new CardDeck52.Card(13, s.get()));
        hand.takeDeal(new CardDeck52.Card(9, s.get()));
        combination = new TexasHoldemCombination(tableCards, hand);
        assertEquals(TexasHoldemCombination.CombinationType.Straight, combination.combinationType);

        //three of a kind test
        tableCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tableCards.add(new CardDeck52.Card(2, s.get()));
        }
        tableCards.add(new CardDeck52.Card(4, s.get()));
        tableCards.add(new CardDeck52.Card(5, s.get()));
        hand = new TexasHoldemHand();
        hand.takeDeal(new CardDeck52.Card(14, s.get()));
        hand.takeDeal(new CardDeck52.Card(13, s.get()));
        combination = new TexasHoldemCombination(tableCards, hand);
        assertEquals(TexasHoldemCombination.CombinationType.ThreeOfAKind, combination.combinationType);

        //two pair test
        tableCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            tableCards.add(new CardDeck52.Card(2, s.get()));
        }
        tableCards.add(new CardDeck52.Card(4, s.get()));
        tableCards.add(new CardDeck52.Card(5, s.get()));
        hand = new TexasHoldemHand();
        hand.takeDeal(new CardDeck52.Card(14, s.get()));
        hand.takeDeal(new CardDeck52.Card(14, s.get()));
        combination = new TexasHoldemCombination(tableCards, hand);
        assertEquals(TexasHoldemCombination.CombinationType.TwoPair, combination.combinationType);

        //one pair test
        tableCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tableCards.add(new CardDeck52.Card(5+i, s.get()));
        }
        tableCards.add(new CardDeck52.Card(14, s.get()));
        tableCards.add(new CardDeck52.Card(3, s.get()));
        hand = new TexasHoldemHand();
        hand.takeDeal(new CardDeck52.Card(14, s.get()));
        hand.takeDeal(new CardDeck52.Card(13, s.get()));
        combination = new TexasHoldemCombination(tableCards, hand);
        assertEquals(TexasHoldemCombination.CombinationType.OnePair, combination.combinationType);

        //high card test
        tableCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tableCards.add(new CardDeck52.Card(5+i, s.get()));
        }
        tableCards.add(new CardDeck52.Card(10, s.get()));
        tableCards.add(new CardDeck52.Card(3, s.get()));
        hand = new TexasHoldemHand();
        hand.takeDeal(new CardDeck52.Card(14, s.get()));
        hand.takeDeal(new CardDeck52.Card(13, s.get()));
        combination = new TexasHoldemCombination(tableCards, hand);
        assertEquals(TexasHoldemCombination.CombinationType.HighCard, combination.combinationType);
    }

    @Test
    void randTest(){
        Stream<TexasHoldemCombination> stream = TexasHoldemCombination.generate();
        stream.limit(10).sorted(TexasHoldemCombination::compareTo).forEach(System.out::println);
    }
}

class SignSup implements Supplier<CardDeck52.Card.Sign> {
    int i=0;
    @Override
    public CardDeck52.Card.Sign get() {
        return CardDeck52.Card.Sign.values()[i++%4];
    }
}