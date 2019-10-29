// 
// Decompiled by Procyon v0.5.36
// 

package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class PropertiesUtil
{
    private Properties prop;
    private static PropertiesUtil instance;
    
    private PropertiesUtil() throws IOException {
        this.prop = new Properties();
        final String propFileName = "config/configuration.properties";
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            this.prop.load(inputStream);
            return;
        }
        //throw new FileNotFoundException(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, propFileName));
    }
    
    public static PropertiesUtil getInstance() throws IOException {
        if (PropertiesUtil.instance == null) {
            PropertiesUtil.instance = new PropertiesUtil();
        }
        return PropertiesUtil.instance;
    }
    
    public String getProperty(final String property) {
        return this.prop.getProperty(property);
    }
}
