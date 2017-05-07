package useBean;
import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
/*
 * Author:  gaozhijie
 * time:    2017-05-06
 * describe:mongodb 基本的java和javaBean操作接口
 */
public interface MongoDBDaoForBean {
	public DBCollection getCollection(String collection);
	//获取集合中的数据数量
	public long getCollectionCount(String collection);
	//查找符合条件的数据数量
	public long getCount(String collection,DBObject obj);
	//插入数据
	public boolean insert(String collection,DBObject o);
	//插入多条数据
	public boolean insertBatch(String collection,List<DBObject> list);
	//删除数据
	public List<DBObject> delete(String collection,DBObject q);
	//删除多条数据
	public boolean deleteBatch(String collection,List<DBObject> list);
	public boolean updateOne(String collection,DBObject q,DBObject setFields);
	//查询集合中所有数据
	public List<DBObject> findAll(String collection);
	//查找符合条件的数据
	public List<DBObject> find(String collection,DBObject q);
	//查找符合条件的数据并排序
	public List<DBObject> findSort(String collection,DBObject q, DBObject sort);
	//查找符合条件的指定数量的数据并排序
	public List<DBObject> findNumSort(String collection,DBObject q, DBObject sort,int start,int limit);
	//根据ID查找数据
	public DBObject findGetByID(String collection,String Id);
	
}
