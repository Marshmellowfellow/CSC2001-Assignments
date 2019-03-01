public class timeStamp{
   	public String time;
   	public String global_active_power;
   	public String voltage;
   	timeStamp(String global_active_power, String voltage, String time){
   		this.time = time;
   		this.global_active_power = global_active_power;
   		this.voltage = voltage;
   	}
}