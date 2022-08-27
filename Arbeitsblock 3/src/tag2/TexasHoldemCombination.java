package tag2;

import tag2.tools.CardDeck52;
import tag2.tools.CardDeck52.Card.Sign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public final class TexasHoldemCombination implements Comparable<TexasHoldemCombination> {
    public enum CombinationType {
        HighCard,
        OnePair,
        TwoPair,
        ThreeOfAKind,
        Straight,
        Flush,
        FullHouse,
        FourOfAKind,
        StraightFlush,
        RoyalFlush
    }

    CombinationType combinationType;
    List<CardDeck52.Card> combinationCards;

    List<CardDeck52.Card> kicker = new ArrayList<>();
    // a)
    TexasHoldemCombination(List<CardDeck52.Card> tableCards, TexasHoldemHand hand) {
        //find all possible combinations and sort them by eval
        List<List<CardDeck52.Card>> combinations = new ArrayList<>();
        combinations.add(tableCards);
    }

    TexasHoldemCombination(List<CardDeck52.Card> list) {
        this.combinationCards = list;
        //comparator for card
        Comparator<CardDeck52.Card> c = ( a, b)-> a.value-b.value;
        list.sort(c);
        
        boolean isFlush = true;
        for (int i=0; i<list.size()-1;i++) {
            isFlush=isFlush&&list.get(i).sign==list.get(i+1).sign;
        }

        boolean isStraight= true;
        for (int i=0; i<list.size()-1;i++) {
            isFlush=isFlush&&list.get(i).value==list.get(i+1).value+1;
        }
        //count values
        Map<Integer,Integer> count = new HashMap<>();
        for (CardDeck52.Card card: list){
            if (!count.containsKey(card.value)) {
                count.put(card.value, 1);
            } else{
                count.put(card.value, count.get(card.value)+1);
            }
        }

        //Royal Flush
        if (isFlush&&isStraight&&list.get(4).value==14) {
            combinationType = CombinationType.RoyalFlush;
            return;
        }

        //StraightFlush
        if (isFlush&&isStraight) {
            combinationType = CombinationType.StraightFlush;
            kicker.add(list.get(4));
            return;
        }

        //Vierling
        if (count.containsValue(4)){
            for (int key:count.keySet()){
                if (count.get(key)==4) {
                    combinationType = CombinationType.FourOfAKind;
                    kicker.add(0,new CardDeck52.Card(key, null));
                } else {
                    kicker.add(new CardDeck52.Card(key, null));
                }
            }
            return;
        }

        //fullhouse
        if (count.containsValue(3)&&count.containsValue(2)) {
            combinationType = CombinationType.FullHouse;
            for (int key:count.keySet()){
                if (count.get(key)==3) {
                    kicker.add(0, new CardDeck52.Card(key, null));
                }
                if (count.get(key)==2) {
                    kicker.add(new CardDeck52.Card(key, null));
                }
            }
        }

        //flush
        if (isFlush){
            combinationType = CombinationType.Flush;
        }

        //straight
        if (isStraight) {
            combinationType = CombinationType.Straight;
            kicker.add(list.get(4));
        }

        //Drilling
        if (count.containsValue(3)) {
            combinationType = CombinationType.ThreeOfAKind;
            for (int key:count.keySet()){
                if (count.get(key)==3) {
                    kicker.add(new CardDeck52.Card(key, null));
                }
            }
            for (int i = 4; i >=0 ; i--) {
                if (list.get(i).value!=kicker.get(i).value){
                    kicker.add(list.get(i));
                    return;
                }
            }
        }

        //Zwei Paare
        

    }



    // b)
    @Override
    public final int compareTo(TexasHoldemCombination that) {
        // TODO

        return 0;
    }

    // c)
    public static Stream<TexasHoldemCombination> generate() {
        // TODO

        return null;
    }

    public static void main(String[] args) {
        CardDeck52 deck = new CardDeck52();
        boolean hasTable = Math.random() >= 0.5;
        List<CardDeck52.Card> tableCards = hasTable ?
                deck.deal(ThreadLocalRandom.current().nextInt(3, 5 + 1)) :
                Collections.emptyList();

        TexasHoldemHand hand = new TexasHoldemHand();
        TexasHoldemHand hand2hand2 = new TexasHoldemHand();
        hand.takeDeal(deck.deal());
        hand2.takeDeal(deck.deal());
        hand.takeDeal(deck.deal());
        hand2.takeDeal(deck.deal());

        Stream.of(hand.eval(tableCards), hand2.eval(tableCards))
                .sorted(Comparator.reverseOrder())
                .forEach(combination -> System.out.println(
                        "CombinationType: " + combination.combinationType +
                        ", CombinationCards: " + combination.combinationCards));

        TexasHoldemCombination combination = hand.eval(tableCards);
        System.out.println("Table Cards (" + tableCards.size() + "): " + tableCards);
        System.out.println("Hand: " + Arrays.toString(hand.get()));
        System.out.println("Combination (" + combination.combinationCards.size() + "): " + combination.combinationType + " -> " + combination.combinationCards);
    }
}
