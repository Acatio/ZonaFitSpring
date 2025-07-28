package moac.zonaFit;

import lombok.SneakyThrows;
import moac.zonaFit.modelo.Cliente;
import moac.zonaFit.servicio.ClienteServicio;
import moac.zonaFit.servicio.IClienteServicio;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner
{
    @Autowired
    private IClienteServicio clienteServicio;
    private static final Logger LOGGER = LoggerFactory.getLogger(ZonaFitApplication.class);

    public static void main(String[] args)
    {
        LOGGER.info("iniciando la aplicacion");
        //levantar la fabrica de spring
        SpringApplication.run(ZonaFitApplication.class, args);
        LOGGER.info("Aplicacion Finalizada");
    }

    @Override
    public void run(String... args) throws Exception
    {
        zonaFitApp();
    }

    private void mostrarMenu()
    {
        mostarMensaje("****** MENU ******");
        mostarMensaje("1.Agregar cliente");
        mostarMensaje("2.Modificar cliente");
        mostarMensaje("3.Eliminar cliente");
        mostarMensaje("4.Buscar cliente por id");
        mostarMensaje("5.Listar clientes");
        mostarMensaje("6.Salir");
    }

    private void mostarMensaje(String mensaje)
    {
        LOGGER.info(mensaje);
    }

    private int leerOpcion()
    {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            return Integer.parseInt(lector.readLine());
        } catch (IOException | NumberFormatException e)
        {
            LOGGER.info("Ocurrio un error al leer la opcion!!" + e.getMessage());
            return 0;
        }
    }

    @SneakyThrows
    private String leerEntrada() throws IOException
    {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
        return lector.readLine();
    }

    private void zonaFitApp()
    {
        boolean finalizada = false;
        while (!finalizada)
        {
            mostrarMenu();
            mostarMensaje("Seleccione una opcion: ");
            var opc = leerOpcion();
            switch (opc)
            {
                case 0 ->
                {
                    mostarMensaje("error al leer la opcion");
                }
                case 1 ->
                {
                    agregarCliente();
                }
                case 2 ->
                {
                    modificarCliente();
                }
                case 3 ->
                {
                    eliminarCliente();
                }
                case 4 ->
                {
                    buscarClientePorID();
                }
                case 5 ->
                {
                    listarClientes();
                }
                case 6 ->
                {
                    finalizada = true;
                }
                default ->
                {
                    mostarMensaje("Opcion invalida!!!");
                }

            }
        }
    }

    @SneakyThrows
    private String leerNombre() throws IOException
    {
        mostarMensaje("Ingrese su nombre: ");
        return leerEntrada();
    }

    @SneakyThrows
    private String leerApellido() throws IOException
    {
        mostarMensaje("Ingrese su apellido: ");
        return leerEntrada();
    }

    @SneakyThrows
    private Integer leerMembresia() throws IOException
    {
        mostarMensaje("Ingrese su membresia: ");
        return Integer.parseInt(leerEntrada());
    }

    @SneakyThrows
    private Integer leerID() throws IOException
    {
        mostarMensaje("Ingrese el ID: ");
        return Integer.parseInt(leerEntrada());
    }

    private void agregarCliente()
    {

        try
        {
            var nombre = leerNombre();
            var apellido = leerApellido();
            var membresia = leerMembresia();
            Cliente cliente = new Cliente();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setMembresia(membresia);
            clienteServicio.guardarCliente(cliente);
            mostarMensaje("Cliente agregado");
        } catch (IOException e)
        {
            mostarMensaje("Error al agregar al cliente: " + e.getMessage());
        }


    }

    private void modificarCliente()
    {

        try
        {
            mostarMensaje("Digite el id del cliente a modificar: ");
            var id = leerID();
            var cliente = clienteServicio.buscarClientePorId(id);
            if (cliente != null)
            {
                mostarMensaje("Inserte los nuevos datos: ");
                var nombre = leerNombre();
                var apellido = leerApellido();
                var membresia = leerMembresia();
                cliente.setId(id);
                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                cliente.setMembresia(membresia);
                clienteServicio.guardarCliente(cliente);
                mostarMensaje("Cliente modificado");
            } else
            {
                mostarMensaje("No se encontro un cliente con ese id!!");
            }
        } catch (IOException e)
        {
            mostarMensaje("Error al agregar al cliente: " + e.getMessage());
        }


    }

    private void eliminarCliente()
    {
        mostarMensaje("Ingrese el id del cliente a eliminar: ");
        try
        {
            var id = leerID();
            var cliente = clienteServicio.buscarClientePorId(id);
            if (cliente != null)
            {
                clienteServicio.elimiarCliente(cliente);
                mostarMensaje("Cliente eliminado");
            } else
            {
                mostarMensaje("No se encontro un cliente con ese id");
            }
        } catch (IOException e)
        {
            mostarMensaje("Error al leer el id: " + e.getMessage());
        }
    }

    private void buscarClientePorID()
    {
        mostarMensaje("Ingrese el id del cliente a buscar: ");
        try
        {
            var id = leerID();
            var cliente = clienteServicio.buscarClientePorId(id);
            if (cliente != null)
            {
                mostarMensaje("-----------Cliente buscado------");
                mostarMensaje(cliente.toString());
            } else
            {
                mostarMensaje("No se encontro el cliente con ese id!!");
            }
        } catch (IOException e)
        {
            mostarMensaje("ocurrio un error al leer el id: " + e.getMessage());
        }
    }

    private void listarClientes()
    {
        var listaClientes = clienteServicio.listarClientes();
        listaClientes.forEach(System.out::println);
    }

}
