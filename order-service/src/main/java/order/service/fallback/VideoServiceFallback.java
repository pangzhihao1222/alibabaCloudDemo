package order.service.fallback;

import net.xdclass.domain.Video;
import order.service.VideoService;
import org.springframework.stereotype.Service;

/**
 * 当feign请求失败后执行下面代码
 */
@Service
public class VideoServiceFallback implements VideoService {
    @Override
    public Video findById(int videoId) {
        return null;
    }

    @Override
    public int save(Video video) {
        return 333;
    }
}
