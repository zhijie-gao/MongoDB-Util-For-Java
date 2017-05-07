package Entity;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
@Entity("testBeanT")

public class testBeanT {
	@Id
	private ObjectId id;
	private String name;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public testBeanT(String name){
		this.name=name;
	}
}
