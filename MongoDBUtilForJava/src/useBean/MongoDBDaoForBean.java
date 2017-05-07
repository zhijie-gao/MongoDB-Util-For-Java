package useBean;
import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
/*
 * Author:  gaozhijie
 * time:    2017-05-06
 * describe:mongodb ������java��javaBean�����ӿ�
 */
public interface MongoDBDaoForBean {
	public DBCollection getCollection(String collection);
	//��ȡ�����е���������
	public long getCollectionCount(String collection);
	//���ҷ�����������������
	public long getCount(String collection,DBObject obj);
	//��������
	public boolean insert(String collection,DBObject o);
	//�����������
	public boolean insertBatch(String collection,List<DBObject> list);
	//ɾ������
	public List<DBObject> delete(String collection,DBObject q);
	//ɾ����������
	public boolean deleteBatch(String collection,List<DBObject> list);
	public boolean updateOne(String collection,DBObject q,DBObject setFields);
	//��ѯ��������������
	public List<DBObject> findAll(String collection);
	//���ҷ�������������
	public List<DBObject> find(String collection,DBObject q);
	//���ҷ������������ݲ�����
	public List<DBObject> findSort(String collection,DBObject q, DBObject sort);
	//���ҷ���������ָ�����������ݲ�����
	public List<DBObject> findNumSort(String collection,DBObject q, DBObject sort,int start,int limit);
	//����ID��������
	public DBObject findGetByID(String collection,String Id);
	
}
