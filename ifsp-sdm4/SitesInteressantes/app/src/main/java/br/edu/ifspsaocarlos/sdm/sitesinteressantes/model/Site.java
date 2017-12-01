package br.edu.ifspsaocarlos.sdm.sitesinteressantes.model;

/**
 * Created by Pio Tofanelli on 30-Nov-17.
 */

public class Site {
    // URL do site interessante
    private String url;
    // Caminho para imagem de favorito
    private int imagemFavorito;

    public Site() {
    }
    public Site(String url, int imagemFavorito) {
        this.url = url;
        this.imagemFavorito = imagemFavorito;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getImagemFavorito() {
        return imagemFavorito;
    }
    public void setImagemFavorito(int imagemFavorito) {
        this.imagemFavorito = imagemFavorito;
    }

    @Override
    public String toString() {
        return url+"-"+imagemFavorito;
    }
}

