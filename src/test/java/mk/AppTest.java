package mk;

import static org.junit.Assert.assertTrue;

import mk.exceptions.AppException;
import mk.service.CarsService;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppTest {
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    public static CarsService carsService;

    @BeforeClass
    public static void beforeClass() {
        carsService = new CarsService("car.json");
    }


    @Test(expected = AppException.class)
    public void when_parameterNotPassedToMethod_Then_ThenThrowException() {
        //given
        String name = null;

        //when
        carsService.findMostExpensiveCars(name);

    }
}
