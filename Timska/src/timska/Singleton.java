package timska;



public class Singleton {
	public int br=0; 
	public String ime="";
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