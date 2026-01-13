package moac.zonaFit.controlador;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import moac.zonaFit.modelo.Cliente;
import moac.zonaFit.servicio.IClienteServicio;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ViewScoped
public class IndexControlador
{
    @Autowired
    IClienteServicio clienteServicio;
    private List<Cliente> clientes;
    private Cliente clienteSeleccionado;
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @PostConstruct
    public void init()
    {
        cargarDatos();
    }

    public void cargarDatos()
    {
        this.clientes = clienteServicio.listarClientes();
        this.clientes.forEach(cliente -> logger.info(cliente.toString()));
    }

    public void agregarCliente()
    {
        this.clienteSeleccionado = new Cliente();
    }

    public void guardarCliente()
    {
        logger.info("cliente a guardar: " + this.clienteSeleccionado);
        //Agregar
        if (this.clienteSeleccionado.getId() == null)
        {
            this.clienteServicio.guardarCliente(this.clienteSeleccionado);
            this.clientes.add(clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente agregado"));
        }
        //Modificar
        else
        {
            this.clienteServicio.guardarCliente(this.clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente actualizado"));
        }
        //ocultar ventana modal cliente
        PrimeFaces.current().executeScript("PF('ventanaModalCliente').hide()");
        //actualizar tabla usando ajax
        PrimeFaces.current().ajax().update("forma-clientes:mensajes", "forma-clientes:clientes-tabla");
        this.clienteSeleccionado = null;
    }

    public void eliminarCliente()
    {

        logger.info("Cliente a elimminar: " + this.clienteSeleccionado);
        this.clienteServicio.elimiarCliente(this.clienteSeleccionado);
        //eliminar el registro de la lista de clientes de memoria
        this.clientes.remove(this.clienteSeleccionado);
        this.clienteSeleccionado = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cliente ELiminado"));
        PrimeFaces.current().ajax().update("forma-clientes:mensajes", "forma-clientes:clientes-tabla");
    }
}
