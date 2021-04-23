package com.healthycoderapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DietPlannerTest {
    private DietPlanner dietPlanner;

    @BeforeEach
    void setUp(){
        this.dietPlanner = new DietPlanner(20,30,50);
    }

    @AfterEach
    void afterEach(){
        System.out.println("Diet Plan testing completed Successfully");
    }

    @Test
    void shouldReturnCorrectDietPlanWhenCorrectCoder(){
        //given
        Coder coder = new Coder(1.82, 75, 26, Gender.MALE);
        DietPlan dietPlanExpected = new DietPlan(2202, 110,73, 275);
        //When
        DietPlan actualPlan = dietPlanner.calculateDiet(coder);
        //then
        assertAll(
                () -> assertEquals(dietPlanExpected.getCalories(), actualPlan.getCalories()),
                () -> assertEquals(dietPlanExpected.getCarbohydrate(), actualPlan.getCarbohydrate()),
                () -> assertEquals(dietPlanExpected.getFat(), actualPlan.getFat()),
                () -> assertEquals(dietPlanExpected.getProtein(), actualPlan.getProtein())
        );
    }
}
