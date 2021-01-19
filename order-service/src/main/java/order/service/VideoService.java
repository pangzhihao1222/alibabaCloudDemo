package order.service;


import order.service.fallback.VideoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import net.xdclass.domain.Video;

@FeignClient(value = "video-service", fallback = VideoServiceFallback.class)  //当服务出问题，会自动到fallback中找对应的方法进行返回
public interface VideoService {
    @GetMapping("/api/v1/video/find_by_id")
    Video findById(@RequestParam("videoId") int videoId);

    @PostMapping("/api/v1/video/save")
    int save(@RequestBody Video video);

}
