package common;
import java.util.ArrayList;  
  import com.mongodb.DB;  
import com.mongodb.DBCollection;  
import com.mongodb.DBObject; 

/*
 * Author:  gaozhijie
 * time:    2017-05-06
 * describe:mongodb ������java�����ӿ�
 */
public interface MongoDBDao {
	//��ȡָ����mongdb���ݿ�
	public DB getDB(String dbName);
	//��ȡָ����mongdb���ݿ��collection����
	public DBCollection getCollection(String dbName,String collectionName);
	//��ָ�������ݿ������key�Ͷ�Ӧ��values
	public boolean insert(String dbName,String collectionName,String[] keys,Object[] values);
	//ɾ�����ݿ��и�����key����Ӧ��values
	public boolean delete(String dbName,String collectionName,String[] keys,Object[] values);
	//�����ݿ�dbName�в���ָ����key����Ӧ��valuesֵ
	public ArrayList<DBObject> find(String dbName,String collection,String[] keys,Object[] values,int num);
	//�������ݿ�dbName�е�oldValueΪnewValue
	public boolean update(String dbName,String collectionName,DBObject oldValue,DBObject newValues);
	//�жϸ�����keys����Ӧ��values��ָ����dbName�е�collectionName�������Ƿ����
	public boolean isExit(String dbName,String collectionName,String key,Object value);

}
