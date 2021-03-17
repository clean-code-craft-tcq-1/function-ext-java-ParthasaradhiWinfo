
package vitals;

import java.util.function.Function;

public class BatteryConditionCheck {

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
			printMessage(Constants.BREACH, Constants.CHARGE_RATE, Constants.HIGH);
			return false;
		}
		checkForMinWarning(chargeRate, 0, 0.8f, Constants.CHARGE_RATE, 5);
		checkForMaxWarning(chargeRate, 0, 0.8f, Constants.CHARGE_RATE, 5);
	
		return true;
	};
	static Function<Float, Function<Float, Boolean>> socCheck = (soc) -> {
		if (soc < 20 || soc > 80) {
			printMessage(Constants.BREACH, Constants.SOC, getHIGHorLowSoc(soc));
			return null;
		}
		checkForMinWarning(soc, 20, 80, Constants.SOC, 5);
		checkForMaxWarning(soc, 20, 80, Constants.SOC, 5);
		return chargeRateCheck;
	};

	static Function<Float, Function<Float, Boolean>> temperatureCheck(float temperature) {
		if (temperature < 0 || temperature > 45) {
			
			printMessage(Constants.BREACH, Constants.TEMPERATURE, getHIGHorLowTemp(temperature));
			return null;
		}
		checkForWarning(temperature, 0, 45, Constants.TEMPERATURE, 5);
		return socCheck;
	}

	static String getHIGHorLowTemp(float temperature){
		return temperature > 45 ? Constants.HIGH : Constants.LOW;
	}

	static String getHIGHorLowSoc(float soc){
		return soc > 80 ? Constants.HIGH : Constants.LOW;
	}

	static void checkForMinWarning(float value, float min, float max, String type, float deltaPercentage) {
		float delta = (deltaPercentage / max) * 100;
		if (type != Constants.CHARGE_RATE && value <= (min + delta)) {
			printMessage(Constants.WARNING, type, Constants.LOW);
		} 
	}

	static void checkForMaxWarning(float value, float min, float max, String type, float deltaPercentage) {
		float delta = (deltaPercentage / max) * 100;
		 if (value >= (max - delta)) {
			printMessage(Constants.WARNING, type, Constants.HIGH);
		}
	}

	

	public static void printMessage(String message, String type, String level) {
		
		System.out.println(message+" "+ type +" " + level);
	}

}