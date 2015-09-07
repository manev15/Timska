package timska;



public class Singleton {
	public int br=0; 
	public int br1=0; 
	public double lat=0;
	public double lng=0;
	public String ime="";
	public String grad="";
	public String Tripgrad="";
	
	public String category="";
	

	
	private static Singleton mInstance = null;
    
	protected Singleton(){}

	public static synchronized Singleton getInstance(){
	if(null == mInstance){
		mInstance = new Singleton();
	}
	return mInstance;
	}
	}