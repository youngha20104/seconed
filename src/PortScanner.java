import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class PortScanner {

	public static ExecutorService es = Executors.newFixedThreadPool(20);
	public static String[] iping = new String[256];
	public static String ip = "192.168.0.";
	public static int timeout = 200;

	public static void main(final String... args) throws InterruptedException {
		// TODO Auto-generated method stub
		for (int i = 0; i < 256; i++) {
			iping[i] = ip + i;
			new anggi(iping[i],i).start();
		}
		
	}

	public static Future<ScanResult> portIsOpen(final ExecutorService es, final String ip, final int port,
			final int timeout) {
		return es.submit(new Callable<ScanResult>() {
			@Override
			public ScanResult call() {
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(ip, port), timeout);
					socket.close();
					return new ScanResult(port, true);
				} catch (Exception ex) {
					return new ScanResult(port, false);
				}

			}
		});
	}

}

class anggi extends Thread {
	private String ip;
	private int index;
	anggi(String ip, int index) {
		this.index = index;
		this.ip = ip;
	}

	public void run() {
		List<Future<ScanResult>> futures = new ArrayList<>();
		for (int port = 1; port <= 1024; port++) {
			futures.add(PortScanner.portIsOpen(PortScanner.es, ip, port, PortScanner.timeout));
		}
		try {
			PortScanner.es.awaitTermination(200L, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean found = false;
		for (final Future<ScanResult> f : futures) {
			try {
				if (f.get().isOpen()) {
					System.out.println(f.get().getPort());
					found = true;
					network.salow[index][4] = f.get().getPort();
					break;
				}  /////msg[4]  =  
			} catch (Exception e) {
			}
		}
		if (found == false) {
			System.out.println("[n/a]");
			network.salow[index][4] = "[n/a]";
		}
		
	}
	
	
	
}

class ScanResult {
	private int port;

	private boolean isOpen;

	public ScanResult(int port, boolean isOpen) {
		super();
		this.port = port;
		this.isOpen = isOpen;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

}