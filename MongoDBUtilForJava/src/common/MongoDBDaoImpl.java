package common;
import java.net.UnknownHostException;  
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;  
import com.mongodb.DB;  
import com.mongodb.DBCollection;  
import com.mongodb.DBCursor;  
import com.mongodb.DBObject;  
import com.mongodb.MongoClient;  
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.WriteResult;
public class MongoDBDaoImpl implements MongoDBDao{
	private MongoClient mongoClient=null;
	private static final MongoDBDaoImpl mongoDBDaoImpl=new MongoDBDaoImpl();
	public static MongoDBDaoImpl getMongoDBDaoImplInstance(){  
	       return mongoDBDaoImpl;  
	    } 
	private MongoDBDaoImpl(){
		if(mongoClient==null){
			MongoCredential credentials=MongoCredential.createCredential("root", "testDB", "123456".toCharArray());
			List<MongoCredential> credentialsList=new ArrayList<MongoCredential>();
			credentialsList.add(credentials);
			MongoClientOptions.Builder build=new MongoClientOptions.Builder();
			//与目标数据库能够建立最大的connection数量为50
			build.connectionsPerHost(50);
			//如果当前所有的connection都在使用，则每个connection上可以有50个线程排队等待
			build.threadsAllowedToBlockForConnectionMultiplier(50);
			build.maxConnectionIdleTime(120000);
			build.connectTimeout(60000);
			build.autoConnectRetry(true);
			MongoClientOptions myOptions=build.build();
			try{
				mongoClient=new MongoClient("localhost",myOptions);
				System.out.println("连接成功");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public DB getDB(String dbName) {
		// TODO Auto-generated method stub
		return mongoClient.getDB(dbName);
	}

	@Override
	public DBCollection getCollection(String dbName, String collectionName) {
		// TODO Auto-generated method stub
		return mongoClient.getDB(dbName).getCollection(collectionName);
	}

	@Override
	public boolean insert(String dbName, String collectionName, String[] keys, Object[] values) {
		// TODO Auto-generated method stub
		DB db=null;
		DBCollection dbCollection=null;
		WriteResult result=null;
		String resultString=null;
		if(keys!=null&&values!=null){
			if(keys.length!=values.length){
				return false;  
			}else{
				try{
				    db=mongoClient.getDB(dbName);
				    dbCollection=db.getCollection(collectionName);
				    BasicDBObject insertObj=new BasicDBObject();
				    for(int i=0;i<keys.length;i++){
				    	insertObj.put(keys[i], values[i]);
				    	}
				
					dbCollection.insert(insertObj);
					resultString = result.getError();
					if(null!=db){
						try{
							db.requestDone();
							db=null;
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					return (resultString!=null)?false:true;
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(null!=db){
						//请求结束后关闭数据库
						db.requestDone();
						db=null;
					}
				}
				
			}
		}
		return false;
	}

	@Override
	public boolean delete(String dbName, String collectionName, String[] keys, Object[] values) {
		// TODO Auto-generated method stub
		DB db=null;
		DBCollection dbCollection=null;
		if(keys!=null&&values!=null){
			//如果Keys和values不对等，直接返回false
			if(keys.length!=values.length){
				return false;  
			}else{
				try{
					//获取数据库实例
				    db=mongoClient.getDB(dbName);
				    //获取指定的collectionName集合  
				    dbCollection=db.getCollection(collectionName);
				    //构建删除条件  
				    BasicDBObject deleteObj=new BasicDBObject();
				    //删除返回结果
					WriteResult result=null;
					String resultString=null;
				    for(int i=0;i<keys.length;i++){
				    	//添加删除的条件
				    	deleteObj.put(keys[i], values[i]);
				    	}
				    //执行删除操作
				    result=dbCollection.remove(deleteObj);
					resultString = result.getError();
					if(null!=db){
						try{
							db.requestDone();
							db=null;
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					return (resultString!=null)?false:true;
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(null!=db){
						//请求结束后关闭数据库
						db.requestDone();
						db=null;
					}
				}
				
			}
		}
		return false;
	}

	@Override
	public ArrayList<DBObject> find(String dbName, String collectionName, String[] keys, Object[] values, int num) {
		// TODO Auto-generated method stub
		//创建返回的结果集 
		ArrayList<DBObject> resultList=new ArrayList<DBObject>();
		DB db=null;
		DBCollection dbCollection=null;
		DBCursor cursor=null;
		if(keys!=null&&values!=null){
			if(keys.length!=values.length){
				//如果传来的查询参数对不对，直接返回空的结果集  
				return resultList;  
			}else{
				try{
					//获取数据库实例 
				    db=mongoClient.getDB(dbName);
				    //获取数据库中指定的collection集合  
				    dbCollection=db.getCollection(collectionName);
				    //构建查询条件
				    BasicDBObject queryObj=new BasicDBObject();
					WriteResult result=null;
					String resultString=null;
				    for(int i=0;i<keys.length;i++){
				    	//填充查询条件
				    	queryObj.put(keys[i], values[i]);
				    }
				    //查询获取数据
				    cursor=dbCollection.find(queryObj);
				    int count=0;
				    //判断是否是返回全部数据，num=-1返回查询全部数据，num!=-1则返回指定的num数据
					if(num!=-1){
						while(count<num&&cursor.hasNext()){
							resultList.add(cursor.next());
							count++;
						}
						return resultList;		
					}else{
						while(cursor.hasNext()){
							resultList.add(cursor.next());
						}
						return resultList;
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					if(null!=cursor){
						cursor.close();
					}
					if(null!=db){
						//关闭数据库请求 
						db.requestDone();
					}
				}
				
			}
		}
		return resultList;
	}

	@Override
	public boolean update(String dbName, String collectionName, DBObject oldValue, DBObject newValues) {
		// TODO Auto-generated method stub
		DB db=null;
		DBCollection dbCollection=null;
		if(oldValue.equals(newValues)){
			return true;
		}else{
			try{

				WriteResult result=null;
				String resultString=null;
				//获取数据库实例
				db=mongoClient.getDB(dbName);
				//获取数据库中指定的collection集合
			    dbCollection=db.getCollection(collectionName);
			    BasicDBObject queryObj=new BasicDBObject();
			    result=dbCollection.update(oldValue, newValues);
				resultString=result.getError();
				return (resultString!=null)?false:true;
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(null!=db){
					db.requestDone();
					db=null;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isExit(String dbName, String collectionName, String key, Object value) {
		// TODO Auto-generated method stub
		DB db=null;
		DBCollection dbCollection=null;
		if(key!=null&&value!=null){
			try{
				//获取数据库实例
				db=mongoClient.getDB(dbName);
				//获取数据库中指定的collection集合
			    dbCollection=db.getCollection(collectionName);
			    //构建查询条件
			    BasicDBObject isExitObj=new BasicDBObject();
			    isExitObj.put(key, value);
			    if(dbCollection.count(isExitObj)>0){
			    	return true;
			    }else{
			    	return false;
			    }
			    }catch(Exception e){
			    	e.printStackTrace();
			    }finally{
			    	if(null!=db){
			    		//关闭数据库
			    		db.requestDone();
			    		db=null;
			    	}
			    }
			}
		return false;
	}

}
