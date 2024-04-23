package keneth.sockets;
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Servidor {

	public static void main(String[] args) {

		MarcoServidor mimarco = new MarcoServidor();
		mimarco.setVisible(true);
	}
}

class MarcoServidor extends JFrame implements Runnable {
	private static final long serialVersionUID = -4046424484065636750L;
	JTextArea area;

	public MarcoServidor() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(1200, 300, 280, 350);
		JPanel milamina = new JPanel();
		milamina.setLayout(new BorderLayout());
		area = new JTextArea();
		milamina.add(area);
		add(milamina);
		setTitle("Servidor");
		Thread mihilo = new Thread(this);
		mihilo.start();
	}

	public void run() {

		try {

			ServerSocket servidor = new ServerSocket(3535);

			String nick, ip, mensaje;
			ArrayList<String> listaIp = new ArrayList<String>();
			PaqueteEnvio paqueteRecibido;

			while (true) {

				Socket misocket = servidor.accept();

//				DataInputStream flujoEntrada = new DataInputStream(misocket.getInputStream());
//				String mensajeTexto = flujoEntrada.readUTF();
//				area.append(mensajeTexto + "\n");
				ObjectInputStream paqueteDatos = new ObjectInputStream(misocket.getInputStream());
				paqueteRecibido = (PaqueteEnvio) paqueteDatos.readObject();

				nick = paqueteRecibido.getNick();
				ip = paqueteRecibido.getIp();
				mensaje = paqueteRecibido.getMensaje();

				if (!mensaje.equals("1073046681444176685L")) {

					area.append(nick + ": " + mensaje + " para " + ip + "\n");
					Socket enviaDestinatario = new Socket(ip, 5353);
					ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
					paqueteReenvio.writeObject(paqueteRecibido);

					paqueteReenvio.close();
					enviaDestinatario.close();
					misocket.close();
				} else {

					// ------------------------DETECTA ONLINE---------------------

					InetAddress localizacion = misocket.getInetAddress();
					String ipRemota = localizacion.getHostAddress();
//					System.out.println(ipRemota);
					listaIp.add(ipRemota);
					paqueteRecibido.setIps(listaIp);
					for (String string : listaIp) {
						
//						System.out.println(string);
						
						Socket enviaDestinatario = new Socket(string, 5353);
						ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
						paqueteReenvio.writeObject(paqueteRecibido);
						
						paqueteReenvio.close();
						enviaDestinatario.close();
						misocket.close();
					}

					// ------------------------------------------------------------
				}
			}
		} catch (IOException | ClassNotFoundException e) {

			e.printStackTrace();
		}
	}
}
