package test;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
/*
 * Author:  gaozhijie
 * time:    2017-05-06
 * describe:mongodb 测试类
 */
public class testDB {
    public static void main1(String[] args) {  
        // TODO Auto-generated method stub
    	//基本java接口测试
    	/*MongoDBDaoImpl.getMongoDBDaoImplInstance();
    	if(MongoDBDaoImpl.getMongoDBDaoImplInstance().isExit("testDB", "col", "likes", 105.0)){
    		System.out.println("存在");
    	}else{
    		System.out.println("不存在");
    	}
    	String[] a=new String[2];
    	a[0]="java";
    	a[1]="php";
    	String[] deletedd=new String[6];
    	deletedd[0]="likes";
    	deletedd[1]="title";
    	deletedd[2]="description";
    	deletedd[3]="by";
    	deletedd[4]="url";
    	Object[] delted=new Object[6];
    	delted[0]=103.0;
    	delted[1]="MongoDB 教程";
    	delted[2]="MongoDB 是一个 Nosql 数据库";
    	delted[3]="菜鸟教程";
    	delted[4]="http://www.runoob.com";
/*    	if(MongoDBDaoImpl.getMongoDBDaoImplInstance().delete("testDB", "col", deletedd, delted)){
    		System.out.println("删除成功");
    	}else{
    		System.out.println("删除失败");
    	}
    	if(MongoDBDaoImpl.getMongoDBDaoImplInstance().insert("testDB", "col", deletedd, delted)){
    		System.out.println("删除成功");
    	}else{
    		System.out.println("删除失败");
    	}*/
/*    	
    	try{
    		System.out.println("直接使用DBObject调用MogoUtil");
    		System.out.println("插入数据");
    		MongoDBDaoImplForBean.getInstance().insert(MongoCollections.USER_DATA, new BasicDBObject().append("name", "hello")
    				.append("type", "2")
    				.append("score", 70)
    				.append("level", 2)
    				.append("inputTime", System.currentTimeMillis()));
    		System.out.println("更新数据");
    		MongoDBDaoImplForBean.getInstance().updateOne(MongoCollections.USER_DATA,new BasicDBObject().append("status",1), 
    				new BasicDBObject().append("status",2));
    		//javaBean 接口测试
    		TestBean bean1=new TestBean();
    		bean1.setId(11111);
    		bean1.setMsg("hello");
    		bean1.setScore(100.0);
    		SubBean sub1=new SubBean();
    		sub1.setId(2222);
    		sub1.setStr("helloSub");
    		List<SubBean> subBeans=new ArrayList<SubBean>();
    		subBeans.add(sub1);
    		bean1.setSubBean(sub1);
    		//插入数据
    		MongoDBDaoImplForBean.getInstance().insert(MongoCollections.USER_DATA, DBObjectUtil.<TestBean>beanToDBObject(bean1));
    		bean1.setId(22222);
    		MongoDBDaoImplForBean.getInstance().insert(MongoCollections.USER_DATA, DBObjectUtil.<TestBean>beanToDBObject(bean1));
    		//更新数据
    		BasicDBObject query=new BasicDBObject();
    		query.put("id", 11111);
    		BasicDBObject setFields=new BasicDBObject();
    		setFields.put("str", "hello mongo");
    		MongoDBDaoImplForBean.getInstance().updateOne(MongoCollections.USER_DATA, query, setFields);
    		//删除数据
    		BasicDBObject delObj=new BasicDBObject();
    		delObj.put("id", 2222);
    		MongoDBDaoImplForBean.getInstance().delete(MongoCollections.USER_DATA,delObj);
    		//查找集合
    		List<DBObject> findList=MongoDBDaoImplForBean.getInstance().findAll(MongoCollections.USER_DATA);
    		for(DBObject dbObject:findList){
    			TestBean findBean=new TestBean();
    			System.out.println("列表");
    			System.out.println(dbObject.toString());
    			findBean=DBObjectUtil.<TestBean> dbObjectToBean(dbObject,TestBean.class);
    			System.out.println(findBean.getId());
    			System.out.println(findBean.getMsg());
    			System.out.println(findBean.getScore());
    			System.out.println(findBean.getSubBean().getId());
    			System.out.println(findBean.getSubBean().getStr());
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}*/
    } 

}
