package br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.model;

/**
 * Created by ptofanelli on 02/23/2018.
 */

public class DrawerMenuItem {

    private  String text;
    private int imagemId;
    private String imagemUri;

    public DrawerMenuItem(String text) {
        this.text = text;
    }

    public DrawerMenuItem(String text, int imagemId) {
        this.text = text;
        this.imagemId = imagemId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImagemId() {
        return imagemId;
    }

    public void setImagemId(int imagemId) {
        this.imagemId = imagemId;
    }

    public String getImagemUri() {
        return imagemUri;
    }

    public void setImagemUri(String imagemUri) {
        this.imagemUri = imagemUri;
    }
}
