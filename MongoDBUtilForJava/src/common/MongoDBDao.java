package common;
import java.util.ArrayList;  
  import com.mongodb.DB;  
import com.mongodb.DBCollection;  
import com.mongodb.DBObject; 

/*
 * Author:  gaozhijie
 * time:    2017-05-06
 * describe:mongodb 基本的java操作接口
 */
public interface MongoDBDao {
	//获取指定的mongdb数据库
	public DB getDB(String dbName);
	//获取指定的mongdb数据库的collection集合
	public DBCollection getCollection(String dbName,String collectionName);
	//向指定的数据库中添加key和对应的values
	public boolean insert(String dbName,String collectionName,String[] keys,Object[] values);
	//删除数据库中给定的key和相应的values
	public boolean delete(String dbName,String collectionName,String[] keys,Object[] values);
	//从数据库dbName中查找指定的key和相应的values值
	public ArrayList<DBObject> find(String dbName,String collection,String[] keys,Object[] values,int num);
	//更新数据库dbName中的oldValue为newValue
	public boolean update(String dbName,String collectionName,DBObject oldValue,DBObject newValues);
	//判断给定的keys和相应的values在指定的dbName中的collectionName集合中是否存在
	public boolean isExit(String dbName,String collectionName,String key,Object value);

}
