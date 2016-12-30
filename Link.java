package teamjava;
public class Link{

 private String name;
 private int idlink;
 public Link ( int lidlink,String lname) {
 name= lname;
 lidlink= idlink;
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