package vue;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EntreeJeu extends JFrame {

	private JPanel contentPane;
	private JTextField txtIp;
	private Controle controle;
	
	/**
	 * Lance le serveur
	 */
	
	 private void btnStart_clic() {   
		 controle.evenementVue(this, "serveur" );
		 } 
	 
	 /**
	  * Connexion au serveur
	  */
	 
	 private void btnConnect_clic() {   
		 controle.evenementVue(this, txtIp.getText() ); 
		 }
	 
	 /**
	  * Fermer la fenêtre
	  */
	 
	 private void btnExit_clic() {   
		 System.exit(0);
		 }

	/**
	 * Create the frame.
	 * @param controle 
	 */
	public EntreeJeu(Controle controle) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 409, 274);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStartAServer = new JLabel("Start a server:");
		lblStartAServer.setBounds(43, 39, 103, 33);
		contentPane.add(lblStartAServer);
		
		JLabel lblConnectAnExisting = new JLabel("Connect an existing server:");
		lblConnectAnExisting.setBounds(53, 95, 171, 22);
		contentPane.add(lblConnectAnExisting);
		
		JLabel lblIpServer = new JLabel("IP server:");
		lblIpServer.setBounds(53, 152, 56, 16);
		contentPane.add(lblIpServer);
		
		txtIp = new JTextField();
		txtIp.setText("127.0.0.1");
		txtIp.setBounds(121, 149, 116, 22);
		contentPane.add(txtIp);
		txtIp.setColumns(10);
		
		JButton btnStart = new JButton("Start");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnStart_clic() ;
			}
		});
		btnStart.setBounds(201, 43, 97, 25);
		contentPane.add(btnStart);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnConnect_clic() ;
			}
		});
		
		btnConnect.setBounds(263, 148, 97, 25);
		contentPane.add(btnConnect);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnExit_clic() ;
			}
		});
		
		btnExit.setBounds(273, 186, 97, 25);
		contentPane.add(btnExit);
		
		this.controle = controle;
		
	}
}
