package moac.zonaFit.servicio;

import moac.zonaFit.modelo.Cliente;
import java.util.List;

public interface IClienteServicio
{
    public List<Cliente> listarClientes();
    public Cliente buscarClientePorId(Integer id);
    public void guardarCliente(Cliente cliente);
    public void elimiarCliente(Cliente cliente);
    
}
