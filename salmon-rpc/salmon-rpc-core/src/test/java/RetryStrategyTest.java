import com.salmon.fault.retry.RetryStrategy;
import com.salmon.fault.retry.impl.NoRetryStrategy;
import com.salmon.model.RpcResponse;
import org.junit.Test;

/**
 * 重试策略测试
 *
 * @author Salmon
 * @since 2024-05-14
 */
public class RetryStrategyTest {
    RetryStrategy retryStrategy = new NoRetryStrategy();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}
