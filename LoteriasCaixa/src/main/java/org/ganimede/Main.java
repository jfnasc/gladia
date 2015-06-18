package org.ganimede;

import org.ganimede.dao.AtrasosDAO;
import org.ganimede.dao.impl.AtrasosDAOImpl;
import org.ganimede.services.CargaResultadosService;
import org.ganimede.services.DownloadResultadosService;
import org.ganimede.services.impl.CargaResultadosDuplaSenaImpl;
import org.ganimede.services.impl.CargaResultadosMegaSenaImpl;
import org.ganimede.services.impl.CargaResultadosQuinaImpl;
import org.ganimede.services.impl.DownloadResultadosDuplaSena;
import org.ganimede.services.impl.DownloadResultadosMegaSena;
import org.ganimede.services.impl.DownloadResultadosQuina;

public class Main {
    public static void main(String[] args) {

        DownloadResultadosService ms = new DownloadResultadosMegaSena();
        ms.processarResultados();

        DownloadResultadosService qu = new DownloadResultadosQuina();
        qu.processarResultados();

        DownloadResultadosService ds = new DownloadResultadosDuplaSena();
        ds.processarResultados();

        CargaResultadosService cms = new CargaResultadosMegaSenaImpl();
        cms.carregar();

        CargaResultadosService cqu = new CargaResultadosQuinaImpl();
        cqu.carregar();

        CargaResultadosService cds = new CargaResultadosDuplaSenaImpl();
        cds.carregar();

        AtrasosDAO dao = new AtrasosDAOImpl();
        dao.registrarAtrasos(TiposConcurso.DUPLA_SENA.sigla, 1, 60);
        dao.registrarAtrasos(TiposConcurso.DUPLA_SENA.sigla, 2, 60);
        dao.registrarAtrasos(TiposConcurso.MEGA_SENA.sigla, 1, 60);
        dao.registrarAtrasos(TiposConcurso.QUINA.sigla, 1, 80);
    }
}
