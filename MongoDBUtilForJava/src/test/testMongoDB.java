package test;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import Entity.SubBean;
import Entity.TestBean;
import mongoDBUtil.MongoCollections;
import useBean.DBObjectUtil;
import useBean.MongoDBDaoImplForBean;

public class testMongoDB {
	public static void main(String[] args) {  
		try{
    		/*System.out.println("直接使用DBObject调用MogoUtil");
    		System.out.println("插入数据");
    		MongoDBDaoImplForBean.getInstance().insert(MongoCollections.USER_DATA, new BasicDBObject().append("name", "hello")
    				.append("type", "2")
    				.append("score", 70)
    				.append("level", 2)
    				.append("inputTime", System.currentTimeMillis()));
    		System.out.println("更新数据");
    		MongoDBDaoImplForBean.getInstance().updateOne(MongoCollections.USER_DATA,new BasicDBObject().append("status",1), 
    				new BasicDBObject().append("status",2));*/
    		//javaBean 接口测试
    		TestBean bean1=new TestBean();
    		bean1.setMId(111);
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
    		bean1.setMId(22222);
    		//MongoDBDaoImplForBean.getInstance().insert(MongoCollections.USER_DATA, DBObjectUtil.<TestBean>beanToDBObject(bean1));
    		//更新数据
    		BasicDBObject query=new BasicDBObject();
    		query.put("msg", "hello");
    		BasicDBObject setFields=new BasicDBObject("$set",new BasicDBObject("score",101));
    		//setFields.put("score", 102);
    		MongoDBDaoImplForBean.getInstance().updateOne(MongoCollections.USER_DATA, query, setFields);
    		//删除数据
    		BasicDBObject delObj=new BasicDBObject();
    		delObj.put("id", 2222);
    		MongoDBDaoImplForBean.getInstance().delete(MongoCollections.USER_DATA,delObj);
    		//查找集合
    		List<DBObject> findList=MongoDBDaoImplForBean.getInstance().find(MongoCollections.USER_DATA,query);
    		for(DBObject dbObject:findList){
    			TestBean findBean=new TestBean();
    			//System.out.println("列表");
    			//System.out.println(dbObject.toString());
    			findBean=DBObjectUtil.<TestBean> dbObjectToBean(dbObject,TestBean.class);
    			System.out.println(findBean.getMId());
    			System.out.println(findBean.getMsg());
    			System.out.println(findBean.getScore());
    			System.out.println(findBean.getSubBean().getId());
    			System.out.println(findBean.getSubBean().getStr());
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
}
