import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

public class network extends JFrame {
	public static Font myFont = new Font("Serif",Font.BOLD,16);
	public static JPanel p1,p2,p3,p1_3,p5;
	public static JLabel b1,b2,b3,jb1,jb2,jb3;
	public static JComboBox jc1,jc2;
	public static JTextField t1,t2,t3;
	public static JButton bt1,bt2;
	public static JMenu Scan,Go_to,Commands,Favorites,Tools,Help;
    public static String option[] = { "IP", "Ping", "TTL","Hostname","Posrts[4+]"}; // JTable
    public static Object[][] salow = new Object[256][5];
    public static JTable jt1;
    public static JScrollPane js1 ;
    public static JToolBar bar1,bar2;
	public static ExecutorService es = Executors.newFixedThreadPool(20);
	public static String[] iping = new String[256];
	public static String ip = "192.168.0.";
	public static int timeout = 200;
	network() {
		super("Network Scanner");
		
		 p5 = new JPanel(new FlowLayout(FlowLayout.LEFT));  //상태바3개  Frame.South
		 p5.setBorder(new BevelBorder(BevelBorder.LOWERED));
		 add(p5,BorderLayout.SOUTH);
		 jb1 = new JLabel("Ready");
		 jb2 = new JLabel("Display:All");
		 jb3 = new JLabel("Threads:0");
		 p5.add(jb1);
		 p5.add(jb2);            //상태바 나중에 
		 p5.add(jb3);
		 jb1.setBorder(new BevelBorder(BevelBorder.RAISED));
		 jb2.setBorder(new BevelBorder(BevelBorder.RAISED));
		 jb3.setBorder(new BevelBorder(BevelBorder.RAISED));
		 jb1.setPreferredSize(new Dimension(300,20));
		 jb2.setPreferredSize(new Dimension(150,20));
		 jb3.setPreferredSize(new Dimension(150,20));
		 JMenuBar menu1 = new JMenuBar();  //만든후 jFrame에 붙임
		 
		 Scan = new JMenu("Scan");
		 Scan.setMnemonic('s');
		 Go_to = new JMenu("Go to");
		 Go_to.setMnemonic('G');
		 Commands = new JMenu("Commands");
		 Commands.setMnemonic('C');
		 Favorites = new JMenu("Favorites");    //메뉴바에 넣을거
		 Favorites.setMnemonic('F');
		 Tools = new JMenu("Tools");
		 Tools.setMnemonic('T');
		 Help = new JMenu("Help");
		 Help.setMnemonic('H');
		 menu1.add(Scan);
		 menu1.add(Go_to);
		 menu1.add(Commands);                 //메뉴바에 붙임
		 menu1.add(Favorites);
		 menu1.add(Tools);
		 menu1.add(Help);
		 
		 JMenuItem load = new JMenuItem("Load from file...");
		 JMenuItem Export1 = new JMenuItem("Export all");
		 JMenuItem Export2 = new JMenuItem("Export selection");      //세부 옵션
		 JMenuItem Quit = new JMenuItem("Quit");
		 Scan.add(load);
		 Scan.add(Export1);
		 Scan.add(Export2);
		 Scan.addSeparator();
		 Scan.add(Quit);
		 
		 
		 JMenuItem alive = new JMenuItem("Next alive host");
		 JMenuItem open= new JMenuItem("Next open port");
		 JMenuItem dead= new JMenuItem("Next dead host");
		 JMenuItem previousalive= new JMenuItem("Previous alive host");
		 JMenuItem Previousopen= new JMenuItem("Previous open port");
		 JMenuItem Previousdead= new JMenuItem("Previous dead host");
		 JMenuItem Find= new JMenuItem("find....");
		 Go_to.add(alive);
		 Go_to.add(open);
		 Go_to.add(dead);
		 Go_to.addSeparator();
		 Go_to.add(previousalive);
		 Go_to.add(Previousopen);
		 Go_to.add(Previousdead);
		 Go_to.addSeparator();
		 Go_to.add(Find);
		 
		 
		 JMenuItem show= new JMenuItem("Show details");
		 JMenuItem rescan= new JMenuItem("Rescan IP(s)");
		 JMenuItem delete= new JMenuItem("Delete IP(s)");
		 JMenuItem copycip= new JMenuItem("Copy IP");
		 JMenuItem copydetails= new JMenuItem("Copy details");
		 JMenuItem open1= new JMenuItem("Open");
		 Commands.add(show);
		 Commands.addSeparator();
		 Commands.add(rescan);
		 Commands.add(delete);
		 Commands.addSeparator();
		 Commands.add(copycip);
		 Commands.add(copydetails);
		 Commands.addSeparator();
		 Commands.add(open1);
		 
		 
		 JMenuItem addcurrent= new JMenuItem("Add current...");
		 JMenuItem manage= new JMenuItem("Manage favorites");
		 Favorites.add(addcurrent);
		 Favorites.add(manage);
		 
		 
		 JMenuItem preferences = new JMenuItem("Preferences...");
		 JMenuItem fetchers= new JMenuItem("Fetchers...");
		 JMenuItem selection = new JMenuItem("Selection");
		 JMenuItem scanstatistics = new JMenuItem("Scan statistics");
		 Tools.add(preferences);
		 Tools.add(fetchers);
		 Tools.addSeparator();
		 Tools.add(selection);
		 Tools.add(scanstatistics);
		 
		 
		 JMenuItem getting= new JMenuItem("getting Startes");
		 JMenuItem officialc= new JMenuItem("Official Website");
		 JMenuItem FAQ= new JMenuItem("FAQ");
		 JMenuItem report= new JMenuItem("Report an issue");
		 JMenuItem plugins= new JMenuItem("Plugins");
		 JMenuItem commandline= new JMenuItem("Command-line usage");
		 JMenuItem check= new JMenuItem("Check for newer version...");
		 JMenuItem about= new JMenuItem("About");
		 Help.add(getting);
		 Help.addSeparator();
		 Help.add(officialc);
		 Help.add(FAQ);
		 Help.add(report);
		 Help.add(plugins);
		 Help.addSeparator();
		 Help.add(commandline);
		 Help.addSeparator();
		 Help.add(check);
		 Help.addSeparator();
		 Help.add(about);
		 
		 
		 Quit.addActionListener(new ActionListener() {  //끄는거 	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		 
		 setJMenuBar(menu1);    //Jframe에 붙임
		 
		 
		 //Jtoolbar이빈다
		 bar1 = new JToolBar();
		 bar1.setLayout(new FlowLayout(FlowLayout.LEFT));
		 bar2 = new JToolBar();
		 bar2.setLayout(new FlowLayout(FlowLayout.LEFT));
		 
		 b1 = new JLabel("IP Range:");
		 b2 = new JLabel("to");							//Label
		 b3 = new JLabel("Hostname");
		 
		 t1 = new JTextField(10);
		 t2 = new JTextField(10); 				//JTextField
		 t3 = new JTextField(10);
		 t1.setPreferredSize(new Dimension(90,20));
		 t2.setPreferredSize(new Dimension(90,20));
		 t3.setPreferredSize(new Dimension(90,20));
		 bt1 = new JButton("IP");
		 JComboBox cb = new JComboBox();
		 cb.addItem("/24");
		 cb.addItem("/26");
		 bt2 = new JButton("Start");
		 bt2.setPreferredSize(new Dimension(90,20));
		 cb.setPreferredSize(new Dimension(90,20));
		 b3.setFont(myFont);
		 
		 bar1.add(b1);
		 bar1.add(t1);
		 bar1.add(b2);
		 bar1.add(t2);
		 
		 bar2.add(b3);
		 bar2.add(t3);
		 bar2.add(bt1);
		 bar2.add(cb);
		 bar2.add(bt2);
		 
		 JPanel panel1 = new JPanel(new BorderLayout());
		 panel1.add(bar1,BorderLayout.NORTH);
		 panel1.add(bar2,BorderLayout.SOUTH);
		 
		 add(panel1,BorderLayout.NORTH);
		 
		 jt1 = new JTable(salow,option);
		 js1 = new JScrollPane(jt1);
		 add(js1,BorderLayout.CENTER);
		 
		 String myip = null;
		 String myhostname = null;
		 
		 InetAddress ia;
		try {
			ia = InetAddress.getLocalHost();
			myip = ia.getHostAddress();
			 myhostname = ia.getHostName();
		} catch (UnknownHostException e) {}
		String fixedip = myip.substring(0,myip.lastIndexOf(".")+1);
		t1.setText(fixedip+"1");
		t2.setText(fixedip+"255");
		t3.setText(myhostname);
		 
		
		 		 
		 setSize(700,700);
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 
		 bt2.addActionListener(new ActionListener() {  ///////////////////////Action
			
			 @Override 
			 public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 Pinging[] pg = new Pinging[254];  //0~253
					for(int i = 1; i<254;i++) {
						pg[i] = new Pinging( fixedip + i);
						pg[i].start();
					}
					try {
						PortScanner.main(null);
					} catch (InterruptedException e1) {
						
					}
					for (int i = 1; i < 255; i++) {
						iping[i] = ip + i;
						new anggi(iping[i],i).start();
					}
					for(int i = 1;i<254;i++) {
						Object[] msg = pg[i].getMsg();
						if(msg[1]== null) {
							msg[1] = "[n/a]";
							msg[2] = "[n/s]";      
							msg[3] = "[n/s]";
							msg[4] = "[n/s]";
						}else if(msg[3]== null) {
							msg[3] = "[n/a]";
							msg[4] = "[n/s]";
						}
						//if(msg[1] != null) {
						//port scanner 시작부분
						
						
						
						
					//	}
						salow[i][0] = msg[0];
						salow[i][1] = msg[1];
						salow[i][2] = msg[2];
						salow[i][3] = msg[3];
						//portscanner 넣는곳 
					}
					jt1.repaint();
				} //end ping
//완료 했다고 창 떠야됨 Diolog
			 	
			 	
			 	
			 
				});
	}
		public static void main(String[] args) {
			new network().setVisible(true);
		}
}
