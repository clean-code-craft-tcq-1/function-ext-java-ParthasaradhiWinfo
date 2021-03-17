package vitals;

import org.junit.jupiter.api.Test;

class BatteryManagementTest {

	@Test
	void batteryGoodTest() {
		assert(BatteryConditionCheck.batteryIsOk(25, 70, 0.7f) == true);
	}
	
	@Test
	void batteryTemperatureLow() {
		assert(BatteryConditionCheck.batteryIsOk(-1, 70, 0.7f) == false);
	}
	
	@Test
	void batteryTemperatureHigh() {
		assert(BatteryConditionCheck.batteryIsOk(46, 70, 0.7f) == false);
	}
	
	@Test
	void batterySocHigh() {
		assert(BatteryConditionCheck.batteryIsOk(35, 81, 0.7f) == false);
	}
	
	@Test
	void batterySocLow() {
		assert(BatteryConditionCheck.batteryIsOk(35, 18, 0.7f) == false);
	}
	@Test
	void batteryChargeRateHigh() {
		assert(BatteryConditionCheck.batteryIsOk(35, 50, 1.7f) == false);
	}

}