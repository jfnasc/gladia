package org.ganimede;

import org.ganimede.dao.AtrasosDAO;
import org.ganimede.dao.impl.AtrasosDAOImpl;
import org.ganimede.services.CargaResultadosService;
import org.ganimede.services.DownloadResultadosService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    public static void main(String[] args) {

        AtrasosDAO dao = new AtrasosDAOImpl();

        ((DownloadResultadosService) context.getBean("downloadResultadosMegaSena")).processarResultados();
        ((CargaResultadosService) context.getBean("cargaResultadosMegaSena")).carregar();

        dao.registrarAtrasos(TiposConcurso.MEGA_SENA, 1);

        // ((DownloadResultadosService)
        // context.getBean("downloadResultadosQuina")).processarResultados();
        // ((CargaResultadosService)
        // context.getBean("cargaResultadosQuina")).carregar();
        //
        // dao.registrarAtrasos(TiposConcurso.QUINA, 1);
        //
        // ((DownloadResultadosService)
        // context.getBean("downloadResultadosDuplaSena")).processarResultados();
        // ((CargaResultadosService)
        // context.getBean("cargaResultadosDuplaSena")).carregar();
        //
        // dao.registrarAtrasos(TiposConcurso.DUPLA_SENA, 1);
        // dao.registrarAtrasos(TiposConcurso.DUPLA_SENA, 2);
        //

        ((DownloadResultadosService) context.getBean("downloadResultadosLotoFacil")).processarResultados();
        ((CargaResultadosService) context.getBean("cargaResultadosLotoFacil")).carregar();
        dao.registrarAtrasos(TiposConcurso.LOTO_FACIL, 1);

    }
}
