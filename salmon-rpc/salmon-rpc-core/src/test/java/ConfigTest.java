import com.salmon.config.RpcConfig;
import com.salmon.constant.RpcConstant;
import com.salmon.utils.ConfigUtils;
import org.junit.Test;

/**
 * @author Salmon
 * @since 2024-05-02
 */
public class ConfigTest {

    @Test
    public void test1(){
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        System.out.println(rpcConfig);
    }
}
