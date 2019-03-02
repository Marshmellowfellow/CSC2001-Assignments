public class timeStamp implements Comparable<timeStamp>{
   	public String time;
   	public String global_active_power;
   	public String voltage;
   	timeStamp(String global_active_power, String voltage, String time){
   		this.time = time;
   		this.global_active_power = global_active_power;
   		this.voltage = voltage;
   	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getGlobal_active_power() {
		return global_active_power;
	}
	public void setGlobal_active_power(String global_active_power) {
		this.global_active_power = global_active_power;
	}
	public String getVoltage() {
		return voltage;
	}
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	@Override
	public int compareTo(timeStamp time) {
		String time1 = this.getTime().replaceAll("[/:.,]|12/2006/", "");
		String time2 = time.getTime().replaceAll("[/:.,]|12/2006/", "");
		int time1int = Integer.valueOf(time1);
		int time2int = Integer.valueOf(time2);
		if(time1int > time2int) 
			return 1;
		else
			return -1;
		
	}
	@Override
	public String toString() {
		return "timeStamp [time=" + time + ", global_active_power=" + global_active_power + ", voltage=" + voltage
				+ "]";
	}
}