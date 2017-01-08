public class Word {

	private String name;
	private int frequency;

	public Word(String name, int frequency){
    this.name=name;
		this.frequency=frequency;
	}

	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}

	public void setFrequency(int frequency){
		this.frequency = frequency;
	}
	public int getFrequency(){
		return frequency;
	}
}
