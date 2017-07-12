/**
 * 
 */
package org.ganimede;

/**
 * @author Administrador
 * 
 */
public enum TiposConcurso {
    LOTO_FACIL("LF", "Quina", 25, 15, 1), //
    QUINA("QN", "Quina", 80, 5, 1), //
    DUPLA_SENA("DS", "Dupla Sena", 60, 6, 2), //
    MEGA_SENA("MS", "Mega Sena", 60, 6, 1);

    public String sigla;
    public String nome;
    public int nuDezenas;
    public int maiorFaixaPremiavel;
    public int qtSorteios;

    private TiposConcurso(String sigla, String nome, int nuDezenas, int maiorFaixaPremiavel, int qtSorteios) {
        this.sigla = sigla;
        this.nome = nome;
        this.nuDezenas = nuDezenas;
        this.maiorFaixaPremiavel = maiorFaixaPremiavel;
        this.qtSorteios = qtSorteios;
    }
}
