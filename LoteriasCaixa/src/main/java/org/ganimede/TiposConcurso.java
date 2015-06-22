/**
 * 
 */
package org.ganimede;

/**
 * @author Administrador
 * 
 */
public enum TiposConcurso {
    LOTO_FACIL("LF", "Quina", 25, 15), //
    QUINA("QN", "Quina", 80, 5), //
    DUPLA_SENA("DS", "Dupla Sena", 60, 6), //
    MEGA_SENA("MS", "Mega Sena", 60, 6);

    public String sigla;
    public String nome;
    public int nuDezenas;
    public int maiorFaixaPremiavel;

    private TiposConcurso(String sigla, String nome, int nuDezenas, int maiorFaixaPremiavel) {
        this.sigla = sigla;
        this.nome = nome;
        this.nuDezenas = nuDezenas;
        this.maiorFaixaPremiavel = maiorFaixaPremiavel;
    }
}
