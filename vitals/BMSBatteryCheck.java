
package vitals;

import java.util.function.Function;

public class BatteryConditionCheck {

   
	public static final String LOW = "LOW";
	public static final String HIGH = "HIGH";
	public static final String WARNING = "WARNING";
	public static final String BREACH = "Breach";
	public static final String SOC = "state of change";
	public static final String CHARGE_RATE = "Charge Rate";
	public static final String TEMPERATURE = "Temperature";

	static boolean batteryIsOk(float temperature, float soc, float chargeRate) {
		try {
			Function<Float, Function<Float, Boolean>> socCheckMethod = temperatureCheck(temperature);
			Function<Float, Boolean> chargeRateCheckMethod = socCheckMethod.apply(soc);
			return chargeRateCheckMethod.apply(chargeRate);
		} catch (NullPointerException ne) {
			return false;
		}
	}

	static Function<Float, Boolean> chargeRateCheck = (chargeRate) -> {
		if (chargeRate > 0.8) {
			printMessage(BREACH, CHARGE_RATE, HIGH);
			return false;
		}
		checkForWarning(chargeRate, 0, 0.8f, CHARGE_RATE, 5);
		return true;
	};
	static Function<Float, Function<Float, Boolean>> socCheck = (soc) -> {
		if (soc < 20 || soc > 80) {
			printMessage(BREACH, SOC,
					soc > 80 ? HIGH : LOW);
			return null;
		}
		checkForWarning(soc, 20, 80, SOC, 5);
		return chargeRateCheck;
	};

	static Function<Float, Function<Float, Boolean>> temperatureCheck(float temperature) {
		if (temperature < 0 || temperature > 45) {
			printMessage(BREACH, TEMPERATURE,
					temperature > 45 ? HIGH : LOW);
			return null;
		}
		checkForWarning(temperature, 0, 45, TEMPERATURE, 5);
		return socCheck;
	}

	static void checkForWarning(float value, float min, float max, String type, float deltaPercentage) {
		float delta = (deltaPercentage / max) * 100;
		if (type != CHARGE_RATE && value <= (min + delta)) {
			printMessage(WARNING, type, LOW);
		} else if (value >= (max - delta)) {
			printMessage(WARNING, type, HIGH);
		}
	}

	

	public static void printMessage(String message, String type, String level) {
		
		System.out.println(message+" "+ type +" " + level);
	}

}