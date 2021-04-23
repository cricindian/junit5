package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class BMICalculatorTest {

    private String env = "dev";

    //static : as it runs once before all the test runs : ex : db connection
    @BeforeAll
    static void beforeAll(){
        System.out.println("before ALL");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After ALL");
    }

    @Nested
    class DietRecommendationTests {
        @ParameterizedTest(name = "weight={0},height={1}")
        @CsvSource(value = {"89,1.71", "95,1.75", "110, 1.78"})
        void shouldReturnTrueWhenDietRecommended(double weight, double height){
            //fail("Not yet implemented");

            //given weight and height

            //when
            boolean recommended = BMICalculator.isDietRecommended(weight, height);
            //then
            assertTrue(recommended);
        }

        @ParameterizedTest(name = "weight={0},height={1}")
        @CsvFileSource(resources = "/dietNotRecommendedInput.csv", numLinesToSkip = 1)
        void shouldReturnFalseWhenDietNotRecommended(double weight, double height){
            //given : csv file input
            //when
            boolean recommended = BMICalculator.isDietRecommended(weight, height);
            //then
            assertFalse(recommended);
        }
    }

    @Nested //Putting all the related test at one place
    class CoderWithWorstBMITests {

        @Test
        void shouldReturnCoderWithWorstBMI(){
            //given : List of codes
            List<Coder> coderList = new ArrayList<Coder>();
            coderList.add(new Coder(1.80,60));
            coderList.add(new Coder(1.82, 98));
            coderList.add(new Coder(1.82, 65));
            //when
            Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coderList);
            //then
            assertAll(()->assertEquals(1.82, coderWithWorstBMI.getHeight()),
                    ()->assertEquals(98.0, coderWithWorstBMI.getWeight()));
        }

        @Test
            //Performance
        void shouldReturnCoderWithWorstBMIWhenCodeListIsHuge(){
            assumeTrue(BMICalculatorTest.this.env.equals("prod"));
            //given : List of codes
            List<Coder> coderList = new ArrayList<Coder>();
            for (int i=0; i < 10000; i++){
                coderList.add(new Coder(1.0+i,10.0+i));
            }
            //when
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coderList);
            //Then
            assertTimeout(Duration.ofMillis(20),executable);
        }

    }

    @Test
    void shouldReturnNullWhenCoderListIsEmpty(){
        //given : empty list
        List<Coder> coderList = new ArrayList<>();
        //when
        Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coderList);
        //then
        assertNull(coderWithWorstBMI);

    }

    @RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisabledOnOs(OS.WINDOWS)
    void shouldThrowArithmeticsExceptionWhenHeightIsZero(){
        //given
        double weight = 50;
        double height = 0;
        //when
        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);
        //then
        assertThrows(ArithmeticException.class, executable);
    }



    @Test
    @DisplayName("sample customised name")
    @Disabled
    void shouldReturnCorrectBMIScoreArray(){
        //given : List of codes
        List<Coder> coderList = new ArrayList<Coder>();
        coderList.add(new Coder(1.80,60));
        coderList.add(new Coder(1.82, 98));
        coderList.add(new Coder(1.82, 64.7));

        double[] expectedScores = {18.52, 29.59, 19.53};
        //when
        double[] scores = BMICalculator.getBMIScores(coderList);
        //then
        assertArrayEquals(expectedScores, scores);
    }
    //Other Annotations
    //@DisplayName("sample name") at method level OR nested class level.
    //@Disabled : to skip the test
    //@DisabledOnOs(OS.WINDOWS) : to disable test on some OS

}
