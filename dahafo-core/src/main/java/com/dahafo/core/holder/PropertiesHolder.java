package com.dahafo.core.holder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.dahafo.core.util.PropertiesUtil;



/**
 * PropertiesHolder类是Spring中PropertyPlaceholderConfigurer的扩展，
 * 主要用于将应用属性文件由Spring加载后，属�?�可在XML配置文件、Java代码中获取�??
 * 
 * @author qianyulong
 */
public class PropertiesHolder extends PropertyPlaceholderConfigurer {

    private static Logger logger = LoggerFactory.getLogger(PropertiesHolder.class);
    private static final Properties appProperties = new Properties();

    private PropertiesHolder() {
    }

    /**
     * 重写PropertyPlaceholderConfigurer类中的setLocations方法�?<br/>
     * 详细描述：接收一个Resource类型的数组作为参数，将locations标签下�?�都会被解析成Resource�?
     * 而这个resource本身则包含了访问这个resource的方法，在这里resource代表的则是properties文件�?
     * 
     * @param locations Resource类型的数组�??
     * @see #setLocations(Resource[])
     */
    @Override
    public void setLocations(Resource[] locations) {
        super.setLocations(locations);
        // 对properties文件进行排序,先读取jar包中的属性文�?,jar包外的属性配置可覆盖Jar包中的属性配�?
        Arrays.sort(locations, new Comparator<Resource>() {

            public int compare(Resource rs1, Resource rs2) {
                try {
                    if (ResourceUtils.JAR_URL_SEPARATOR.equals(rs1.getURL().getProtocol())) {
                        return -1;
                    } else {
                        return 1;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        logger.info("加载Properties配置文件:" + StringUtils.arrayToDelimitedString(locations, ","));
    }

    /** 输出�?有属性文件中配置的属性配置项 **/
    @SuppressWarnings({"unused", "rawtypes"})
    private void printAllProperties(Resource[] locations) {
        for (Resource res : locations) {
            PropertiesUtil propertiesUtil = new PropertiesUtil();
            try {
                propertiesUtil.load(res.getInputStream());
                Enumeration enums = propertiesUtil.keys();
                while (enums.hasMoreElements()) {
                    String propName = (String)enums.nextElement();
                    System.out.println(res.getURL().getPath().substring(res.getURL().getPath().lastIndexOf("/") + 1)
                                       + "," + propName + "," + propertiesUtil.getProperty(propName));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 覆盖Spring属�?�文件处理方法processProperties�?<br/>
     * 详细描述：将属�?��?�获取至静�?�Map对象中，用于java代码中获取属性�?��??<br/>
     * @param confFactory ConfigurableListableBeanFactory�?
     * @param props Properties�?
     * @see #postProcessBeanFactory(ConfigurableListableBeanFactory)
     * @throws BeansException异常，spring内部自定义的异常�?
     */
    protected void processProperties(ConfigurableListableBeanFactory confFactory, Properties props)
        throws BeansException {
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            if (keyStr.endsWith(".encrypted")) {
                String newKey = keyStr.substring(0, keyStr.lastIndexOf(".encrypted"));
                String newValue = value;
                appProperties.put(newKey, newValue);
            } else {
                appProperties.put(keyStr, value);
            }
        }
        super.processProperties(confFactory, appProperties);
    }

    /**
     * 获取属�?�对象�??<br/>
     * 详细描述：�?�过属�?�key，获取属性对象�??<br/>
     * 使用方式：可在java代码中直接调用此静�?�方法�??
     * 
     * @param key 属�?�key�?
     * @return 返回查询到的属�?�对象�??
     */
    public static Object getPropertyObj(String key) {
        if (appProperties == null) {
            logger.error("未初始化加载属�?�文件，请检查Spring配置");
            return null;
        }
        return appProperties.get(key);
    }

    /**
     * 获取String类型属�?��?��??<br/>
     * 详细描述：�?�过属�?�key，获取属性�?��??<br/>
     * 使用方式：java代码中可直接调用此静态方法�??
     * 
     * @param key 属�?�key�?
     * @return 返回String类型的属性�?��??
     */
    public static String getProperty(String key) {
        return (String)getPropertyObj(key);
    }

    /**
     * 获取int类型属�?��?��??<br/>
     * 详细描述：�?�过属�?�key，获取属性�?��??<br/>
     * 使用方式：java代码中可直接调用此静态方法�??
     * 
     * @param key 属�?�key�?
     * @return 返回int类型的属性�?��??
     */
    public static int getIntProperty(String key) {
        return Integer.valueOf(getProperty(key));
    }

    /**
     * 获取boolean类型属�?��?��?? <br/>
     * 详细描述：�?�过属�?�key，获取属性�?��??<br/>
     * 使用方式：java代码中可直接调用此静态方法�??
     * 
     * @param key 属�?�key�?
     * @return 返回boolean类型的属性�?��??
     */
    public static boolean getBooleanProperty(String key) {
        String val = getProperty(key);
        if (val != null && "true".equalsIgnoreCase(val)) {
            return true;
        }
        return false;
    }



    /**
     * 根据前缀获取符合条件的所有properties属�?��?��?? 详细描述：根据前�?进行匹配，找出符合该前缀条件的所有的properties的属性�?�，以map返回�?<br/>
     * 使用方式：PropertiesHolder.getPropertiesByPrefix("前缀")方式调用�?
     * 
     * @param prefix properties的key前缀�?
     * @return 符合参数条件的map�?
     */
    public static Map<String, Object> getPropertiesByPrefix(String prefix) {
        if (appProperties == null) {
            logger.error("未初始化加载属�?�文件，请检查Spring配置");
            return null;
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Set<String> set = appProperties.stringPropertyNames();
        for (String key : set) {
            if (key.startsWith(prefix)) {
                resultMap.put(key, getPropertyObj(key));
            }
        }
        return resultMap;
    }
}