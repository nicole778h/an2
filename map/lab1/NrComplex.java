public class NrComplex {
    private double real; //partea reala
    private double img; //partea imaginara

    //constructor
    public NrComplex(double real, double img){
        this.real = real;
        this.img = img;


    }

    //metode pt operatii de +-*
    public NrComplex adunare(NrComplex other){
        //implementare pt adunare
        return new NrComplex(this.real + other.real, this.img + other.img);
    }

    public NrComplex scadere(NrComplex other){
        //implementare scadere
        return new NrComplex(this.real - other.real, this.img - other.img);
    }

    public NrComplex inmultire(NrComplex other){
        //implementare inmultire
        double newReal = (this.real * other.real) - (this.img * other.img);
        double newImg = (this.real * other.img) + (this.img * other.real);
        return new NrComplex(newReal, newImg);
    }

    @Override
  public String toString(){
      return real + " + " + img + "i";

    }
}

