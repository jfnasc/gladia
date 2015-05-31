import org.manekineko.services.ResultadoService;
import org.manekineko.services.impl.ResultadoMegaSenaService;

public class Main {
	public static void main(String[] args) {
		ResultadoService rs = new ResultadoMegaSenaService();

		int nroUltimoSorteio = rs.buscarNroUltimoSorteio();

		for (int i = nroUltimoSorteio - 9; i <= nroUltimoSorteio; i++) {
			System.out.println(rs.buscarSorteio(i));
		}

	}
}
