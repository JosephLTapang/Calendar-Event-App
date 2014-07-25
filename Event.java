
public class Event {

  protected String versi;
  protected String classi;
  protected String geopos;
  protected int priori;
  protected String summar;
  protected String start;
  protected String end;
  protected String tzi;
  protected String recur;
  
  public Event()
  {
    versi = "1.0";
    classi = "default";
    geopos = "none";
    priori = 0;
    summar = "none";
    start = "00000000000000Z";
    end = "00000000000000Z";
    tzi = "none";
    recur = "none";  
  }

}
