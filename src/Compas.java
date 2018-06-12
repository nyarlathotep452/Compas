import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;






public class Compas{
	
	
	public static void main(String [] args){
		
		
		ventana principal = new ventana();
		principal.setVisible(true);
	}
}

//creo el hilo para pintar por esperas 
class espera extends javax.swing.JPanel implements Runnable,Serializable{

    private int progreso = 0;
    private Thread hiloPrincipal = null;
    private int radio;
    private int posicionX; 
    private int posicionY;
    private ArrayList<Integer> posiciones = new ArrayList<>();
    private Color color = Color.blue;

    // tamaño del panel que va dentro del frame
    public espera(int ancho, int alto) {
        this.setSize(ancho, alto);
        this.setPosiciones(this.getWidth()/2, this.getHeight()/2);
        this.setBackground(new Color(255,0,0));
    }
    //manda las las ubicaciones donde se dibujara dentro del ´panel
    public espera(){
    	this.setSize(200, 200);
    	this.posicionX = this.getWidth()/2;
    	this.posicionY = this.getHeight()/2;
    	
    }
    //metodo para agarrar el color que se usara
    public Color getColor(){
    	return this.color;
    }
    //metodo para obtener el radio que tendra el circulo
    public int getRadioCirculo(){
    	return this.radio;
    }
    //metodo para obtener el punto en x donde se posicionara el medio
    public int getPosicionX(){
    	return this.posicionX;
    }
    //metodo para obtener el punto en y donde se posicionara el medio
    public int getPosicionY(){
    	return this.posicionY;
    }
    
    public ArrayList<Integer> getCentro(){
    	this.posiciones.clear();
    	this.posiciones.add(this.posicionX);
    	this.posiciones.add(this.posicionY);
    	return this.posiciones;
    }
    
    
    public void setPosiciones(int posicionX,int posicionY){
    	this.posicionX = posicionX;
    	this.posicionY  = posicionY;
    }
    public void setPosicionX(int posicionX){
    	this.posicionX = posicionX;
    }
    
    public void setPosicionY(int posicionY){
    	this.posicionY = posicionY;
    }
    public void setRadio(int radio){
    	this.radio = radio;
    }
    public void setColor(Color color){
    	this.color = color;
    }
    

    // metodo de pintado de la pantalla 
    public void paint(Graphics g) {
    	this.setSize(this.getWidth()+this.radio,this.getHeight()+this.radio);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(this.posicionX, this.posicionY);
        g2.rotate(Math.toRadians(270));

        Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
        Ellipse2D circulo = new Ellipse2D.Float(0, 0, 110, 110);
        Ellipse2D circulo1 = new Ellipse2D.Float(0, 0, 110, 110);

        arc.setFrameFromCenter(new Point(0, 0), new Point(this.radio, this.radio));
        circulo.setFrameFromCenter(new Point(0, 0), new Point(this.radio-5, this.radio-5));
        circulo1.setFrameFromCenter(new Point(0, 0), new Point(this.radio, this.radio));

        arc.setAngleStart(1);
        arc.setAngleExtent(-this.progreso * 3.6);
        
        g2.setColor(this.color);
        g2.draw(arc);
        g2.fill(arc);

        g2.setColor(Color.WHITE);
        g2.draw(circulo);
        g2.fill(circulo);
        this.setOpaque(false);
    }
    //evento propio
    public void dibujar(){
    	if(this.hiloPrincipal == null){
    		this.hiloPrincipal = new Thread(this);
    		this.hiloPrincipal.start();
    	}
    }
    

    @Override
    // metodo de la funcion en la cual tiene la funcion del hilo
    public void run() {
        try {
            //int tiempo = (int) (Math.random() * 100) + 1;
            for (int i = 1; i <= 100; i++) {
                this.progreso = i;
                this.repaint();
                Thread.sleep(70);
            }
            this.hiloPrincipal = null;

        } catch (Exception e) {
        	
        }
    }
    
}

class ventana extends JFrame{
    
	   private JLabel titulo;
	   private JLabel dato1;
	   private JLabel dato2;
	   private JTextField Tdato1;
	   private JTextField Tdato2;
	   private JButton boton;
	   private JPanel panel1;
	   private JPanel panel2;
	   private espera panel;
       private Connection con; 
       private conexion conex = new conexion();
       
	
	public ventana() {
		
		this.con=this.conex.getConexion();
		this.init();
		
		
	}
	//metodo para poner el dibujado en paneles e imprimirlos en pantalla
	public void init() {
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setSize(900,400);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		
		
		
		
		this.panel = new espera();
		
		this.panel1 = new JPanel();
		this.panel1.setBackground(Color.LIGHT_GRAY);
		this.panel1.setLayout(null);
		
		
		this.panel1.setBounds(0, 0, 300,400);
		
		JLabel titulo = new JLabel("ingrese datos:");
        titulo.setBounds(0, 90, 120, 40);
        this.panel1.add(titulo);
        
		JLabel dato1 =new JLabel("ingrese x:");
		dato1.setBounds(0,120,120,40);
		this.panel1.add(dato1);
		
		JLabel dato2 = new JLabel("ingrese y:");
		dato2.setBounds(0,150,120,40);
		this.panel1.add(dato2);
		
		JLabel dato3 = new JLabel("ingrese el radio:");
		dato3.setBounds(0,180,120,40);
		this.panel1.add(dato3);
		
		JTextField Tdato1 = new JTextField("");
		Tdato1.setBounds(130,120,120,30);
		this.panel1.add(Tdato1);
		
		JTextField Tdato2 = new JTextField("");
		Tdato2.setBounds(130,150,120,30);
		this.panel1.add(Tdato2);
		
		JTextField Tdato3 = new JTextField("");
		Tdato3.setBounds(130,180,120,30);
		this.panel1.add(Tdato3);
		
		JButton boton = new JButton("aceptar");
		boton.setBounds(30,210,120,40);
		
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				int posx = Integer.parseInt(Tdato1.getText());
				int posy = Integer.parseInt(Tdato2.getText());
				int radio = Integer.parseInt(Tdato3.getText());
				panel.setPosiciones(posx, posy);
				panel.setRadio(radio);
				panel2.add(panel);
				panel.dibujar();
				
				try {
					
					String sql = "INSERT INTO datos (x,y,radio) VALUES (?,?,?)";
					PreparedStatement pst = con.prepareStatement(sql);
					pst.setInt(1, posx);
					pst.setInt(2, posy);
					pst.setInt(3, radio);
					
					pst.executeUpdate();
				}catch(Exception e){
					e.printStackTrace();
					
				}
				

				
			}
			
		});
		
		this.panel1.add(boton);
		this.add(panel1);
		
		
		this.panel2 = new JPanel();
		this.panel2.setBackground(Color.WHITE);
		this.panel2.setBounds(300, 0, 600,400);
		this.panel2.setLayout(null);
		
		this.add(panel2);
		
		
	}
	
}
// crear evento propio, visualizacion de los datos de la base de datos, separado el componente del frame, lusar el listener e implementarlo como el documento




