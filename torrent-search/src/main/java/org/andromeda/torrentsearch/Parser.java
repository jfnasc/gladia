package org.andromeda.torrentsearch;

import java.util.List;

public interface Parser {
	List<TorrentDTO> listar(String nomeSerie);
}
