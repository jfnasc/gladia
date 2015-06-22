package org.ganimede;

import org.ganimede.services.DownloadResultadosService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    public static void main(String[] args) {

        ((DownloadResultadosService) context.getBean("downloadResultadosQuina")).processarResultados();
        ((DownloadResultadosService) context.getBean("downloadResultadosMegaSena")).processarResultados();
        ((DownloadResultadosService) context.getBean("downloadResultadosDuplaSena")).processarResultados();

        // CargaResultadosService cms = new CargaResultadosMegaSenaImpl();
        // cms.carregar();
        //
        // CargaResultadosService cqu = new CargaResultadosQuinaImpl();
        // cqu.carregar();
        //
        // CargaResultadosService cds = new CargaResultadosDuplaSenaImpl();
        // cds.carregar();
        //
        // AtrasosDAO dao = new AtrasosDAOImpl();
        // dao.registrarAtrasos(TiposConcurso.DUPLA_SENA.sigla, 1, 60);
        // dao.registrarAtrasos(TiposConcurso.DUPLA_SENA.sigla, 2, 60);
        // dao.registrarAtrasos(TiposConcurso.MEGA_SENA.sigla, 1, 60);
        // dao.registrarAtrasos(TiposConcurso.QUINA.sigla, 1, 80);
    }
}
