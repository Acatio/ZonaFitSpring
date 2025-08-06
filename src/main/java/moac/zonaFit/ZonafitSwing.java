package moac.zonaFit;
import com.formdev.flatlaf.FlatDarculaLaf;
import moac.zonaFit.gui.ZonaFitForma;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
@SpringBootApplication
public class ZonafitSwing
{
    public static void main(String[] args)
    {
        //configurar el modo oscuro
        FlatDarculaLaf.setup();

        //instanciar fabrica de spring
        ConfigurableApplicationContext contextoSpring =
                new SpringApplicationBuilder(ZonafitSwing.class)
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);
        //cerar un objeto de swing
        SwingUtilities.invokeLater(() ->
        {
           ZonaFitForma zonafitForma= contextoSpring.getBean(ZonaFitForma.class);
           zonafitForma.setVisible(true);
        });
    }
}
