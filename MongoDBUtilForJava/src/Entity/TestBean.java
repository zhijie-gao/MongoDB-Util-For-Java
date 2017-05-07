package Entity;

import com.google.code.morphia.annotations.Id;

public class TestBean {
	
	private Integer mid;
	private String msg;
	private Double score;
	private SubBean subBean;

	public Integer getMId() {
		return mid;
	}
	public void setMId(Integer id) {
		this.mid = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public SubBean getSubBean() {
		return subBean;
	}
	public void setSubBean(SubBean subBean) {
		this.subBean = subBean;
	}

}
