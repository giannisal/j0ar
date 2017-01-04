public class Frequency {

	 private int idword;
	 private int idlink;
	 private int frequency;

	 public Frequency(int idword, int idlink, int frequency){
		 this.idword = idword;
		 this.idlink = idlink;
		 this.frequency = frequency;
	 }

	 public void setIdLink(int idlink){
	    this.idlink = idlink;
	 }
	 public int getIdLink(){
	    return idlink;
	 }

	 public void setIdWord(int idword){
	 	    this.idword = idword;
	 }
	 public int getIdWord(){
	     return idword;
	 }

	 public void setFrequency(int frequency){
			this.frequency = frequency;
	 }
	 public int getFrequency(){
		 return frequency;
     }



}