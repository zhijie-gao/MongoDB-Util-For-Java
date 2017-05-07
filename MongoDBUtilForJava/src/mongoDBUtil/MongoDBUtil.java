package mongoDBUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDBUtil {
	// ����Ĭ�����ã�1��IP��ַ 2���˿ں� 3���û��� 4������ 5�������ļ�λ���� 6�����ݿ���
	private static final String MONGODB_ADDRESS = "127.0.0.1";
	private static final int MONGODB_PORT = 27017;
	private static final String MONGODB_USERNAME = "root";
	private static final String MONGODB_PASSWORD = "";
	private static final String MONGODB_RESOURCE_FILE = "mongodb.cfg.properties";
	private static final String MONGODB_DBNAME = "test";
	private static final String MONGODB_COLLECTIONNAME = "test";
	// ���徲̬������1��Mongo���󣨴������ݿ����ӣ�2��DB���󣨴������ݿ⣩3��������4�����ݿ��������ӳ�伯��5���ѻ�ȡ�����ݿ�����
	private static Mongo mongo;
	private static DB db;
	private static DBCollection collection;
	private static Map<String, String> cfgMap = new HashMap<String, String>();
	private static Hashtable<String, DB> mongoDBs = new Hashtable<String, DB>();

	/**
	 * ��ʼ��Mongo�����ݿ�
	 */
	static {
		init();
	}

	/**
	 * ��ȡ�����ļ������õ�DB����
	 */
	public static DB getDB() {
		return db;
	}

	/**
	 * ��ȡ�����ļ������õ�DBCollection����
	 */
	public static DBCollection getDBCollection() {
		return collection;
	}

	/**
	 * �������ݿ�����,�õ����ݿ� ���������,�򴴽�һ�������Ƶ����ݿ�,�������û���������Ϊ�����ļ��еĲ���ֵ
	 * 
	 * @param dbName
	 * @return DB
	 */
	@SuppressWarnings("deprecation")
	public static DB getDBByName(String dbName) {
		DB db = mongo.getDB(dbName);
		if (!mongoDBs.contains(db)) {
			System.out.println("add");
			db.addUser(cfgMap.get("mongo.db.username"),
					cfgMap.get("mongo.db.password").toCharArray());
			mongoDBs.put(dbName, db);
		}
		return db;
	}

	// ��������������������������������������������������������������������������ʼ�����̡�����������������������������������������������������������������������
	/**
	 * ��ȡ�����ļ�mongedb.cfg.properties���ļ�����
	 */
	public static File getConfigFile() {
		String path = MongoDBUtil.class.getResource("/").getPath();
		String fileName = path + MONGODB_RESOURCE_FILE;
		System.out.println(fileName);
		File file = new File(fileName);
		if (file.exists()) {
			return file;
		}
		return null;
	}

	/**
	 * ͨ��mongedb.cfg.properties�����ļ���ʼ������ӳ�伯�ϣ����û�б�д�����ļ�������س���ָ����Ĭ������
	 */
	@SuppressWarnings("unchecked")
	private static void initCfgMap() {
		File file = getConfigFile();
		if (file != null) {
			Properties p = new Properties();
			try {
				p.load(new FileInputStream(file));
				for (Enumeration enu = p.propertyNames(); enu.hasMoreElements();) {
					String key = (String) enu.nextElement();
					String value = (String) p.getProperty(key);
					cfgMap.put(key, value);
				}
			} catch (IOException e) {
				System.out.println("����Mongo�����ļ�ʧ��!");
				e.printStackTrace();
			}
		} else { // ���û�б�д�����ļ��������Ĭ������
			cfgMap.put("mongo.db.address", MONGODB_ADDRESS);
			cfgMap.put("mongo.db.port", String.valueOf(MONGODB_PORT));
			cfgMap.put("mongo.db.username", MONGODB_USERNAME);
			cfgMap.put("mongo.db.password", MONGODB_PASSWORD);
			cfgMap.put("mongo.db.dbname", MONGODB_DBNAME);
			cfgMap.put("mongo.db.collectionname", MONGODB_COLLECTIONNAME);
		}
	}

	/**
	 * ��ʼ��Mongo�����ݿ⣨��dbָ����Ӧ�������ã���collectionָ����Ӧ�������ã�ͨ��mongoDBs��¼�������ݿ����
	 */
	@SuppressWarnings("deprecation")
	private static void init() {
		initCfgMap();
		try {
			String address = cfgMap.get("mongo.db.address");
			int port = Integer.parseInt(cfgMap.get("mongo.db.port").toString());
			String dbName = cfgMap.get("mongo.db.dbname");
			String username = cfgMap.get("mongo.db.username");
			String password = cfgMap.get("mongo.db.password");
			String collectionName = cfgMap.get("mongo.db.collectionname");
			mongo = new Mongo(address, port);
			if (dbName != null && !"".equals(dbName)) {
				db = mongo.getDB(dbName);
				if (username != null && !"".equals(username)) {
					db.addUser(username, password.toCharArray());
					if (collectionName != null && !"".equals(collectionName)) {
						collection = db.getCollection(collectionName);
					}
				}
				mongoDBs.put(dbName, db);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
