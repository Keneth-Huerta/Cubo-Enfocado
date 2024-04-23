package keneth.sockets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente {

	public static void main(String[] args) {

		MarcoCliente mimarco = new MarcoCliente();
		mimarco.setVisible(true);
	}
}

class MarcoCliente extends JFrame {
	private static final long serialVersionUID = 9134190277217573927L;

	public MarcoCliente() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(600, 300, 280, 350);
		LaminaMarcoCliente milamina = new LaminaMarcoCliente();
		add(milamina);
		setTitle("Chat");
		addWindowListener(new EnvioOnline());
	}
}

//------------------------SEÑAL ONLINE---------------------
class EnvioOnline extends WindowAdapter {

	public void windowOpened(WindowEvent e) {

		try {

			Socket misocket = new Socket("192.168.100.12", 3535);
			PaqueteEnvio datos = new PaqueteEnvio();
			datos.setMensaje("1073046681444176685L");
			ObjectOutputStream paquetdDatos = new ObjectOutputStream(misocket.getOutputStream());
			paquetdDatos.writeObject(datos);
			misocket.close();
		} catch (Exception e2) {

		}
		super.windowOpened(e);
	}
}

//----------------------------------------------------------
class LaminaMarcoCliente extends JPanel implements Runnable {
	private static final long serialVersionUID = 1073046681444176685L;
	private JTextField cuadroT;
	JComboBox<String> ip;
	JLabel nick,text;
	JTextArea campoChat;

	public LaminaMarcoCliente() {

		nick = new JLabel(JOptionPane.showInputDialog("Cuá es tu nombre de usuario"));

		ip = new JComboBox<String>();
//		ip.addItem("Usuario1");
//		ip.addItem("Usuario2");
//		ip.addItem("Usuario3");
//		ip.addItem("192.168.0.172");
//		ip.addItem("192.168.0.220");
//		ip.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				System.out.println(ip.getSelectedItem());
//			}
//		});

		JLabel texto = new JLabel("Online: ");
		JLabel text = new JLabel("Usuario: ");
		JButton boton = new JButton("Enviar");
		cuadroT = new JTextField(20);
		campoChat = new JTextArea(12, 20);
		add(text);
		add(nick);
		add(texto);
		add(ip);
		add(campoChat);
		add(cuadroT);
		add(boton);
		boton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				campoChat.append(cuadroT.getText() + "\n");

				try {

					Socket misocket = new Socket("192.168.100.12", 3535);
					PaqueteEnvio datos = new PaqueteEnvio();
					datos.setNick(nick.getText());
					datos.setIp(ip.getSelectedItem().toString());
					datos.setMensaje(cuadroT.getText());
//					DataOutputStream flujoSalida = new DataOutputStream(misocket.getOutputStream());
//					flujoSalida.writeUTF(cuadorT.getText());
//					flujoSalida.close();
					ObjectOutputStream paqueteDatos = new ObjectOutputStream(misocket.getOutputStream());
					paqueteDatos.writeObject(datos);
					misocket.close();
				} catch (IOException e1) {

					e1.printStackTrace();
					System.out.println(e1.getMessage());
				}
				cuadroT.setText("");
			}
		});
		Thread mihilo = new Thread(this);
		mihilo.start();
	}

	public void run() {

		try {

			ServerSocket servidorCliente = new ServerSocket(5353);
			Socket cliente;
			PaqueteEnvio paqueteRecibido;
			while (true) {

				cliente = servidorCliente.accept();
				ObjectInputStream flujeEntrada = new ObjectInputStream(cliente.getInputStream());
				paqueteRecibido = (PaqueteEnvio) flujeEntrada.readObject();
				if (!paqueteRecibido.getMensaje().equals("1073046681444176685L")) {

					campoChat.append(paqueteRecibido.getNick() + ": " + paqueteRecibido.getMensaje() + "\n");
				} else {

//					campoChat.append(paqueteRecibido.getIps()+"\n");
					ArrayList<String> IpsMenu = new ArrayList<String>();
					IpsMenu = paqueteRecibido.getIps();
					ip.removeAllItems();
					for (String string : IpsMenu) {

						ip.addItem(string);
					}
				}
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

class PaqueteEnvio implements Serializable {
	private static final long serialVersionUID = -4487216796798719441L;
	private String nick, ip, mensaje;
	private ArrayList<String> Ips;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public ArrayList<String> getIps() {
		return Ips;
	}

	public void setIps(ArrayList<String> ips) {
		Ips = ips;
	}
}