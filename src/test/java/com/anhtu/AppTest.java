package com.anhtu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    public void calculateDistanceInKilometer_CompareWithExpectedResult_EqualsExpected() {
        //System.out.println(App.calculateDistanceInKilometer(53.32055555555556, -1.7297222222222221, 53.31861111111111, -1.6997222222222223));
        assertEquals(5034, Math.round(Solver.calculateDistanceInKilometer(50.36638889, 4.13388889, 42.35111111, 71.04083333)));
        assertEquals(3, Math.round(Solver.calculateDistanceInKilometer(49.243824, -121.887340, 49.235347, -121.92532)));
        assertEquals(3936, Math.round(Solver.calculateDistanceInKilometer(40.7128, 74.00600, 34.0522, 118.2437)));
        assertEquals(2, Math.round(Solver.calculateDistanceInKilometer(53.32055555555556, -1.7297222222222221, 53.31861111111111, -1.6997222222222223)));
    }
}
