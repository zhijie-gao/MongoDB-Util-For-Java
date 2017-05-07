package Morphia;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import Entity.testBeanT;

public class MorphiaUtil {
	public static Datastore ds;
	public static String dbName="db";
	public static Datastore getDatastore(){
		if(ds==null){
			ds=buildDatastore();
		}
		return ds;
	}
	public static Datastore buildDatastore(){
		MongoClient mongo=null;
		try{
			ServerAddress addr=new ServerAddress("127.0.0.1",27017);
			mongo=new MongoClient(addr,getDBOptions(dbName));
		}catch(Exception e){
			e.printStackTrace();
		}
		Morphia morphia=new Morphia();
		morphia.mapPackage("mongoDBUtil");
		ds=morphia.createDatastore(mongo,"hero1");
		ds.ensureIndexes();
		if(ds==null){
			System.out.println("connect fail");
		}
		return ds;
	}
	private static MongoClientOptions getDBOptions(String dbName){
		MongoClientOptions.Builder build=new MongoClientOptions.Builder();
		//与目标数据库能够建立最大的connection数量为50
		build.connectionsPerHost(50);
		//如果当前所有的connection都在使用，则每个connection上可以有50个线程排队等待
		build.threadsAllowedToBlockForConnectionMultiplier(50);
		build.maxConnectionIdleTime(120000);
		build.connectTimeout(60000);
		build.autoConnectRetry(true);
		MongoClientOptions myOptions=build.build();
		return myOptions;
	}
	public void test(){
		final testBeanT testBeanT=new testBeanT("hello");
		ds.save(testBeanT);
		
		final testBeanT testBeanT2=new testBeanT("hello2");
		ds.save(testBeanT2);
		
		final Query<testBeanT> query = ds.createQuery(testBeanT.class);
		final List<testBeanT> testBeanTs = query.asList();
		
		
	}
	
}
