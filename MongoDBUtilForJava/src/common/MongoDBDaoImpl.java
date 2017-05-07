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
			//��Ŀ�����ݿ��ܹ���������connection����Ϊ50
			build.connectionsPerHost(50);
			//�����ǰ���е�connection����ʹ�ã���ÿ��connection�Ͽ�����50���߳��Ŷӵȴ�
			build.threadsAllowedToBlockForConnectionMultiplier(50);
			build.maxConnectionIdleTime(120000);
			build.connectTimeout(60000);
			build.autoConnectRetry(true);
			MongoClientOptions myOptions=build.build();
			try{
				mongoClient=new MongoClient("localhost",myOptions);
				System.out.println("���ӳɹ�");
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
						//���������ر����ݿ�
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
			//���Keys��values���Եȣ�ֱ�ӷ���false
			if(keys.length!=values.length){
				return false;  
			}else{
				try{
					//��ȡ���ݿ�ʵ��
				    db=mongoClient.getDB(dbName);
				    //��ȡָ����collectionName����  
				    dbCollection=db.getCollection(collectionName);
				    //����ɾ������  
				    BasicDBObject deleteObj=new BasicDBObject();
				    //ɾ�����ؽ��
					WriteResult result=null;
					String resultString=null;
				    for(int i=0;i<keys.length;i++){
				    	//���ɾ��������
				    	deleteObj.put(keys[i], values[i]);
				    	}
				    //ִ��ɾ������
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
						//���������ر����ݿ�
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
		//�������صĽ���� 
		ArrayList<DBObject> resultList=new ArrayList<DBObject>();
		DB db=null;
		DBCollection dbCollection=null;
		DBCursor cursor=null;
		if(keys!=null&&values!=null){
			if(keys.length!=values.length){
				//��������Ĳ�ѯ�����Բ��ԣ�ֱ�ӷ��ؿյĽ����  
				return resultList;  
			}else{
				try{
					//��ȡ���ݿ�ʵ�� 
				    db=mongoClient.getDB(dbName);
				    //��ȡ���ݿ���ָ����collection����  
				    dbCollection=db.getCollection(collectionName);
				    //������ѯ����
				    BasicDBObject queryObj=new BasicDBObject();
					WriteResult result=null;
					String resultString=null;
				    for(int i=0;i<keys.length;i++){
				    	//����ѯ����
				    	queryObj.put(keys[i], values[i]);
				    }
				    //��ѯ��ȡ����
				    cursor=dbCollection.find(queryObj);
				    int count=0;
				    //�ж��Ƿ��Ƿ���ȫ�����ݣ�num=-1���ز�ѯȫ�����ݣ�num!=-1�򷵻�ָ����num����
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
						//�ر����ݿ����� 
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
				//��ȡ���ݿ�ʵ��
				db=mongoClient.getDB(dbName);
				//��ȡ���ݿ���ָ����collection����
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
				//��ȡ���ݿ�ʵ��
				db=mongoClient.getDB(dbName);
				//��ȡ���ݿ���ָ����collection����
			    dbCollection=db.getCollection(collectionName);
			    //������ѯ����
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
			    		//�ر����ݿ�
			    		db.requestDone();
			    		db=null;
			    	}
			    }
			}
		return false;
	}

}
