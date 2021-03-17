package vitals;

public class Main {
    public static void main(String[] args) {
		assert (BatteryConditionCheck.batteryIsOk(25, 70, 0.7f) == true);
		assert (BatteryConditionCheck.batteryIsOk(-1, 70, 0.7f) == false);
		assert (BatteryConditionCheck.batteryIsOk(46, 70, 0.7f) == false);
		assert (BatteryConditionCheck.batteryIsOk(35, 81, 0.7f) == false);
		assert (BatteryConditionCheck.batteryIsOk(35, 18, 0.7f) == false);
		assert (BatteryConditionCheck.batteryIsOk(35, 50, 1.7f) == false);
	}
}
