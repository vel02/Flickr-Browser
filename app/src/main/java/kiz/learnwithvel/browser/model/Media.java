package kiz.learnwithvel.browser.model;

public class Media {

    private String m;

    public Media(String m) {
        this.m = m;
    }

    public Media() {
    }

    @Override
    public String toString() {
        return "Media{" +
                "media='" + m + '\'' +
                '}';
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
}
