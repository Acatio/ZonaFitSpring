package moac.zonaFit.gui;

import moac.zonaFit.modelo.Cliente;
import moac.zonaFit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ZonaFitForma extends JFrame
{
    private JPanel panelPrincipal;
    private JTable jtbClientes;
    private JTextField jtfNombre;
    private JTextField jtfApellido;
    private JTextField jtfMembresia;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton limpiarButton;
    IClienteServicio clienteServicio;
    private DefaultTableModel modeloTablaClientes;
    private Integer idCliente;

    @Autowired
    public ZonaFitForma(IClienteServicio clienteServicio)
    {
        this.clienteServicio = clienteServicio;
        iniciarForma();
        btnGuardar.addActionListener(e ->
        {
            guardarCliente();
        });
        jtbClientes.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                cargarClienteSeleccionado();
            }
        });
        btnEliminar.addActionListener(e -> eliminarRegistro());
        limpiarButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                limpiarCampos();
            }
        });
    }

    private void eliminarRegistro()
    {
        if (idCliente != null)
        {
            var cliente = new Cliente();
            cliente.setId(idCliente);
            clienteServicio.elimiarCliente(cliente);
            mostrarMensaje("Cliente eliminado con exito");
            limpiarCampos();
            listarClientes();
        } else
        {
            mostrarMensaje("Debe seleccionar un cliente para elimiarlo");
        }
    }

    private void cargarClienteSeleccionado()
    {
        var renglonSeleccionado = jtbClientes.getSelectedRow();
        if (renglonSeleccionado != -1)
        {
            var id = jtbClientes.getModel().getValueAt(renglonSeleccionado, 0).toString();
            var nombre = jtbClientes.getModel().getValueAt(renglonSeleccionado, 1).toString();
            var apellido = jtbClientes.getModel().getValueAt(renglonSeleccionado, 2).toString();
            var membresia = jtbClientes.getModel().getValueAt(renglonSeleccionado, 3).toString();
            idCliente = Integer.parseInt(id);
            jtfNombre.setText(nombre);
            jtfApellido.setText(apellido);
            jtfMembresia.setText(membresia);

        }
    }

    private void guardarCliente()
    {
        var nombre = jtfNombre.getText();
        var membresia = jtfMembresia.getText();
        var apellido = jtfApellido.getText();
        if (nombre.isBlank())
        {
            mostrarMensaje("Debes proporcionar el nombre");
            jtfNombre.requestFocus();
            return;
        }
        if (membresia.isBlank())
        {
            mostrarMensaje("Debes proporcionar la membresia");
            jtfNombre.requestFocus();
            return;
        }
        try
        {
            Integer membresiaInt = Integer.parseInt(membresia);
            Cliente nuevoCliente = new Cliente(idCliente, nombre, apellido, membresiaInt);
            clienteServicio.guardarCliente(nuevoCliente);
            if (idCliente == null) mostrarMensaje("Cliente agregado con éxito");
            else mostrarMensaje("Cliente Modificado con éxito");
            limpiarCampos();
            listarClientes();
        } catch (NumberFormatException e)
        {
            mostrarMensaje("La membresia debe ser un numero");
            jtfMembresia.requestFocus();
        }
    }

    private void limpiarCampos()
    {
        jtfMembresia.setText("");
        jtfNombre.setText("");
        jtfApellido.setText("");
        idCliente = null;
        jtbClientes.clearSelection();
    }

    private void mostrarMensaje(String mensaje)
    {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void iniciarForma()
    {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents()
    {
        // TODO: place custom component creation code here
        // modeloTablaClientes = new DefaultTableModel(0, 4);
        modeloTablaClientes = new DefaultTableModel(0, 4)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        String[] cabeceras = {"Id", "Nombre", "Apellido", "Membresia"};
        modeloTablaClientes.setColumnIdentifiers(cabeceras);
        jtbClientes = new JTable(modeloTablaClientes);
        jtbClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarClientes();

    }

    private void listarClientes()
    {
        modeloTablaClientes.setRowCount(0);
        var clientes = clienteServicio.listarClientes();
        clientes.forEach(cliente ->
        {
            Object[] renglonCliente = {cliente.getId(), cliente.getNombre(), cliente.getApellido(), cliente.getMembresia()};
            modeloTablaClientes.addRow(renglonCliente);
        });
    }

}
