package useBean;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class DBObjectUtil {
	//把Bean转换为DBObject对象
	public static <T>DBObject beanToDBObject(T bean){
		if(bean==null){
			return null;
		}
		DBObject dbObject=new BasicDBObject();
		//将javaBean转换为json
		String json=com.alibaba.fastjson.JSON.toJSONString(bean);
		//把json转为DBObject
		dbObject=(DBObject)JSON.parse(json);
		return dbObject;
	}
	public static <T>T dbObjectToBean(DBObject dbObj,Class<T> clz)throws Exception{
		if(dbObj==null){
			return null;
		}
		//把DBObject转为Json
		String json=JSON.serialize(dbObj);
		//把Json转为JavaBean
		//System.out.println(json);
		T obj=com.alibaba.fastjson.JSON.parseObject(json,clz);
		return obj;
	}
}
