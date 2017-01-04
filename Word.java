public class Word{
	private String name;
	private int idword;

	public Word(int idword, String name){
		this.idword=idword;
		this.name=name;
	}

	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}

	public void setId(int idword){
		this.idword = idword;
	}
	public int getId(){
		return idword;
	}
}