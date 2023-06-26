package org.project.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Model.PersonalGoal_Deck;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonalGoal_DeckTest {

    PersonalGoal_Deck deck;

    @BeforeEach
    void setup(){
        deck = new PersonalGoal_Deck();
    }

    @Test
    void fillDeck() {
        deck.fillDeck("test_1.json");
        PersonalGoal goal = deck.getRandom();
        System.out.println(goal.getColoredGoal().get(0).getCoordinates().getX());
        assertTrue(deck.deck.size() == 11);
    }
}
