public class Link{

 private String name;
 private int idlink;

 public Link(int idlink, String name){
	 this.name = name;
	 this.idlink= idlink;
 }


 public void setName(String name){
    this.name = name;
 }
 public String getName(){
    return name;
 }

 public void setId(int idlink){
    this.idlink = idlink;
 }
 public int getId(){
    return idlink;
 }

}