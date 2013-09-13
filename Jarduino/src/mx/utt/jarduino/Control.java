package mx.utt.jarduino;

public class Control {
private String id,equipo,status;
	
	public String getId(){return id;}
	public String getEquipo(){return equipo;}
	public String getStatus(){return status;}
	
	public Control()
	{
		id="";
		equipo="";
		status="";
	}
	public Control(String clave,String equi, String stu)
	{
		id=clave;
		equipo=equi;
		status=stu;
	}
}
