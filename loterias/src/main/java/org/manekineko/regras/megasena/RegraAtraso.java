/**
 * 
 */
package org.manekineko.regras.megasena;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.manekineko.TiposSorteio;
import org.manekineko.regras.Regra;
import org.manekineko.regras.RegraBase;

/**
 * @author josen
 * 
 */
public class RegraAtraso extends RegraBase implements Regra {

	private Logger LOGGER = Logger.getLogger(RegraAtraso.class);
	
	/**
	 * 
	 */
	private static final int ATRASO = 4;

	@Override
	public void aplicar(List<Integer[]> p) {

		if (p == null || p.size() == 0) {
			return;
		}

		Float total = Float.valueOf(p.size());

		try {

			List<Integer[]> aRemover = new ArrayList<Integer[]>();

			// senas
			for (int i = 0; i < p.size(); i++) {

				boolean manter = false;

				for (Integer dezena : p.get(i)) {
					if ((manter = getResultadoDAO().isDezenaEmAtraso(TiposSorteio.MEGASENA.sigla, ATRASO, dezena)) == false) {
						break;
					}
				}

				if (!manter) {
					aRemover.add(p.get(i));
				}
			}

			p.removeAll(aRemover);

			LOGGER.debug("RegraAtraso: " + (Float.valueOf(aRemover.size()) / total * 100));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
