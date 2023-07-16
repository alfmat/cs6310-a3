package AppTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.gatech.cs6310.Service.DeliveryService;

public class AppTest {
	
	DeliveryService service;
	String original;
	String result;
	List<Object> files;
	
	// JUnit Before/After
	@BeforeEach
	void setup() {
		service = new DeliveryService();
	}
	
	@AfterEach
	void clearVariable() {
		service = null;
		original = null;
		result = null;
		files = null;
	}
	
	// helper functions
	List<Object> getFiles(String i) throws Exception {
		List<Object> files = new ArrayList<>();
		files.add(new Scanner(new File("test_scenarios/commands_" + i + ".txt")));
		files.add(FileUtils.readFileToString(new File("test_results/drone_delivery_initial_" + i + "_results.txt"), "utf-8"));
		return files;
	}
	
	void outputCompare(String testCase, String original, String result) {
		int idx = StringUtils.indexOfDifference(original, result);
		final int NO_MATCHES_FOUND = -1;
		if(idx > NO_MATCHES_FOUND) {
			final int DISPLAY_LIMIT = 500;
			System.out.println("TC:" + testCase);
			System.out.println("-------------- Result --------------");
			System.out.println(StringUtils.abbreviate(result.substring(idx), DISPLAY_LIMIT) + "\n");
			System.out.println("-------------- Original --------------");
			System.out.println(StringUtils.abbreviate(original.substring(idx), DISPLAY_LIMIT) + "\n\n");
		}
	}
	
	void baseRun(String testCase) throws Exception {
		files = getFiles(testCase);
		Scanner sc = (Scanner)files.get(0);
		result = service.testCommandLoop(sc);
		original = (String)files.get(1);
		outputCompare(testCase, original, result);
	}
	
	// JUnit tests
	
	@Test
	@DisplayName("case00")
	void case00() throws Exception {
		baseRun("00");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case01")
	void case01() throws Exception {
		baseRun("01");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case02")
	void case02() throws Exception {
		baseRun("02");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case03")
	void case03() throws Exception {
		baseRun("03");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case04")
	void case04() throws Exception {
		baseRun("04");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case05")
	void case05() throws Exception {
		baseRun("05");
		assertEquals(original, result);
	}

	@Test
	@DisplayName("case06")
	void case06() throws Exception {
		baseRun("06");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case07")
	void case07() throws Exception {
		baseRun("07");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case08")
	void case08() throws Exception {
		baseRun("08");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case09")
	void case09() throws Exception {
		baseRun("09");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case 10")
	void case10() throws Exception {
		baseRun("10");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case11")
	void case11() throws Exception {
		baseRun("11");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case12")
	void case12() throws Exception {
		baseRun("12");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case13")
	void case13() throws Exception {
		baseRun("13");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case14")
	void case14() throws Exception {
		baseRun("14");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case15")
	void case15() throws Exception {
		baseRun("15");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case16")
	void case16() throws Exception {
		baseRun("16");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case17")
	void case17() throws Exception {
		baseRun("17");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case18")
	void case18() throws Exception {
		baseRun("18");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case19")
	void case19() throws Exception {
		baseRun("19");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case20")
	void case20() throws Exception {
		baseRun("20");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case21")
	void case21() throws Exception {
		baseRun("21");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case22")
	void case22() throws Exception {
		baseRun("22");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case23")
	void case23() throws Exception {
		baseRun("23");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case24")
	void case24() throws Exception {
		baseRun("24");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case25")
	void case25() throws Exception {
		baseRun("25");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case26")
	void case26() throws Exception {
		baseRun("26");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case27")
	void case27() throws Exception {
		baseRun("27");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case28")
	void case28() throws Exception {
		baseRun("28");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case29")
	void case29() throws Exception {
		baseRun("29");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case30")
	void case30() throws Exception {
		baseRun("30");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case31")
	void case31() throws Exception {
		baseRun("31");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case32")
	void case32() throws Exception {
		baseRun("32");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case33")
	void case33() throws Exception {
		baseRun("33");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case34")
	void case34() throws Exception {
		baseRun("34");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case35")
	void case35() throws Exception {
		baseRun("35");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case36")
	void case36() throws Exception {
		baseRun("36");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case37")
	void case37() throws Exception {
		baseRun("37");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case38")
	void case38() throws Exception {
		baseRun("38");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case39")
	void case39() throws Exception {
		baseRun("39");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case40")
	void case40() throws Exception {
		baseRun("40");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case41")
	void case41() throws Exception {
		baseRun("41");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case42")
	void case42() throws Exception {
		baseRun("42");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case43")
	void case43() throws Exception {
		baseRun("43");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case44")
	void case44() throws Exception {
		baseRun("44");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case45")
	void case45() throws Exception {
		baseRun("45");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case46")
	void case46() throws Exception {
		baseRun("46");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case47")
	void case47() throws Exception {
		baseRun("47");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case48")
	void case48() throws Exception {
		baseRun("48");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case49")
	void case49() throws Exception {
		baseRun("49");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case50")
	void case50() throws Exception {
		baseRun("50");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case51")
	void case51() throws Exception {
		baseRun("51");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case52")
	void case52() throws Exception {
		baseRun("52");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case53")
	void case53() throws Exception {
		baseRun("53");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case54")
	void case54() throws Exception {
		baseRun("54");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case55")
	void case55() throws Exception {
		baseRun("55");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case56")
	void case56() throws Exception {
		baseRun("56");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case57")
	void case57() throws Exception {
		baseRun("57");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case58")
	void case58() throws Exception {
		baseRun("58");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case59")
	void case59() throws Exception {
		baseRun("59");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case60")
	void case60() throws Exception {
		baseRun("60");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case61")
	void case61() throws Exception {
		baseRun("61");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case62")
	void case62() throws Exception {
		baseRun("62");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case63")
	void case63() throws Exception {
		baseRun("63");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case64")
	void case64() throws Exception {
		baseRun("64");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case65")
	void case65() throws Exception {
		baseRun("65");
		assertEquals(original, result);
	}
	
	@Test
	@DisplayName("case66")
	void case66() throws Exception {
		baseRun("66");
		assertEquals(original, result);
	}
}
