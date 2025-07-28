package moac.zonaFit.repositorio;

import moac.zonaFit.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente,Integer>
{
}
