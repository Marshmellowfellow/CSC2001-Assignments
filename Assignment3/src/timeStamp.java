
public class timeStamp implements Comparable<timeStamp>{
	
   	public String time;
   	public String global_active_power;
   	public String voltage;

   	timeStamp(String global_active_power, String voltage, String time){
   		this.time = time;
   		this.global_active_power = global_active_power;
   		this.voltage = voltage;
   	}
   	
   	//Getters and setters
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
	
	public int isPrime(int setSize) {
        int prime = 0;  //next prime will be assigned to this var
        for(int j = setSize; j<1000; j++){  //outer loop

              int count = 0;
              for(int i=2; i<=j/2; i++){  //inner loop

                    if(j%i==0){
                       count++;
                    }                      
              }
              if(count==0){

                    prime = j;   //assign next prime
                    return prime;
              }
        }
        return setSize;
	}
}