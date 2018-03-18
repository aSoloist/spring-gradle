import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author 文卡<wkwenka@gmail.com>  on 17-6-13.
 */
public class EmbededTomcat {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static String CONTEXT_PATH = "";
    private static String PROJECT_PATH = System.getProperty("user.dir");
    // web 目录
    private static String WEB_APP_PATH = PROJECT_PATH + File.separatorChar + "src/main/webapp";
    // 类编译路径
    private static String[] OUT_PUT_PATHS = { "domain/build/classes/java/main", "web/build/classes/java/main"};

    private static int PORT = 8082;

    private Tomcat tomcat = new Tomcat();

    private void start() throws Exception {
        log.info("==> AppBase: " + WEB_APP_PATH);

        tomcat.setPort(PORT);
        tomcat.setBaseDir(WEB_APP_PATH);
        tomcat.getHost().setAppBase(WEB_APP_PATH);

        StandardServer server = (StandardServer) tomcat.getServer();
        AprLifecycleListener listener = new AprLifecycleListener();
        server.addLifecycleListener(listener);

        StandardContext ctx = (StandardContext) tomcat.addWebapp(CONTEXT_PATH, WEB_APP_PATH);
        // declare an alternate location for your "WEB-INF/classes" dir:
        WebResourceRoot resources = new StandardRoot(ctx);

        for (String path : OUT_PUT_PATHS) {
            path = PROJECT_PATH + File.separatorChar + path;
            resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", path, "/"));
        }

        ctx.setResources(resources);
        ctx.setReloadable(true);

        // tomcat.addUser("system", "123");
        // tomcat.addRole("system", "SecurityUsers");
        tomcat.enableNaming();
        tomcat.getConnector().setURIEncoding("UTF-8");

        tomcat.start();
        tomcat.getServer().await();

        log.info("============== Tomcat 启动 ==============");
    }

    public void stop() throws Exception {
        tomcat.stop();
        log.info("============== Tomcat 终止 ==============");
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        EmbededTomcat embededTomcat = new EmbededTomcat();
        embededTomcat.start();
    }

}