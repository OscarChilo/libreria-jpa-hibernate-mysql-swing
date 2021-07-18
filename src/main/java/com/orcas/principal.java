package com.orcas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

public class principal extends JFrame {

	private JPanel contentPane;
	private JTextField ttitulo;
	private JTextField tautor;
	private JTextField tprecio;
	private JTextField tid;
	
	static EntityManagerFactory factory;
	static EntityManager entityManager;
	private JTable table1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					principal frame = new principal();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	private static void begin() {
		factory=Persistence.createEntityManagerFactory("LibroUnit");
		entityManager=factory.createEntityManager();
		
		entityManager.getTransaction().begin();
	}
	
	private static void end() {
		entityManager.getTransaction().commit();
		entityManager.close();
		factory.close();
	}
	
	public void listarLibros(JTable tabla) {
		String titulo;
		String autor;
		float precio;
		DefaultTableModel model=new DefaultTableModel();
		ArrayList<Object> nombreColumna=  new ArrayList<>();

		String jpql="from Libro";		
		Query query=entityManager.createQuery(jpql);
		
		@SuppressWarnings("unchecked")
		List<Libro> listLibro=query.getResultList();
		
		nombreColumna.removeAll(nombreColumna);
		nombreColumna.add("ID_LIBRO");
		nombreColumna.add("TITULO");
		nombreColumna.add("AUTOR");
		nombreColumna.add("PRECIO");
		
		for(Object columna:nombreColumna) {
			model.addColumn(columna);
		}
		for(Libro a : listLibro) {
			model.addRow(new Object[] {a.getID(),a.getTitulo(),a.getAutor(),a.getPrecio()});
		}
		tabla.setModel(model);
		    
	}
	
	public void cargarTabla() {
		begin();
		listarLibros(table1);
		end();
	}
	

	/**
	 * Create the frame.
	 */
	public principal() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 511, 374);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LIBRERIA");
		lblNewLabel.setFont(new Font("Gulim", Font.BOLD, 16));
		lblNewLabel.setBounds(215, 11, 81, 24);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registrar Libro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 47, 224, 168);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("TITULO");
		lblNewLabel_1.setBounds(10, 34, 42, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("AUTOR");
		lblNewLabel_2.setBounds(10, 77, 46, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("PRECIO");
		lblNewLabel_3.setBounds(10, 122, 46, 14);
		panel.add(lblNewLabel_3);
		
		ttitulo = new JTextField();
		ttitulo.setBounds(55, 31, 142, 20);
		panel.add(ttitulo);
		ttitulo.setColumns(10);
		
		tautor = new JTextField();
		tautor.setBounds(55, 74, 142, 20);
		panel.add(tautor);
		tautor.setColumns(10);
		
		tprecio = new JTextField();
		tprecio.setBounds(55, 119, 142, 20);
		panel.add(tprecio);
		tprecio.setColumns(10);
		
		JButton guardar = new JButton("Guardar");
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				begin();
				
				String titulo,autor;
				float precio;
				
				titulo=ttitulo.getText();
				autor=tautor.getText();
				precio=Float.parseFloat(tprecio.getText());
				
				Libro newLibro=new Libro();
				
				newLibro.setTitulo(titulo);
				newLibro.setAutor(autor);
				newLibro.setPrecio(precio);
				
				entityManager.persist(newLibro);
				
				end();
				
				JOptionPane.showMessageDialog(null, "registrado con exito");
				
				ttitulo.setText("");
				tautor.setText("");
				tprecio.setText("");
				ttitulo.requestFocus();
				
				
				
			}
		});
		guardar.setBounds(10, 214, 73, 39);
		contentPane.add(guardar);
		
		JButton borrar = new JButton("Borrar");
		borrar.setBounds(82, 214, 73, 39);
		contentPane.add(borrar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(152, 214, 81, 39);
		contentPane.add(btnSalir);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Buscar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 264, 224, 60);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		tid = new JTextField();
		tid.setBounds(60, 21, 133, 20);
		tid.setColumns(10);
		panel_1.add(tid);
		
		JLabel lblNewLabel_1_1 = new JLabel("ID Libro");
		lblNewLabel_1_1.setBounds(10, 24, 52, 14);
		panel_1.add(lblNewLabel_1_1);
		
		JButton actualizar = new JButton("Actualizar");
		actualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cargarTabla();
				
			}
		});
		
		actualizar.setBounds(244, 253, 107, 47);
		contentPane.add(actualizar);
		
		JButton btnNewButton_1 = new JButton("Eliminar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				begin();
				
				Integer claveBusqueda;
				claveBusqueda=Integer.parseInt(tid.getText());
				Libro reference=entityManager.getReference(Libro.class, claveBusqueda);
				entityManager.remove(reference);
				
				end();
				
				JOptionPane.showMessageDialog(null, "El libro ha sido eliminado");
				tid.setText("");
			}
		});
		btnNewButton_1.setBounds(381, 253, 104, 47);
		contentPane.add(btnNewButton_1);
		
		table1 = new JTable();
		table1.setBounds(244, 46, 241, 207);
		contentPane.add(table1);
	}
}
