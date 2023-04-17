package org.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal_DeckTest {

    //test to see that I create 12 cards
    @Test
    void testfillDeck() {
        CommonGoal_Deck deck= new CommonGoal_Deck();
        assertEquals(12, deck.getDeck().size());
    }

    //Test to see that I take two different cards and that these cards are no longer in the deck
    @Test
    void getRandom() {
        CommonGoal_Deck deck= new CommonGoal_Deck();
        int size= deck.getDeck().size();
        CommonGoal card1= deck.getRandom();
        CommonGoal card2= deck.getRandom();
        assertNotEquals(card1,card2);
        assertEquals(size-2, deck.getDeck().size());
        assertFalse(deck.getDeck().contains(card1));
        assertFalse(deck.getDeck().contains(card2));
    }
}