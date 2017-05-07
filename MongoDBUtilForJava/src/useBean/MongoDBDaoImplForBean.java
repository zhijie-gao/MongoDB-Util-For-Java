package useBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bson.types.ObjectId;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

import mongoDBUtil.MongoDBUtil;

public class MongoDBDaoImplForBean implements MongoDBDaoForBean{
	private MongoClient mongo=null;
	private DB db=null;
	private String dbName;
	private String collName;
	private static final Map<String,MongoDBDaoImplForBean> instances=new ConcurrentHashMap<String,MongoDBDaoImplForBean>();
	
	static{
		getInstance("db");
	}
	
	public static MongoDBDaoImplForBean getInstance(){
		return getInstance("db");
	}
	public static MongoDBDaoImplForBean getInstance(String dbName){
		MongoDBDaoImplForBean mongoMgr=instances.get(dbName);
		if(mongoMgr==null){
			mongoMgr=buildInstance(dbName);
			if(mongoMgr==null){
				return null;
			}
			instances.put(dbName, mongoMgr);
		}
		return mongoMgr;
	}
	

	public static synchronized MongoDBDaoImplForBean buildInstance(String dbName){
		MongoDBDaoImplForBean mongoMgr=new MongoDBDaoImplForBean();
		try{
			/*MongoCredential credentials=MongoCredential.createCredential("root", "admin", "123456".toCharArray());
			List<MongoCredential> credentialsList=new ArrayList<MongoCredential>();
			credentialsList.add(credentials);
			MongoClientOptions.Builder build=new MongoClientOptions.Builder();

			build.connectionsPerHost(50);
			build.threadsAllowedToBlockForConnectionMultiplier(50);
			build.maxConnectionIdleTime(120000);
			build.connectTimeout(60000);
			build.autoConnectRetry(true);
			MongoClientOptions myOptions=build.build();
			ServerAddress addr=new ServerAddress("127.0.0.1",27017);
			mongoMgr.mongo=new MongoClient(addr,credentialsList,myOptions);
			mongoMgr.db=mongoMgr.mongo.getDB("testDB");
			System.out.println("连接成功");
			boolean flag=mongoMgr.db.authenticate("root", "123456".toCharArray());
			if(!flag){
				System.out.println("认证失败");
				return null;
			}*/
			if (dbName == null) {
				mongoMgr.db = MongoDBUtil.getDB();
			} else {
				mongoMgr.db = MongoDBUtil.getDBByName(dbName);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return mongoMgr;
	}
	@Override
	public DBCollection getCollection(String collection){
		db.requestStart();
		DBCollection collect=db.getCollection(collection);
		return collect;
	}
	//插入
	@Override
	public boolean insert(String collection,DBObject o){
		if(collection==null||o==null){
			return false;
		}else{
			try{
				WriteResult result=null;
				String resultString=null;
				result=getCollection(collection).insert(o);
				resultString=result.getError();
				return (resultString!=null)?false:true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}finally{
				db.requestDone();
			}

		}


	}
	//删除
	@Override
	public List<DBObject> delete(String collection,DBObject q){
		List<DBObject> list=null;
		if(collection==null||q==null){
			return null;
		}else{
			try{
				getCollection(collection).remove(q);
				list=find(collection,q);						
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.requestDone();
			}
	
		}
		return list;
	}
	//更新一条数据
	@Override
	public boolean updateOne(String collection,DBObject q,DBObject setFields){

		if(collection==null||q==null||setFields==null){
			return false;
		}else{
			try{
				//getCollection(collection).findAndModify(q, setFields);
				WriteResult result=null;
				String resultString=null;
				//result=getCollection(collection).update(q, setFields);
				result=getCollection(collection).update(q, setFields, false, false);
				resultString=result.getError();
				return (resultString!=null)?false:true;
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.requestDone();
			}
	
		}
		return false;
	}
	//查找所有集合对象
	@Override
	public List<DBObject> findAll(String collection){
		List<DBObject> list=null;
		if(collection==null){
			return null;
		}else{
			try{
				list=getCollection(collection).find().toArray();							
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.requestDone();
			}
	
		}
		return list;
	}
	//查找特定的集合对象
	@Override
	public List<DBObject> find(String collection,DBObject q){
		List<DBObject> list=null;
		if(collection==null||q==null){
			return null;
		}else{
			try{
				list=getCollection(collection).find(q).toArray();							
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.requestDone();
			}
	
		}
		return list;
	}
	@Override
	public long getCollectionCount(String collection) {
		// TODO Auto-generated method stub
		if(collection==null){
			return (Long) null;
		}
		return getCollection(collection).getCount();
	}
	@Override
	public long getCount(String collection, DBObject obj) {
		// TODO Auto-generated method stub
		if(collection==null){
			return (Long) null;
		}
		if (obj != null)
			return getCollection(collection).getCount(obj);
		return getCollectionCount(collection);
	}
	@Override
	public boolean insertBatch(String collection, List<DBObject> list) {
		// TODO Auto-generated method stub
		if(collection==null){
			return false;
		}
		if (list == null || list.isEmpty()) {
			return false;
		}
		List<DBObject> listDB = new ArrayList<DBObject>();
		for (int i = 0; i < list.size(); i++) {
			listDB.add(list.get(i));
		}
		try{
			WriteResult result=null;
			String resultString=null;
			result=getCollection(collection).insert(listDB);
			resultString=result.getError();
			return (resultString!=null)?false:true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			db.requestDone();
		}

	}
	@Override
	public boolean deleteBatch(String collection, List<DBObject> list) {
		// TODO Auto-generated method stub
		if(collection==null){
			return false;
		}
		if (list == null || list.isEmpty()) {
			return false;
		}
		try{
			WriteResult result=null;
			String resultString=null;
			for (int i = 0; i < list.size(); i++) {
				result=getCollection(collection).remove(list.get(i));
				resultString=result.getError();
				if(resultString!=null){
					return false;
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			db.requestDone();
		}

	}
	@Override
	public List<DBObject> findSort(String collection, DBObject q, DBObject sort) {
		// TODO Auto-generated method stub
		if(collection==null){
			return null;
		}
		DBCursor cur;
		try{
			if (q != null) {
				cur = getCollection(collection).find(q);
			} else {
				cur = getCollection(collection).find();
			}
			if (sort != null) {
				cur.sort(sort);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			db.requestDone();
		}

		return DBCursor2list(cur);
	}
	@Override
	public List<DBObject> findNumSort(String collection, DBObject q, DBObject sort, int start, int limit) {
		// TODO Auto-generated method stub
		if(collection==null){
			return null;
		}
		DBCursor cur;
		try{
			if (q != null) {
				cur = getCollection(collection).find(q);
			} else {
				cur = getCollection(collection).find();
			}
			if (sort != null) {
				cur.sort(sort);
			}
			if (start == 0) {
				cur.batchSize(limit);
			} else {
				cur.skip(start).limit(limit);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			db.requestDone();
		}

		return DBCursor2list(cur);
	}
	@Override
	public DBObject findGetByID(String collection, String id) {
		// TODO Auto-generated method stub
		if(collection==null){
			return null;
		}
		DBObject obj = new BasicDBObject();
		obj.put("_id", new ObjectId(id));
		DBObject result = getCollection(collection).findOne(obj);
		return result;
	}
	//将DBCursor转化为list<DBObject>
	private List<DBObject> DBCursor2list(DBCursor cur) {
		List<DBObject> list = new ArrayList<DBObject>();
		if (cur != null) {
			list = cur.toArray();
		}
		return list;
	}
}
