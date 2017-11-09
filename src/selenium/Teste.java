package selenium;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Teste {
	
	private WebDriver drive;
	@Before
	public void fixtureSetup() throws Exception {
		System.setProperty("webdriver.gecko.driver",
				"drivers/geckodriver");
		drive = new FirefoxDriver();
		drive.get("http://www.calculatoria.com/");
	}
	@After
	public void tearDown() throws Exception {
		drive.close();
	}
	
	enum Teclas {
		ZERO,UM,DOIS,TRES,QUATRO,CINCO,SEIS,SETE,OITO,NOVE,
		MULT,DIVID,MAIS,MENOS,IGUAL,DEL
	};
	
	private WebElement getTecla(Teclas t) throws Exception {
		if(Teclas.ZERO.ordinal() <= t.ordinal() && 
				t.ordinal() <= Teclas.NOVE.ordinal()) {
			By id = By.id("btn" + String.valueOf(
					96+t.ordinal()-Teclas.ZERO.ordinal()));
			return drive.findElement(id);
		}
		switch (t) {
		case MAIS:
			return drive.findElement(By.id("btn107"));
		case MULT:
			return drive.findElement(By.id("btn106"));
		case DEL:
			return drive.findElement(By.id("btn8"));
		case DIVID:
			return drive.findElement(By.id("btn111"));
		case IGUAL:
			return drive.findElement(By.id("btn13"));
		default:
			break;
		}
		throw new Exception();
	}
	
	@Test
	public void multiplicacao() throws Exception {
		getTecla(Teclas.DOIS).click();
		getTecla(Teclas.ZERO).click();
		getTecla(Teclas.MULT).click();
		getTecla(Teclas.QUATRO).click();
		getTecla(Teclas.IGUAL).click();

		String resultado = drive
				.findElement(By.name("exprdisplay"))
				.getAttribute("value");
		
		assertEquals("80", resultado);
	}

	@Test
	public void divisao() throws Exception {
		getTecla(Teclas.UM).click();
		getTecla(Teclas.ZERO).click();
		getTecla(Teclas.ZERO).click();
		getTecla(Teclas.DIVID).click();
		getTecla(Teclas.TRES).click();
		getTecla(Teclas.IGUAL).click();

		String resultado = drive
				.findElement(By.name("exprdisplay"))
				.getAttribute("value");
		
		assertEquals("33.333", resultado);
	}
	
	@Test
	public void complexo() throws Exception {
		// 5/2
		getTecla(Teclas.CINCO).click();
		getTecla(Teclas.DIVID).click();
		getTecla(Teclas.DOIS).click();
		getTecla(Teclas.IGUAL).click();
		// apagar casas decimais
		getTecla(Teclas.DEL).click();
		getTecla(Teclas.DEL).click();
		// +4
		getTecla(Teclas.MAIS).click();
		getTecla(Teclas.QUATRO).click();
		getTecla(Teclas.IGUAL).click();

		String resultado = drive
				.findElement(By.name("exprdisplay"))
				.getAttribute("value");
		
		assertEquals("6", resultado);
	
	}
}
