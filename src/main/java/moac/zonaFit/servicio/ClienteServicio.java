package moac.zonaFit.servicio;

import moac.zonaFit.modelo.Cliente;
import moac.zonaFit.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServicio implements IClienteServicio
 {
     @Autowired
     private ClienteRepositorio clienteRepositorio;

     @Override
     public List<Cliente> listarClientes()
     {
         return clienteRepositorio.findAll();
     }

     @Override
     public Cliente buscarClientePorId(Integer id)
     {
         return clienteRepositorio.findById(id).orElse(null);
     }

     @Override
     public void guardarCliente(Cliente cliente)
     {
            clienteRepositorio.save(cliente);
     }

     @Override
     public void elimiarCliente(Cliente cliente)
     {
        clienteRepositorio.delete(cliente);
     }
 }
