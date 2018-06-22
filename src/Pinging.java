import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pinging extends Thread{
	private Object[] msg;
	private String ip;
		
		public Pinging(String ip) {
			this.ip =ip;
			msg = new Object[5];
		}
		public void run() {    ////////////thread       
			InputStream is = null;
			BufferedReader br = null;
			try {
				Runtime run = Runtime.getRuntime();
				Process p = run.exec("ping -a "+ip);
			msg[0] = ip;
			is = p.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while((line = br.readLine()) != null) {
				if(line.indexOf("[") >= 0) {
					msg[3] = line.substring(5,line.indexOf("["));
				}
				if(line.indexOf("ms")>= 0 ) {
					msg[1] = line.substring(line.indexOf("ms")-1,line.indexOf("ms")+2);
					msg[2] = line.substring(line.indexOf("TTL=")+4,line.length());
					break;
				}	
			}
			}catch(Exception e) {
				
			}finally {
				try {
					if(br != null) br.close();
				}catch(Exception ex) {}
			try {
				if(is != null) is.close();
			}catch(Exception ex2) {}}}
		
		public Object[] getMsg() {
				try {
					join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //해당 thread가 종료될때까지 대기.
				return msg;
			}
		public static void main(String args[]) {
			Pinging[] pg = new Pinging[254];  //0~253
			
			for(int i = 0; i<254;i++) {
				pg[i] = new Pinging( "10.128.115."+(i+1));
				pg[i].start();
			}
			
			for(int i = 1;i<254;i++) {
				
				Object[] msg = pg[i].msg;
					if(msg == null) {
						System.out.println("Die");
					} else {
					System.out.println(msg[0] + ", "+ msg[1]+", "+msg[2]+", "+msg[3]);
					}
			}
		} //end main
}
