package fr.spark.pfe.calcul.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import fr.spark.pfe.calcul.domain.Calculator;
import fr.spark.pfe.calcul.domain.model.CalculationModel;
import fr.spark.pfe.calcul.domain.model.CalculationType;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
//declarer mockito comme extension
@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {
   @Mock
   // l'appelle du Calculator et par calclatorService donc on va le mocké
   Calculator calculator = new Calculator();
   @Mock
	SolutionFormatter solutionFormatter;
	//classUnderTesting
	CalculatorService classUnderTest;
	@BeforeEach
			public void setup() {
		System.out.println("avant chaque test");
		 classUnderTest = new CalculatorServiceImpl(calculator,solutionFormatter);}
	@Test
	@DisplayName("calculateShouldUseCalculatorForAddition")
	public void calculateShouldUseCalculatorForAddition() {
		//GIVEN
		//parametrage du mock => sans echec puisque il est dans le cas de simulation
		when(calculator.add(1,2)).thenReturn(3);
		//WHEN
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.ADDITION, 1, 2)).getSolution();
		//THEN
		//verifier l'appelle de methode
		verify(calculator).add(1,2);
		assertThat(result).isEqualTo(3);
	}
	@Test
	@DisplayName("calculateShouldUseCalculatorForMultip")
	public void calculateShouldUseCalculatorForMultip() {
		//GIVEN
		//parametrage du mock
		when(calculator.multiply(1,2)).thenReturn(2);
		//WHEN
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.MULTIPLICATION, 1, 2)).getSolution();
		//THEN
		verify(calculator).multiply(1,2);
		assertThat(result).isEqualTo(2);
	}
	@Test
	@DisplayName("CalculateShouldUseCalculatorForDiv")
	public void calculateShouldUseCalculatorForDiv() {
		//GIVEN
		//parametrage du mock
		when(calculator.divide(2,2)).thenReturn(1);
		//WHEN
		final int result = classUnderTest.calculate(
				new CalculationModel(CalculationType.DIVISION, 2, 2)).getSolution();
		//THEN
		//verifier l'appelle de methode
		verify(calculator).divide(2,2);
		assertThat(result).isEqualTo(1);
	}
@Test
	public void shouldThrowIlligaleExceptionForDevidedByZero(){
		//GIVEN
	when(calculator.divide(1, 0)).thenThrow(new ArithmeticException());
	//WHEN
	//pour vérifier qu'une ligne de code peut générer une exception
	assertThrows(IllegalArgumentException.class,() -> classUnderTest.calculate(new CalculationModel(
			CalculationType.DIVISION, 1,
			0)).getSolution());
	//THEN
verify(calculator,times(1)).divide(1,0);

}
@Test
	public void calculateShouldFormatSolutionForAnAddition(){
	// GIVEN
	when(calculator.add(10000, 3000)).thenReturn(13000);
    when(solutionFormatter.format(13000)).thenReturn("13 000");
	// WHEN
	final String formattedResult = classUnderTest
			.calculate(new CalculationModel(CalculationType.ADDITION, 10000, 3000))
			.getFormattedSolution();

	// THEN
	assertThat(formattedResult).isEqualTo("13 000");
}

}
