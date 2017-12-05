package wang.huaichao;

import org.openxmlformats.schemas.drawingml.x2006.main.STAdjAngle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.Permission;
import java.security.PermissionCollection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Administrator on 8/17/2016.
 */
public class Global {
    private static final Logger log = LoggerFactory.getLogger(Global.class);

    public static final String DB_DATETIME_FMT_STR = "yyyy-MM-dd HH:mm:ss";
    public static final DateFormat DB_DATETIME_FMT = new SimpleDateFormat(DB_DATETIME_FMT_STR);
    public static final DateFormat yyyyMM_FMT = new SimpleDateFormat("yyyyMM");

    public static final String MonthFullNamesEn[] = "January,February,March,April,May,June,July,August,September,October,November,December".split(",");
    public static final String MonthAbbrNamesEn[] = "Jan.,Feb.,Mar.,Apr.,May,June,July,Aug.,Sept.,Oct.,Nov.,Dec.".split(",");


    public static final String USER_HOME_DIR;
    public static final String PDF_STORE_DIR;

    static {
        USER_HOME_DIR = _getUserHome();
        PDF_STORE_DIR = USER_HOME_DIR + File.separator + "bill-pdf-dir" + File.separator;
    }

    private static String _getUserHome() {
        String userHome = System.getProperty("user.home");
        return userHome == null ? "." : userHome;
    }

    public static void removeCryptographyRestrictions() {
        if (!isRestrictedCryptography()) {
            log.info("Cryptography restrictions removal not needed");
            return;
        }
        try {
        /*
         * Do the following, but with reflection to bypass access checks:
         *
         * JceSecurity.isRestricted = false;
         * JceSecurity.defaultPolicy.perms.clear();
         * JceSecurity.defaultPolicy.add(CryptoAllPermission.INSTANCE);
         */
            final Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
            final Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
            final Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");

            final Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
            isRestrictedField.setAccessible(true);
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(isRestrictedField, isRestrictedField.getModifiers() & ~Modifier.FINAL);
            isRestrictedField.set(null, false);

            final Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
            defaultPolicyField.setAccessible(true);
            final PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);

            final Field perms = cryptoPermissions.getDeclaredField("perms");
            perms.setAccessible(true);
            ((Map<?, ?>) perms.get(defaultPolicy)).clear();

            final Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            defaultPolicy.add((Permission) instance.get(null));

            log.info("Successfully removed cryptography restrictions");
        } catch (final Exception e) {
            log.warn("Failed to remove cryptography restrictions", e);
        }
    }

    private static boolean isRestrictedCryptography() {
        // This simply matches the Oracle JRE, but not OpenJDK.
        return "Java(TM) SE Runtime Environment".equals(System.getProperty("java.runtime.name"));
    }
}
