package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointAssignerTest {

    PointAssigner pointAssigner;

    @BeforeEach
    void setUp(){
        pointAssigner = new PointAssigner();
    }
    @Test
    void testInitialize() {
        pointAssigner.initialize(4,2);
        assertEquals(pointAssigner.getStackList().get(0).pop(), 8);
        assertEquals(pointAssigner.getStackList().get(0).pop(), 6);
        assertEquals(pointAssigner.getStackList().get(0).pop(), 4);
        assertEquals(pointAssigner.getStackList().get(0).pop(), 2);
        assertEquals(pointAssigner.getStackList().get(1).pop(), 8);
        assertEquals(pointAssigner.getStackList().get(1).pop(), 6);
        assertEquals(pointAssigner.getStackList().get(1).pop(), 4);
        assertEquals(pointAssigner.getStackList().get(1).pop(), 2);

        pointAssigner = new PointAssigner();
        pointAssigner.initialize(2,3);
        assertEquals(pointAssigner.getStackList().get(0).pop(), 8);
        assertEquals(pointAssigner.getStackList().get(1).pop(), 8);
        assertEquals(pointAssigner.getStackList().get(0).pop(), 4);
        assertEquals(pointAssigner.getStackList().get(1).pop(), 4);

        pointAssigner = new PointAssigner();
        pointAssigner.initialize(3,3);
        assertEquals(pointAssigner.getStackList().get(0).pop(), 8);
        assertEquals(pointAssigner.getStackList().get(1).pop(), 8);
        assertEquals(pointAssigner.getStackList().get(0).pop(), 6);
        assertEquals(pointAssigner.getStackList().get(1).pop(), 6);
        assertEquals(pointAssigner.getStackList().get(0).pop(), 4);
        assertEquals(pointAssigner.getStackList().get(1).pop(), 4);
    }
    @Test
    void testExceptionInitialize(){
     //TODO
    }
    @Test
    void testAssignPoints() {
        //TODO
    }
    @Test
    void testExceptionAssignPoints(){
        //TODO
    }
}