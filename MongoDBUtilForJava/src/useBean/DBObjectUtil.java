package useBean;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class DBObjectUtil {
	//��Beanת��ΪDBObject����
	public static <T>DBObject beanToDBObject(T bean){
		if(bean==null){
			return null;
		}
		DBObject dbObject=new BasicDBObject();
		//��javaBeanת��Ϊjson
		String json=com.alibaba.fastjson.JSON.toJSONString(bean);
		//��jsonתΪDBObject
		dbObject=(DBObject)JSON.parse(json);
		return dbObject;
	}
	public static <T>T dbObjectToBean(DBObject dbObj,Class<T> clz)throws Exception{
		if(dbObj==null){
			return null;
		}
		//��DBObjectתΪJson
		String json=JSON.serialize(dbObj);
		//��JsonתΪJavaBean
		//System.out.println(json);
		T obj=com.alibaba.fastjson.JSON.parseObject(json,clz);
		return obj;
	}
}
